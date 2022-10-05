package io.my.ui

import androidx.compose.runtime.compositionLocalOf

internal val LocalAppColors = compositionLocalOf<AppColors> {
    error("No LocalAppColors provided")
}