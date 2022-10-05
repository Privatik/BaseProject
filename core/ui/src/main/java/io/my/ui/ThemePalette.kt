package io.my.ui

import io.my.ui.palettes.DarkColorPalette
import io.my.ui.palettes.LightColorPalette

sealed class ColorForTheme(
    internal val palette: AppColors
){
    object Light: ColorForTheme(LightColorPalette)
    object Dark: ColorForTheme(DarkColorPalette)

}