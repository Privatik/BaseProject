package com.example.routing

import androidx.annotation.CallSuper
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import com.example.routing.route.RouteActionHandler
import io.my.core.domain.DomainProvider
import io.my.ui.presenter.MyPresenter
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

typealias GetDomainProvider<D> = (KClass<out DomainProvider<D>>) -> DomainProvider<D>

interface ScreenInfo<P: Path, DomainDependency: Any>{
    val path: KClass<P>
    val screenFactory: (P) -> Screen.Factory
    val scope: ((GetDomainProvider<DomainDependency>) -> MyPresenter)?
}