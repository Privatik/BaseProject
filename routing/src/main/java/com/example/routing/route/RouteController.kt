package com.example.routing.route

import androidx.navigation.NavHostController
import com.example.routing.Argument
import com.example.routing.Path
import com.example.routing.ScreenData

internal class RouteController(
    private val controller: NavHostController,
    private val screenData: ScreenData,
    private val argument: Argument<String>
){

    fun navigate(path: Path, value: Any? = null){
        val info = screenData.getInfo(path)!!
        if (value != null) { argument.set(info.routeForNavigation, value) }

        controller.navigate(info.routeForNavigation)
    }

    fun pop(
        path: Path? = null,
        inclusive: Boolean = true,
        saveState: Boolean = false
    ){
        if (path == null) controller.popBackStack()
        else {
            val info = screenData.getInfo(path)!!
            controller.popBackStack(info.routeForNavigation, inclusive, saveState)
        }
    }

}