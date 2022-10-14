package com.example.routing

import android.util.Log
import androidx.navigation.NavController

interface Routing {

    fun navigate(route: Route)

}

enum class Path{
    FIRST_SCREEN,
    SECOND_SCREEN
}

internal class RoutingImpl(
   private val navController: NavController,
   private val screens: Map<Path, ScreenInfo>,
   private val argument: Argument<String>
): Routing {

    override fun navigate(route: Route) {
        when (route) {
            Route.Back -> navController.popBackStack()
            is Route.OpenNextScreen -> {
                val info = screens.getValue(Path.SECOND_SCREEN)
                Log.d("Compose","save ${info.route} = ${route.message}")
                argument.set(info.route, route.message)
                navController.navigate(info.route)
            }
        }

    }
}