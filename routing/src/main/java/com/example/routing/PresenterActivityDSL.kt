package com.example.routing

import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.routing.di.DaggerRoutingComponent
import com.example.routing.di.RouteDependencies
import com.example.routing.route.RouteMaker
import com.io.navigation.PresenterComponentActivity
import com.io.navigation.PresenterCompositionLocalProvider
import io.my.core.GlobalDependencies
import io.my.ui.ProjectTheme

fun PresenterComponentActivity.setContentPerJetpack(
    startPath: Path,
    builder: RouteMaker.Builder.() -> Unit,
    globalDependencies: GlobalDependencies
){
    setContent {
        val controller = rememberNavController()

        val routeMaker = remember {
            RouteMaker.Builder()
                .apply(builder)
                .build()
        }

        val routeScope = remember<RouteDependencies> {
            DaggerRoutingComponent
                .builder()
                .navController(controller)
                .routeMaker(routeMaker)
                .build()
        }

        val arguments = remember { routeScope.arguments() }

        val presenterController = remember {
            GooglePresenterController(presenterStoreOwner, controller)
        }

        ProjectTheme {
            PresenterCompositionLocalProvider(
                controller = presenterController,
                owner = presenterStoreOwner,
                canUpdate = true,
                canSaveStateKey = true
            ) {
                NavHost(
                    navController = controller,
                    startDestination = routeMaker.getInfo(startPath)!!.route
                ) {
                    routeMaker.getScreens().forEach { screen ->
                        composable(screen.route) {
                            val arg = rememberArgument(
                                key = screen.route,
                                value = arguments.get(screen.route) ?: Unit
                            )

                            screen.create(routeScope.action(), globalDependencies, arg).Content()
                        }
                    }
                }
            }
        }
    }
}