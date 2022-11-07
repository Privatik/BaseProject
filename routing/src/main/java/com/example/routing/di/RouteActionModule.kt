package com.example.routing.di

import androidx.navigation.NavHostController
import com.example.routing.Argument
import com.example.routing.RoutingAction
import com.example.routing.RoutingActionImpl
import com.example.routing.route.RouteController
import com.example.routing.ScreenData
import dagger.Module
import dagger.Provides

@Module
internal class RouteActionModule {

    @Provides
    fun provideRouteAction(
        navHostController: NavHostController,
        screenData: ScreenData,
        argument: Argument<String>
    ): RoutingAction{
        val routeController = RouteController(
            controller = navHostController,
            screenData = screenData,
            argument = argument
        )
        return RoutingActionImpl(routeController)
    }
}