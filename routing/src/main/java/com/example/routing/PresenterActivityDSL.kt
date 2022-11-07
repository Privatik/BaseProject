package com.example.routing

import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.routing.di.DaggerRoutingComponent
import com.example.routing.di.RouteDependencies
import com.io.navigation.PresenterComponentActivity
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation.presenter
import com.io.navigation_common.UIPresenter
import io.my.core.AssistedPresenterFactory
import io.my.core.DomainDependencies
import io.my.core.GlobalDependencies
import io.my.ui.ProjectTheme

fun PresenterComponentActivity.setContentPerJetpack(
    startPath: Path,
    builder: ScreenData.Builder.() -> Unit,
    globalDependencies: GlobalDependencies
){
    setContent {
        val controller = rememberNavController()

        val screenData = remember {
            ScreenData.Builder()
                .apply(builder)
                .build()
        }

        val routeScope = remember<RouteDependencies> {
            DaggerRoutingComponent
                .builder()
                .navController(controller)
                .routeMaker(screenData)
                .build()
        }

        val arguments = remember { routeScope.arguments() }

        val presenterController = remember {
            GooglePresenterController(presenterStoreOwner, controller)
        }

        val domainDependency = remember {
            DomainDependency(globalDependencies)
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
                    startDestination = screenData.getInfo(startPath)!!.routeForNavigation
                ) {
                    screenData.getScreens().forEach { screen ->
                        composable(screen.routeForNavigation) {
                            val arg = rememberArgument(
                                key = screen.routeForNavigation,
                                value = arguments.get(screen.routeForNavigation) ?: Unit
                            )

                            presenter<UIPresenter>(
                                factory = AssistedPresenterFactory {
                                    screen.scopeInPresenter(
                                        routeScope.action(),
                                        domainDependency.getByPath(screen.path)
                                    )
                                },
                                clazz = screen.scopeKClazz.java,
                                isShared = true
                            )

                            screen.screenFactory().create(arg).Content()
                        }
                    }
                }
            }
        }
    }
}