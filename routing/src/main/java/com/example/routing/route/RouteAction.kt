package com.example.routing.route

import android.util.Log

interface RouteAction {

    fun navigate(route: Route)

}

enum class Path{
    FIRST_SCREEN,
    SECOND_SCREEN
}

internal class RouteActionImpl(
    private val manager: RouteController
): RouteAction {

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