package com.example.routing

import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import com.example.routing.initialize_helper.InitialController
import com.example.routing.route.RouteActionHandler
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation_common.PresenterStoreOwner
import io.my.ui.effect.LocalEffectHandler
import io.my.ui.theme.ProjectTheme
import kotlinx.coroutines.launch

internal class BaseHost (
    buildContext: BuildContext,
    private val routeActionHandler: RouteActionHandler,
    private val getFactoryForScreenAndCreateScopeByPath: (Path) -> Screen.Factory,
    private val owner: PresenterStoreOwner<NavKey<Path>>,
    private val backStack: BackStack<Path>,
    private val initialController: InitialController,
    private val baseExtensions: List<BaseExtension<*>>,
): ParentNode<Path>(
    buildContext = buildContext,
    navModel = backStack
) {

    override fun resolve(navTarget: Path, buildContext: BuildContext): Node {
        return SimpleScreen(
            buildContext = buildContext,
            factory = getFactoryForScreenAndCreateScopeByPath(navTarget),
        )
    }

    @Composable
    override fun View(modifier: Modifier) {

        PresenterCompositionLocalProvider(
            owner = owner,
            providers = baseExtensions.mapNotNull { it.provider() }.toTypedArray()
        ) {
            Box {
                Children(
                    modifier = modifier
                        .background(ProjectTheme.colors.backgroundPrimary)
                        .imePadding(),
                    navModel = backStack,
                    transitionHandler = rememberBackstackFader(transitionSpec = { spring() })
                )
                baseExtensions.forEach {
                    it.Content(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .navigationBarsPadding()
                    )
                }
                LaunchedEffect(Unit){ initialController.continueInitialize() }
            }
        }
    }
}