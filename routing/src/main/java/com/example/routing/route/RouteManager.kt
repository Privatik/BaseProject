package com.example.routing.route

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.routing.*
import com.example.routing.Argument
import com.example.routing.GoogleArgumentImpl
import com.example.routing.rememberArgument
import kotlin.properties.Delegates

class RouteManager private constructor(
    private val controller: RouteController,
    private val host: RouteHost
) {

    @Composable
    internal fun InstanceNavHost(startPath: Path) = host.CreateHost(startPath = startPath)

    internal fun navigate(path: Path, value: Any? = null) = controller.navigate(path, value)

    internal fun pop(
        path: Path? = null,
        inclusive: Boolean = true,
        saveState: Boolean = false
    ) = controller.pop(path, inclusive, saveState)

    class Builder() {
        private val argument: Argument<String> by lazy(LazyThreadSafetyMode.NONE) {
            GoogleArgumentImpl()
        }
        private var routeAction: RoutingAction by Delegates.notNull()
        private val map = hashMapOf<Path, RouteInfo>()

        private fun ScreenInfo.asRoute(): RouteInfo {
            return RouteInfo(
                route = route,
                screen = {
                    val info = this@asRoute
                    composable(info.route) {
                        val arg = rememberArgument(
                            key = info.route,
                            value = argument.get(info.route) ?: Unit
                        )

                        info.factory(routeAction).create(arg).Content()
                    }
                }
            )
        }

        fun add(path: Path, screenInfo: ScreenInfo){
            map[path] = screenInfo.asRoute()
        }

        fun addAll(map: Map<Path, ScreenInfo>){
            this.map.putAll(map.map { it.key to it.value.asRoute() })
        }

        fun build(navHostController: NavHostController): RouteManager {

            val controller = RouteController(
                controller = navHostController,
                screens = map,
                argument = argument
            )

            val host = RouteHost(
                controller = navHostController,
                screens = map
            )

            return RouteManager(
                controller = controller,
                host = host
            ).also { routeAction = RoutingActionImpl(it) }
        }
    }
}