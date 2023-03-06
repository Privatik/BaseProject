package com.example.routing

import com.example.routing.route.Route
import io.my.ui.effect.Effect
import io.my.ui.effect.EffectHandler
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class DefaultEffectHandler: EffectHandler {
    private val _effectFlow = MutableSharedFlow<Effect>()
    val effectFlow = _effectFlow.asSharedFlow()

    override suspend fun emit(effect: Effect) { _effectFlow.emit(effect) }

    sealed interface DefaultEffect: Effect{
        data class Navigate(val route: Route): DefaultEffect
        data class SnackBar(val message: String): DefaultEffect
    }
}