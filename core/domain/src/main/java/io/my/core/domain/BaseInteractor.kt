package io.my.core.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseInteractor<S: Any>(state: S) {
    private val _state = MutableStateFlow(state)
    val state = _state.asStateFlow()

    suspend fun updateState(block: (S) -> S){
        _state.emit(block(state.value))
    }

}