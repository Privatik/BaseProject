package com.example.routing.di

import androidx.navigation.NavHostController
import com.example.routing.route.RouteMaker
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [ArgumentModule::class, RouteActionModule::class]
)
@Singleton
internal interface RoutingComponent: RouteInsideDependencies, RouteDependencies {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun navController(navHostController: NavHostController): Builder

        @BindsInstance
        fun routeMaker(routeMaker: RouteMaker): Builder

        fun build(): RoutingComponent
    }
}