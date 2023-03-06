package io.my.ui.theme

import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.compositionLocalOf

internal val LocalAppColors = compositionLocalOf<AppColors> {
    error("No LocalAppColors provided")
}

internal val LocalAppShape = compositionLocalOf<Shapes> {
    error("No LocalAppShape provided")
}

internal val LocalAppTypography = compositionLocalOf<Typography> {
    error("No LocalAppTypography provided")
}