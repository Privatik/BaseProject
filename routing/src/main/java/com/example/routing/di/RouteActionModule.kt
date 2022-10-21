package com.example.routing.di

import androidx.navigation.NavHostController
import com.example.routing.Argument
import com.example.routing.RoutingAction
import com.example.routing.RoutingActionImpl
import com.example.routing.route.RouteController
import com.example.routing.route.RouteMaker
import dagger.Module
import dagger.Provides

@Module
internal class RouteActionModule {

    @Provides
    fun provideRouteAction(
        navHostController: NavHostController,
        maker: RouteMaker,
        argument: Argument<String>
    ): RoutingAction{
        val routeController = RouteController(
            controller = navHostController,
            routeMaker = maker,
            argument = argument
        )
        return RoutingActionImpl(routeController)
    }
}