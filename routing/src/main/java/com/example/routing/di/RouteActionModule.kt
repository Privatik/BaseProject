package com.example.routing.di

import androidx.navigation.NavHostController
import com.example.routing.managers.ArgumentsManager
import com.example.routing.route.RouteAction
import com.example.routing.route.RouteActionImpl
import com.example.routing.route.RouteController
import com.example.routing.ScreenConfig
import dagger.Module
import dagger.Provides

@Module
internal class RouteActionModule {

    @Provides
    fun provideRouteAction(
        navHostController: NavHostController,
        screenConfig: ScreenConfig,
        argumentsManager: ArgumentsManager<String>
    ): RouteAction {
        val routeController = RouteController(
            controller = navHostController,
            screenConfig = screenConfig,
            argumentsManager = argumentsManager
        )
        return RouteActionImpl(routeController)
    }
}