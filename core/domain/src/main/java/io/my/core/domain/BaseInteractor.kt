@file:OptIn(ExperimentalCoroutinesApi::class)

package io.my.core.domain

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

abstract class BaseInteractor<S: Any>(state: S) {

    protected abstract val handleDataFromOutSide: Flow<S>
    private val _state = MutableStateFlow(state)
    protected val state: Flow<S> by lazy {
        merge(
            _state.asStateFlow().mapLatest { state -> WrapState(TagState.STATE, state) },
            handleDataFromOutSide.map { state -> WrapState(TagState.DATA, state) }
        ).map { wrapState ->
            if (wrapState.tag == TagState.DATA){ _state.emit(wrapState.body) }
            wrapState.body
        }.distinctUntilChanged()
    }

    fun <P> Flow<P>.updateState(
        block: (state: S, payload: P) -> S
    ): Flow<S> = map{ payload -> block(_state.value, payload) }


    suspend fun updateState(
        block: (state: S) -> S
    ) {
        _state.emit(block(_state.value))
    }
}

private enum class TagState{
    DATA,
    STATE
}

private data class WrapState<S: Any>(
    val tag: TagState,
    val body: S
)