package io.my.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
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
            LaunchedEffect(palette.isDark) {
                sysUiController.setSystemBarsColor(
                    color = Color.Transparent,
                    darkIcons = palette.isDark
                )
                sysUiController.setNavigationBarColor(
                    color = Color.Transparent,
                    darkIcons = palette.isDark
                )
                sysUiController.navigationBarDarkContentEnabled = false
            }
            ProvideWindowInsets(
                consumeWindowInsets = false,
                content = content
            )
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

