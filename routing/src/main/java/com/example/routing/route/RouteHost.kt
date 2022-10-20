package com.example.routing.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.routing.Path

class RouteHost(
    private val controller: NavHostController,
    private val screens: Map<Path, RouteInfo>,
) {

    @Composable
    fun CreateHost(
        startPath: Path
    ){
        NavHost(
            navController = controller,
            startDestination = screens[startPath]!!.route
        ) {
            screens.values.forEach { it.screen.invoke(this) }
        }
    }
}