package com.example.routing.di

import com.bumble.appyx.navmodel.backstack.BackStack
import com.example.routing.Path
import com.example.routing.route.RouteActionHandler
import com.example.routing.route.RouteActionHandlerImpl
import dagger.Module
import dagger.Provides

@Module
internal class RouteActionModule {

    @Provides
    fun provideRouteAction(
        backStack: BackStack<Path>
    ): RouteActionHandler {
        return RouteActionHandlerImpl(backStack)
    }
}