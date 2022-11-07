package com.example.routing

class ScreenData private constructor(
    private val screens: Map<Path, ScreenInfo>,
){

    fun getInfo(path: Path): ScreenInfo? = screens[path]
    fun getScreens(): Collection<ScreenInfo> = screens.values

    class Builder() {
        private val map = hashMapOf<Path, ScreenInfo>()

        fun screens(infoForScreen: Set<ScreenInfo>){
            this.map.putAll(infoForScreen.map { it.path to it })
        }

        internal fun build(): ScreenData {
            return ScreenData(map)
        }
    }

}