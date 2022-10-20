package com.example.routing

import com.example.routing.route.Route
import com.example.routing.route.RouteManager

interface RoutingAction {

    fun navigate(route: Route)

}

enum class Path{
    FIRST_SCREEN,
    SECOND_SCREEN
}

internal class RoutingActionImpl(
    private val manager: RouteManager
): RoutingAction {

    override fun navigate(route: Route) {
        when (route) {
            Route.Back -> manager.pop()
            is Route.OpenNextScreen -> {
                manager.navigate(Path.SECOND_SCREEN, route.email)
            }
        }
    }
}