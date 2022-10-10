package io.my.ui

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.my.ui.palettes.DefaultPalette

@Composable
fun ProjectTheme(
    colorTheme: ColorForTheme = if (isSystemInDarkTheme()) ColorForTheme.Dark else ColorForTheme.Light,
    content: @Composable () -> Unit
) {
    val palette = DefaultPalette
    palette.updateColors(colorTheme)

    MaterialTheme(
        colors = defaultColor
    ) {
        CompositionLocalProvider(
            LocalAppColors provides palette,
            LocalAppTypography provides AppTypography,
            LocalAppShape provides AppShapes,
        ) {
            val sysUiController = rememberSystemUiController()
            LaunchedEffect(palette.isDark){
                sysUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = palette.isDark
                )
            }
            content()
        }
    }
}

fun AppColors.updateColors(
    colorTheme: ColorForTheme
){
    update(colorTheme.palette)
}

object ProjectTheme {

    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalAppColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = LocalAppTypography.current

    val shape: Shapes
        @Composable
        @ReadOnlyComposable
        get() = LocalAppShape.current

}

private val defaultColor = Colors(
    primary = DefaultColor,
    primaryVariant = DefaultColor,
    secondary = DefaultColor,
    secondaryVariant = DefaultColor,
    background = DefaultColor,
    surface = DefaultColor,
    error = DefaultColor,
    onPrimary = DefaultColor,
    onSecondary = DefaultColor,
    onBackground = DefaultColor,
    onSurface = DefaultColor,
    onError = DefaultColor,
    isLight = true
)

