package com.example.routing

import androidx.compose.runtime.Composable

abstract class Screen(protected val routingAction: RoutingAction){

    @Composable
    abstract fun Content()

    interface Factory{
        val route: String

         fun <A: Any> create(
             routingAction: RoutingAction,
            arg: A
        ): Screen
    }

}