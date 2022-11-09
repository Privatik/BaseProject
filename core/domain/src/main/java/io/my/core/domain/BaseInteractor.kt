package io.my.core.domain

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseInteractor<S: Any>(state: S) {
    private val _state = MutableStateFlow(state)
    val state = _state.asStateFlow().onStart {
        handleDataFromOutSide()
            .onEach { newState -> _state.emit(newState) }
            .launchIn(CoroutineScope(currentCoroutineContext()))
    }

    protected abstract suspend fun handleDataFromOutSide(): Flow<S>

    fun <P> Flow<P>.updateState(
        block: (state: S, payload: P) -> S
    ): Flow<S> = map{ payload -> block(_state.value, payload) }


    suspend fun updateState(
        block: (state: S) -> S
    ) {
        _state.emit(block(_state.value))
    }
}