package com.example.routing

import android.util.Log
import com.example.routing.route.Route
import com.example.routing.route.RouteController

interface RoutingAction {

    fun navigate(route: Route)

}

enum class Path{
    FIRST_SCREEN,
    SECOND_SCREEN
}

internal class RoutingActionImpl(
    private val manager: RouteController
): RoutingAction {

    override fun navigate(route: Route) {
        Log.d("Navigate","Open by $route")
        when (route) {
            Route.Back -> manager.pop()
            is Route.OpenNextScreen -> {
                manager.navigate(Path.SECOND_SCREEN, route.email)
            }
        }
    }
}