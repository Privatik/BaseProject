package io.my.ui.effect

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import io.my.ui.presenter.Presenter

interface Effect

interface EffectHandler {

    suspend fun emit(effect: Effect)
}

@Suppress("NOTHING_TO_INLINE")
@Composable
inline fun Presenter<*, *, Effect>.HandleEffects(){
    val effectHandler = LocalEffectHandler.current
    LaunchedEffect(Unit){
        this@HandleEffects.singleEffect.collect{
            effectHandler.emit(it)
        }
    }
}