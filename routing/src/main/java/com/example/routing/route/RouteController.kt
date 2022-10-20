package com.example.routing.route

import androidx.navigation.NavHostController
import com.example.routing.Argument
import com.example.routing.Path

internal class RouteController constructor(
    val controller: NavHostController,
    private val screens: Map<Path, RouteInfo>,
    private val argument: Argument<String>
){

    internal fun navigate(path: Path, value: Any? = null){
        val info = screens[path]!!
        if (value != null) { argument.set(info.route, value) }

        controller.navigate(info.route)
    }

    internal fun pop(
        path: Path? = null,
        inclusive: Boolean = true,
        saveState: Boolean = false
    ){
        if (path == null) controller.popBackStack()
        else {
            val info = screens[path]!!
            controller.popBackStack(info.route, inclusive, saveState)
        }
    }

}