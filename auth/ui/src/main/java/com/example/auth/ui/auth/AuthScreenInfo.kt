package com.example.auth.ui.auth

import com.example.auth.ui.AuthPresenterScope
import com.example.routing.GetDomainProvider
import com.example.routing.Path
import com.example.routing.Screen
import com.example.routing.ScreenInfo
import com.example.routing.route.RouteActionHandler
import io.my.auth.domain.AuthDomainProvider
import io.my.auth.domain.di.AuthDomainDependencies
import io.my.ui.presenter.MyPresenter
import javax.inject.Inject
import kotlin.reflect.KClass

class AuthScreenInfo @Inject constructor(): ScreenInfo<Path.FirstScreen, AuthDomainDependencies> {
    override val path: KClass<Path.FirstScreen> = Path.FirstScreen::class

    override val screenFactory: (Path.FirstScreen) -> Screen.Factory = { AuthScreenFactory() }

    override val scope: (Path.FirstScreen, GetDomainProvider<AuthDomainDependencies>) -> MyPresenter =
        { _, provider ->
            AuthPresenterScope(provider(AuthDomainProvider::class))
        }
}