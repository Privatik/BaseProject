package com.example.routing

import androidx.activity.compose.setContent
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.lifecycle.lifecycleScope
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.bumble.appyx.navmodel.backstack.BackStack
import com.example.routing.di.DaggerRoutingComponent
import com.example.routing.di.RouteDependencies
import com.example.routing.extensions.DefaultEffectExtension
import com.example.routing.initialize_helper.InitialController
import com.example.routing.initialize_helper.InitialPathHelper
import com.example.routing.presenter.BasePresenterAdapter
import com.example.routing.presenter.BasePresenterStoreOwner
import io.my.core.domain.DomainProvider
import io.my.data.di.CoreDataDependencies
import io.my.ui.presenter.AssistedPresenterFactory
import io.my.ui.presenter.MyPresenter
import io.my.ui.theme.ProjectTheme

fun NodeActivity.setNavigationContent(
    builder: ScreenConfig.Builder<Path>.() -> Unit,
    coreDataDependencies: CoreDataDependencies,
    initialController: InitialController,
){
    val initialPathHelper = InitialPathHelper(
        scope = lifecycleScope
    )

    initialController.stopInitialize()

    setContent {
        val screenConfig = remember {
            ScreenConfig.Builder<Path>()
                .apply(builder)
                .build()
        }

        var isReadyForDisplayedContent by remember {
            mutableStateOf(false)
        }

        LaunchedEffect(Unit){
            initialPathHelper.calculateInitialPath {
                isReadyForDisplayedContent = true
            }
        }

        if (isReadyForDisplayedContent){
            ProjectTheme {
                NodeHost(integrationPoint = appyxIntegrationPoint) { buildContext ->
                    val backStack: BackStack<Path> = BackStack(
                        initialElement = initialPathHelper.getInitialPath(),
                        savedStateMap = buildContext.savedStateMap
                    )

                    val owner = BasePresenterStoreOwner(
                        BasePresenterAdapter(backStack)
                    )

                    val routeDependencies: RouteDependencies = DaggerRoutingComponent
                        .builder()
                        .coreDataDependencies(coreDataDependencies)
                        .bacKStack(backStack)
                        .build()

                    val extensions = listOf(
                        DefaultEffectExtension(
                            routeActionHandler = routeDependencies.action(),
                            snackbarHostState = SnackbarHostState(),
                        ),
                    )


                    BaseHost(
                        buildContext = buildContext,
                        routeActionHandler = routeDependencies.action(),
                        backStack = backStack,
                        owner = owner,
                        baseExtensions = extensions,
                        initialController = initialController,
                        getFactoryForScreenAndCreateScopeByPath = { path ->
                            screenConfig.getInfo(path)!!.let { info ->
                                info.scope?.also { createPresenterScope ->
                                    val presenter = createPresenterScope(path) { providerClazz ->
                                        @Suppress("UNCHECKED_CAST")
                                        routeDependencies.domainDependenciesProvider[providerClazz.java]!!.get() as DomainProvider<Any>
                                    }

                                    owner.cleanGarbageIntoStoreAndCreatePresenter<MyPresenter>(
                                        clazz = presenter::class.java,
                                        factory = AssistedPresenterFactory{ presenter },
                                        isShared = true
                                    )
                                }

                                info.screenFactory(path)
                            }
                        },
                    )
                }
            }
        }
    }
}