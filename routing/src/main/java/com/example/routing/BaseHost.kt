package com.example.routing

import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
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
    private val backStack: BackStack<Path>,
    private val presenterOwner: PresenterStoreOwner<NavKey<Path>>
): ParentNode<Path>(
    buildContext = buildContext,
    navModel = backStack
) {

    override fun resolve(navTarget: Path, buildContext: BuildContext): Node {
        return getFactoryForScreenAndCreateScopeByPath(navTarget).create(buildContext)
    }

    @Composable
    override fun View(modifier: Modifier) {
        val effectHandler = remember { DefaultEffectHandler() }
        val snackbarHostState = remember { SnackbarHostState() }
        val colors = ProjectTheme.colors

        LaunchedEffect(Unit){
            effectHandler.effectFlow.collect{ effect ->
                val defEffect = effect as DefaultEffectHandler.DefaultEffect
                when (defEffect){
                    is DefaultEffectHandler.DefaultEffect.Navigate -> {
                        routeActionHandler.navigate(defEffect.route)
                    }
                    is DefaultEffectHandler.DefaultEffect.SnackBar -> {
                        snackbarHostState.showSnackbar(defEffect.message)
                    }
                }
            }
        }

        PresenterCompositionLocalProvider(
            owner = presenterOwner,
            providers = arrayOf(LocalEffectHandler provides effectHandler)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
            ) {
                Children(
                    modifier = modifier,
                    navModel = backStack,
                    transitionHandler = rememberBackstackFader(transitionSpec = { tween() })
                )
                SnackbarHost(
                    hostState = snackbarHostState,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .navigationBarsPadding(),
                    snackbar = { data ->
                        Snackbar(
                            snackbarData = data,
                            backgroundColor = colors.backgroundPrimary,
                            contentColor = colors.textPrimary
                        )
                    }
                )
            }
        }
    }
}