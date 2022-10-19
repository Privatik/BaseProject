package com.example.routing.di

import com.example.routing.RouteController
import com.example.routing.RoutingAction
import com.example.routing.RoutingActionImpl
import dagger.Module
import dagger.Provides

@Module()
class RouteModule {

    @Provides
    fun provideRoute(routeController: RouteController): RoutingAction{
        return RoutingActionImpl(routeController)
    }
}