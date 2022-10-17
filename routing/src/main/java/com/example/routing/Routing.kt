package com.example.routing

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavHostController

interface Routing {

    fun navigate(route: Route)

}

enum class Path{
    FIRST_SCREEN,
    SECOND_SCREEN
}

internal class RoutingImpl(
    private val controller: NavHostController,
    private val screens: Map<Path, ScreenInfo>,
    private val argument: Argument<String>
): Routing {

    override fun navigate(route: Route) {
        when (route) {
            Route.Back -> controller.popBackStack()
            is Route.OpenNextScreen -> {
                val info = screens.getValue(Path.SECOND_SCREEN)
                argument.set(info.route, route.message)
                controller.navigate(info.route)
            }
        }

    }
}