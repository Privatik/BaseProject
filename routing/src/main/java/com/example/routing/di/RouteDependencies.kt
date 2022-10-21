package com.example.routing.di

import com.example.routing.Argument
import com.example.routing.RoutingAction

interface RouteDependencies {
    fun action(): RoutingAction
}

internal interface RouteInsideDependencies {
    fun arguments(): Argument<String>
}