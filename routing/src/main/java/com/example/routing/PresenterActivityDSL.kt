package com.example.routing

import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.bumble.appyx.navmodel.backstack.BackStack
import com.example.routing.di.DaggerRoutingComponent
import com.example.routing.di.RouteDependencies
import com.example.routing.presenter.BasePresenterAdapter
import com.example.routing.presenter.BasePresenterStoreOwner
import com.io.navigation_common.UIPresenter
import io.my.core.domain.DomainProvider
import io.my.data.di.CoreDataDependencies
import io.my.ui.presenter.AssistedPresenterFactory
import io.my.ui.presenter.MyPresenter
import io.my.ui.theme.ProjectTheme

fun NodeActivity.setContentPerJetpack(
    startPath: Path,
    builder: ScreenConfig.Builder<Path>.() -> Unit,
    coreDataDependencies: CoreDataDependencies
){
    setContent {
        val screenConfig = remember {
            ScreenConfig.Builder<Path>()
                .apply(builder)
                .build()
        }

        ProjectTheme {
            NodeHost(integrationPoint = appyxIntegrationPoint) { buildContext ->
                val backStack: BackStack<Path> = BackStack(
                    initialElement = startPath,
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

                BaseHost(
                    buildContext = buildContext,
                    routeActionHandler = routeDependencies.action(),
                    backStack = backStack,
                    presenterOwner = owner,
                    getFactoryForScreenAndCreateScopeByPath = { path ->
                        screenConfig.getInfo(path)!!.let { info ->
                            info.scope?.also { createPresenterScope ->
                                val presenter = createPresenterScope{ providerClazz ->
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