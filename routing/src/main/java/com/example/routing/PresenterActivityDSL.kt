package com.example.routing

import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import com.bumble.appyx.core.integration.NodeHost
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.bumble.appyx.navmodel.backstack.BackStack
import com.example.routing.di.DaggerRoutingComponent
import com.example.routing.di.RouteDependencies
import com.example.routing.managers.DomainDependencyManagerImpl
import com.example.routing.presenter.BasePresenterAdapter
import com.example.routing.presenter.BasePresenterStoreOwner
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation.presenter
import com.io.navigation.sharedPresenter
import com.io.navigation_common.UIPresenter
import io.my.core.AssistedPresenterFactory
import io.my.data.di.CoreDataDependencies
import io.my.ui.ProjectTheme

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

                val domainDependencyManager = DomainDependencyManagerImpl(coreDataDependencies)

                val routeDependencies = DaggerRoutingComponent
                    .builder()
                    .bacKStack(backStack)
                    .build()

                BaseHost(
                    buildContext = buildContext,
                    backStack = backStack,
                    presenterOwner = owner,
                    getFactoryForScreenAndCreateScopeByPath = { path ->
                        screenConfig.getInfo(path)!!.let { info ->
                            info.scope?.run {
                                val presenter = this(routeDependencies.action(), domainDependencyManager.getByPath(path))

                                owner.cleanGarbageIntoStoreAndCreatePresenter<UIPresenter>(
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