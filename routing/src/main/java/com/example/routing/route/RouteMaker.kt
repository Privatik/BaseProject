package com.example.routing.route

import com.example.routing.Path
import com.example.routing.Screen

class RouteMaker private constructor(
    private val screens: Map<Path, Screen.Factory>,
){

    fun getInfo(path: Path): Screen.Factory? = screens[path]
    fun getScreens(): Collection<Screen.Factory> = screens.values

    class Builder() {
        private val map = hashMapOf<Path, Screen.Factory>()

        fun screens(map: Map<Path, Screen.Factory>){
            this.map.putAll(map)
        }

        internal fun build(): RouteMaker{
            return RouteMaker(map)
        }
    }

}