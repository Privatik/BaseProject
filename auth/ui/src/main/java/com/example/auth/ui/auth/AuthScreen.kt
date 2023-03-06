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
import com.bumble.appyx.core.modality.BuildContext
import com.example.auth.ui.AuthPresenterScope
import com.example.routing.Screen
import io.my.ui.effect.handleEffects
import io.my.ui.presenter.myPresenter
import io.my.ui.presenter.mySharedPresenter
import io.my.ui.theme.ProjectTheme

class AuthScreen private constructor(
    buildContext: BuildContext,
): Screen(buildContext) {

    @Composable
    override fun Content(modifier: Modifier) {
        val scope: AuthPresenterScope = mySharedPresenter()
        val presenter: AuthPresenter = myPresenter(scope.factory)
        presenter.handleEffects()

        Content(
            modifier = modifier,
            wrap = presenter.state.collectAsState(),
            intent = presenter.intent
        )
    }

    @Composable
    internal fun Content(
        modifier: Modifier = Modifier,
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
            TextField(
                value = state.login,
                onValueChange = intent.changeLogin::invoke
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = state.password.joinToString(),
                onValueChange = { password ->
                    intent.changePassword(password.toList())
                }
            )
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
        override fun create(buildContext: BuildContext): Screen = AuthScreen(
            buildContext = buildContext
        )
    }
}