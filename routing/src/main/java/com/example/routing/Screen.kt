package com.example.routing

import androidx.compose.runtime.Composable
import io.my.core.DependenciesPresenterFactory
import io.my.core.GlobalDependencies

abstract class Screen(
    protected val routingAction: RoutingAction,
    protected val scopeFactory: DependenciesPresenterFactory
){

    @Composable
    abstract fun Content()

    interface Factory{
        val route: String

         fun <A: Any> create(
             routingAction: RoutingAction,
             dependencies: GlobalDependencies,
            arg: A
        ): Screen
    }

}