package com.example.routing.route

import androidx.navigation.NavHostController
import com.example.routing.managers.ArgumentsManager
import com.example.routing.ScreenConfig

internal class RouteController(
    private val controller: NavHostController,
    private val screenConfig: ScreenConfig,
    private val argumentsManager: ArgumentsManager<String>
){

    fun navigate(path: Path, value: Any? = null){
        val info = screenConfig.getInfo(path)!!
        if (value != null) { argumentsManager.set(info.routeForNavigation, value) }

        controller.navigate(info.routeForNavigation)
    }

    fun pop(
        path: Path? = null,
        inclusive: Boolean = true,
        saveState: Boolean = false
    ){
        if (path == null) controller.popBackStack()
        else {
            val info = screenConfig.getInfo(path)!!
            controller.popBackStack(info.routeForNavigation, inclusive, saveState)
        }
    }

}