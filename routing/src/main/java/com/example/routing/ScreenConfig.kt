package com.example.routing

import com.example.routing.route.Path

class ScreenConfig private constructor(
    private val screens: Map<Path, ScreenInfo>,
){

    internal fun getInfo(path: Path): ScreenInfo? = screens[path]
    internal fun getScreens(): Collection<ScreenInfo> = screens.values

    class Builder() {
        private val map = hashMapOf<Path, ScreenInfo>()

        fun screens(infoForScreen: Set<ScreenInfo>){
            this.map.putAll(infoForScreen.map { it.path to it })
        }

        internal fun build(): ScreenConfig {
            return ScreenConfig(map)
        }
    }

}