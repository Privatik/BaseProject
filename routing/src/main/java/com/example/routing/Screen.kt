package com.example.routing

import androidx.compose.runtime.Composable
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

interface ScreenInfo{
    val path: Path
    val routeForNavigation: String

    val scopeKClazz: KClass<out UIPresenter>

    val screenFactory: () -> Screen.Factory
    val scopeInPresenter:
                (routingAction: RoutingAction, domainDependencies: DomainDependencies) -> UIPresenter
}