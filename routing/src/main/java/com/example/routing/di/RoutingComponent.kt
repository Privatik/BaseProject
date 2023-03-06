package com.example.routing.di

import com.bumble.appyx.navmodel.backstack.BackStack
import com.example.routing.Path
import dagger.BindsInstance
import dagger.Component
import io.my.core.domain.DomainProvider
import io.my.data.di.CoreDataDependencies
import io.my.ui.presenter.Presenter
import javax.inject.Provider
import javax.inject.Singleton

@Component(
    modules = [
        RouteActionModule::class,
        DomainDependencyModule::class,
        DataDependencyModule::class
    ]
)
@Singleton
internal interface RoutingComponent: RouteDependencies {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun bacKStack(backStack: BackStack<Path>): Builder

        @BindsInstance
        fun coreDataDependencies(coreDataDependencies: CoreDataDependencies): Builder

        fun build(): RoutingComponent
    }
}