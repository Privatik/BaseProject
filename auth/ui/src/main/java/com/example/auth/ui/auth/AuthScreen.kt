package com.example.auth.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.routing.Routing
import com.example.routing.Screen
import com.example.routing.ScreenInfo
import com.io.navigation.presenter
import io.my.ui.ProjectTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class AuthScreen private constructor(
    routing: Routing,
): Screen(routing) {

    @Composable
    override fun Content() {
        val presenter: AuthPresenter = presenter()
        LaunchedEffect(Unit){
            presenter.singleEffect
                .onEach { effect ->
                    when(effect){
                        is AuthEffect.Message -> {}
                        is AuthEffect.Navigate -> routing.navigate(effect.route)
                    }
                }
                .launchIn(this)
        }

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
            TextField(value = state.login, onValueChange = { intent.changeLogin(it) })
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = state.password, onValueChange = { intent.changePassword(it) })
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { intent.doLogin() }
            ) {
                Text(
                    text = "Sing in",
                    color = ProjectTheme.colors.backgroundSecondary
                )
            }
        }
    }

    class AuthScreenFactory: Screen.Factory{

        override fun <A : Any> create(routing: Routing, arg: A): Screen {
            return AuthScreen(routing)
        }
    }
}

object AuthScreenInfo: ScreenInfo{
    override val route: String = "auth"
    override val factory: () -> Screen.Factory = {
        AuthScreen.AuthScreenFactory()
    }
}