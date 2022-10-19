package com.example.routing.di



import com.example.routing.RouteController
import com.example.routing.RoutingAction
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

interface RouteDependencies{
    fun route(): RoutingAction
}

@Component(
    modules = [RouteModule::class]
)
@Singleton
interface RoutingComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun route(controller: RouteController): Builder

        fun build(): RoutingComponent
    }
}