package com.example.routing

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class RouteController private constructor(
    internal val controller: NavHostController,
    private val screens: Map<Path, ScreenInfo>,
    private val argument: Argument<String>
){
    @Composable
    internal fun SetupNavigate(
        startPath: Path
    ){
        NavHost(
            navController = controller,
            startDestination = screens[startPath]!!.route
        ){
            screens.values.forEach { screen ->
                composable(screen.route){
                    val arg = rememberArgument(
                        key = screen.route,
                        value = getArgument(screen.route)
                    )

                    screen.factory().create(arg).Content()
                }
            }
        }
    }

    internal fun getScreen(path: Path): Screen = screens[path]!!.let { info ->
        info.factory().create(getArgument(info.route))
    }

    internal fun navigate(path: Path, value: Any? = null){
        val info = screens[path]!!
        if (value != null) { setArgument(info.route, value) }

        controller.navigate(info.route)
    }

    internal fun pop(
        path: Path? = null,
        inclusive: Boolean = true,
        saveState: Boolean = false
    ){
        if (path == null) controller.popBackStack()
        else {
            val info = screens[path]!!
            controller.popBackStack(info.route, inclusive, saveState)
        }
    }

    private fun setArgument(key: String, value: Any){
        argument.set(key, value)
    }

    private fun getArgument(key: String): Any {
        return argument.get(key) ?: Unit
    }

    class Builder {
        private val map = hashMapOf<Path, ScreenInfo>()

        fun add(path: Path, screenInfo: ScreenInfo){
            map[path] = screenInfo
        }

        fun addMap(map: Map<Path,ScreenInfo>){
            this.map.putAll(map)
        }

        fun build(navHostController: NavHostController):RouteController{
            return RouteController(
                controller = navHostController,
                screens = map,
                argument = GoogleArgumentImpl()
            )
        }
    }
}