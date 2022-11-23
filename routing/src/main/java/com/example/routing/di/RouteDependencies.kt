package com.example.routing.di

import com.example.routing.route.RouteAction

internal interface RouteDependencies {
    fun action(): RouteAction
}