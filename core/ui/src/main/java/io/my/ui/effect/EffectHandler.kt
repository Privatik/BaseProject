package io.my.ui.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import io.my.ui.presenter.Presenter

interface Effect

interface EffectHandler {

    suspend fun emit(effect: Effect)
}

@Composable
inline fun Presenter<*, *, Effect>.handleEffects(){
    val effectHandler = LocalEffectHandler.current
    LaunchedEffect(Unit){
        this@handleEffects.singleEffect.collect{
            effectHandler.emit(it)
        }
    }
}