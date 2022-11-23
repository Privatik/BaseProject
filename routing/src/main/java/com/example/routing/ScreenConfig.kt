package com.example.routing

import kotlin.reflect.KClass

class ScreenConfig<P: Path> private constructor(
    private val screens: Map<KClass<out P>, ScreenInfo<out P>>,
){

    @Suppress("UNCHECKED_CAST")
    internal fun getInfo(path: P): ScreenInfo<P>? = screens[path::class] as? ScreenInfo<P>

    class Builder<P: Path>() {
        private val map = hashMapOf<KClass<out P>, ScreenInfo<out P>>()

        fun screens(infoForScreen: Set<ScreenInfo<P>>){
            this.map.putAll(infoForScreen.map { it.path to it })
        }

        internal fun build(): ScreenConfig<P> {
            return ScreenConfig(map)
        }
    }

}