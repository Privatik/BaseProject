package com.example.routing.di

import com.example.routing.RoutingAction

interface RouteDependencies{
    fun route(): RoutingAction
}