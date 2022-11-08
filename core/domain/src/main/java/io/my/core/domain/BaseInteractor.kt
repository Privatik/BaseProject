package io.my.core.domain

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseInteractor<S: Any>(state: S) {
    private val _state = MutableStateFlow(state)
    val state = _state.asStateFlow().onStart { handleDataFromOutSide() }

    protected abstract suspend fun handleDataFromOutSide()

    suspend fun <P> Flow<P>.updateState(
        block: (state: S, payload: P) -> S
    ) = coroutineScope{
        onEach { payload -> _state.emit(block(_state.value, payload)) }.launchIn(this)
    }

    suspend fun updateState(
        block: (state: S) -> S
    ) {
        _state.emit(block(_state.value))
    }
}