package com.example.routing.di

import com.example.routing.route.RouteManager
import com.example.routing.RoutingAction
import com.example.routing.RoutingActionImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class RouteModule {

    @Provides
    @Singleton
    fun provideRoute(routeManager: RouteManager): RoutingAction{
        return RoutingActionImpl(routeManager)
    }
}