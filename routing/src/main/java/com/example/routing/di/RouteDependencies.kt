package com.example.routing.di

import com.example.routing.Argument
import com.example.routing.RoutingAction

internal interface RouteDependencies {
    fun action(): RoutingAction
    fun arguments(): Argument<String>
}