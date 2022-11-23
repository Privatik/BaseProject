package com.example.routing.route

import android.util.Log
import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import com.example.routing.Path

interface RouteAction {

    fun navigate(route: Route)

}

internal class RouteActionImpl(
    private val backStack: BackStack<Path>
): RouteAction {

    override fun navigate(route: Route) {
        when (route) {
            Route.Back -> { backStack.pop() }
            is Route.OpenNextScreen -> {
                backStack.push(Path.SecondScreen(route.email))
            }
        }
    }
}