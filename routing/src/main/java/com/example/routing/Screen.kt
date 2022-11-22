package com.example.routing

import androidx.compose.runtime.Composable
import com.bumble.appyx.core.node.Node
import com.example.routing.route.Path
import com.example.routing.route.RouteAction
import com.io.navigation_common.UIPresenter
import io.my.core.DomainDependencies
import kotlin.reflect.KClass

abstract class Screen(){

    @Composable
    abstract fun Content()

    interface Factory{
         fun <A: Any> create(
            arg: A
        ): Screen
    }

}

interface NodeFactory{
    fun create(
        buildConfig: BuildConfig
    ): Node
}

interface ScreenInfo{
    val path: Path
    val routeForNavigation: String
    val scopeKClazz: KClass<out UIPresenter>?

    val screenFactory: () -> Screen.Factory
    val scopeInPresenter: (routeAction: RouteAction, domainDependencies: DomainDependencies) -> UIPresenter
}