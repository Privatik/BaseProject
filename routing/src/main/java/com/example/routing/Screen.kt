package com.example.routing

import androidx.compose.runtime.Composable

abstract class Screen(protected val routingAction: RoutingAction){

    @Composable
    abstract fun Content()

    interface Factory{
         fun <A: Any> create(
            arg: A
        ): Screen
    }

}

interface ScreenInfo{
    val route: String
    val factory: () -> Screen.Factory
}