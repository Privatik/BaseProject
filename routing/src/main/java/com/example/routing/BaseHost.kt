package com.example.routing

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import com.example.routing.di.RouteDependencies
import com.example.routing.managers.DomainDependencyManager
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation_common.PresenterStoreOwner

internal class BaseHost (
    buildContext: BuildContext,
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
        PresenterCompositionLocalProvider(
            owner = presenterOwner
        ) {
            Children(
                navModel = backStack,
                transitionHandler = rememberBackstackFader(transitionSpec = { tween() })
            )
        }
    }
}