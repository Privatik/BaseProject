package io.my.core.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class BaseInteractor<S: Any>(
    state: S,
    protected val dispatchers: CoroutineDispatchersContext = getCoroutineDispatchersContext()
) {

    protected abstract val handleDataFromOutSide: Flow<S>
    private val _state = MutableStateFlow(state)
    protected val currentState: S get() = _state.value
    private val mutex = Mutex()
    @Volatile private var wasSettingFirstFlow = false

    private val state: Flow<S> by lazy {
        merge(
            _state.asStateFlow(),
            handleDataFromOutSide.flowOn(dispatchers.IO),
        )
    }

    protected fun <B: Any> state(
        transform: (S) -> B = {
            @Suppress("UNCHECKED_CAST")
            it as B
        }
    ): Flow<B> {
        if (!wasSettingFirstFlow){
            var flow: Flow<S>?
            synchronized(this) {
                flow = if (!wasSettingFirstFlow){
                    wasSettingFirstFlow = true
                    state.map { _state.value }
                } else {
                    _state.asStateFlow()
                }
            }
            return flow!!.map(transform)
        }
        return _state.asStateFlow().map(transform)
    }

    protected fun <P> Flow<P>.updateState(
        block: (state: S, payload: P) -> S
    ): Flow<S> = map { payload ->
        mutex.withLock {
            block(_state.value, payload).also { newState -> _state.emit(newState) }
        }
    }


    protected suspend fun updateState(
        block: (state: S) -> S
    ) {
        mutex.withLock {
            _state.emit(block(_state.value))
        }
    }

}
