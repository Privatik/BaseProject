package com.example.routing.route

import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import com.example.routing.Path

interface RouteActionHandler {

    fun navigate(route: Route)

}

internal class RouteActionHandlerImpl(
    private val backStack: BackStack<Path>
): RouteActionHandler {

    override fun navigate(route: Route) {
        when (route) {
            Route.Back -> { backStack.pop() }
            is Route.OpenNextScreen -> {
                backStack.push(Path.SecondScreen(route.email))
            }
        }
    }
}