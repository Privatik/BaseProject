package com.example.auth.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.auth.ui.AuthPresenterScope
import com.example.routing.route.Path
import com.example.routing.route.RouteAction
import com.example.routing.Screen
import com.example.routing.ScreenInfo
import com.io.navigation.presenter
import com.io.navigation.sharedPresenter
import com.io.navigation_common.UIPresenter
import io.my.auth.domain.di.AuthDomainDependencies
import io.my.core.DomainDependencies
import io.my.ui.ProjectTheme
import javax.inject.Inject
import kotlin.reflect.KClass

class AuthScreen private constructor(): Screen() {

    @Composable
    override fun Content() {
        val scope: AuthPresenterScope = sharedPresenter()
        val presenter: AuthPresenter = presenter(scope.factory)

        Content(
            wrap = presenter.state.collectAsState(),
            intent = presenter.intent
        )
    }

    @Composable
    private fun Content(
        wrap: State<AuthState>,
        intent: AuthIntent
    ) {
        val state by wrap

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ProjectTheme.colors.backgroundPrimary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = state.login, onValueChange = intent.changeLogin::invoke )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = state.password, onValueChange = intent.changePassword::invoke )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = intent.doLogin::invoke
            ) {
                Text(
                    text = "Sing in",
                    color = ProjectTheme.colors.backgroundSecondary
                )
            }
        }
    }

    class AuthScreenFactory: Factory{

        override fun <A : Any> create(arg: A): Screen = AuthScreen()
    }
}

class AuthScreenInfo @Inject constructor(): ScreenInfo {
    override val path: Path = Path.FIRST_SCREEN
    override val routeForNavigation: String = "auth"
    override val scopeKClazz: KClass<out UIPresenter> = AuthPresenterScope::class

    override val screenFactory: () -> Screen.Factory = {
        AuthScreen.AuthScreenFactory()
    }
    override val scopeInPresenter: (
        route: RouteAction,
        domainDependencies: DomainDependencies
    ) -> UIPresenter = { routingAction, domain ->
        AuthPresenterScope(routingAction, domain as AuthDomainDependencies)
    }

}