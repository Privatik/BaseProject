package com.example.routing.di

import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.navmodel.backstack.BackStack
import com.example.routing.Path
import com.example.routing.route.RouteAction
import com.example.routing.route.RouteActionImpl
import com.example.routing.ScreenConfig
import dagger.Module
import dagger.Provides

@Module
internal class RouteActionModule {

    @Provides
    fun provideRouteAction(
        backStack: BackStack<Path>
    ): RouteAction {
        return RouteActionImpl(backStack)
    }
}