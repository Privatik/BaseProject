package com.example.routing

import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.composable.Children
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.core.node.Node
import com.bumble.appyx.core.node.ParentNode
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.operation.pop
import com.bumble.appyx.navmodel.backstack.operation.push
import com.bumble.appyx.navmodel.backstack.transitionhandler.rememberBackstackFader
import com.example.routing.presenter.BasePresenterAdapter
import com.example.routing.presenter.BasePresenterStoreOwner
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation_common.PresenterStoreOwner
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal class Host (
    initPath: Path,
    buildContext: BuildContext,
    private val backStack: BackStack<Path> = BackStack(
        initialElement = initPath,
        savedStateMap = buildContext.savedStateMap
    ),
    private val presenterOwner: PresenterStoreOwner<NavKey<Path>> = BasePresenterStoreOwner(
        BasePresenterAdapter(backStack)
    )
): ParentNode<Path>(
    buildContext = buildContext,
    navModel = backStack
) {

    override fun resolve(navTarget: Path, buildContext: BuildContext): Node {
        return when (navTarget){
            Path.FirstScreen -> TODO()
            Path.SecondScreen -> TODO()
        }
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