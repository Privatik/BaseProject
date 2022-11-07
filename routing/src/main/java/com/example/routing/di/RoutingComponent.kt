package com.example.routing.di

import androidx.navigation.NavHostController
import com.example.routing.ScreenData
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [ArgumentModule::class, RouteActionModule::class]
)
@Singleton
internal interface RoutingComponent: RouteDependencies {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun navController(navHostController: NavHostController): Builder

        @BindsInstance
        fun routeMaker(screenData: ScreenData): Builder

        fun build(): RoutingComponent
    }
}