package com.example.routing

interface RoutingAction {

    fun navigate(route: Route)

}

enum class Path{
    FIRST_SCREEN,
    SECOND_SCREEN
}

internal class RoutingActionImpl(
    private val controller: RouteController
): RoutingAction {

    override fun navigate(route: Route) {
        when (route) {
            Route.Back -> controller.pop()
            is Route.OpenNextScreen -> {
                controller.navigate(Path.SECOND_SCREEN, route.email)
            }
        }

    }
}