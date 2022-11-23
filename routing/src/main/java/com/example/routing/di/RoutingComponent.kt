package com.example.routing.di

import com.bumble.appyx.navmodel.backstack.BackStack
import com.example.routing.Path
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [RouteActionModule::class]
)
@Singleton
internal interface RoutingComponent: RouteDependencies {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bacKStack(backStack: BackStack<Path>): Builder

        fun build(): RoutingComponent
    }
}