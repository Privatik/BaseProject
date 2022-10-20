package com.example.routing.di

import com.example.routing.route.RouteManager
import com.example.routing.RoutingAction
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [RouteModule::class]
)
@Singleton
interface RoutingComponent: RouteDependencies {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun instanceRoute(manager: RouteManager): Builder

        fun build(): RoutingComponent
    }
}