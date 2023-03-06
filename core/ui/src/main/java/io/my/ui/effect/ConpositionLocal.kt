package io.my.ui.effect

import androidx.compose.runtime.compositionLocalOf

val LocalEffectHandler = compositionLocalOf<EffectHandler> {
    error("No LocalEffectHandler provided")
}