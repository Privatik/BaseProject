package io.my.ui

import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Cyan
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import io.my.ui.palettes.DarkColorPalette
import io.my.ui.palettes.LightColorPalette

@Composable
fun ProjectTheme(
    colorTheme: ColorForTheme = if (isSystemInDarkTheme()) ColorForTheme.Dark else ColorForTheme.Light,
    content: @Composable () -> Unit
) {
    Log.d("ReCompose","ProjectTheme")

    val sysUiController = rememberSystemUiController()
    SideEffect {
        sysUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = !colorTheme.palette.isDark,
        )
    }

    MaterialTheme(
        colors = defaultColor,
        typography = Typography,
        shapes = Shapes,
    ) {
        CompositionLocalProvider(
            LocalAppColors provides colorTheme.palette,
            content = content
        )
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

}

private val DefaultColor = Cyan
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

