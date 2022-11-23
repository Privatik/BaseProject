package com.example.routing

import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.example.routing.route.RouteAction
import com.io.navigation.presenter
import com.io.navigation.sharedPresenter
import com.io.navigation_common.UIPresenter
import io.my.core.AssistedPresenterFactory
import io.my.core.DomainDependencies
import kotlin.properties.Delegates
import kotlin.reflect.KClass

abstract class Screen(
    buildContext: BuildContext,
): Node(buildContext){

    @CallSuper
    @Composable
    override fun View(modifier: Modifier) = Content(modifier = modifier)

    @Composable
    abstract fun Content(modifier: Modifier)

    interface Factory{

         fun create(buildContext: BuildContext): Screen
    }

}

interface ScreenInfo<P: Path>{
    val path: KClass<P>
    val screenFactory: (P) -> Screen.Factory
    val scope: ((RouteAction, DomainDependencies) -> UIPresenter)?
}