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
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.example.auth.ui.AuthPresenterScope
import com.example.auth.ui.AuthTestTags
import com.example.routing.Screen
import io.my.ui.presenter.myPresenterAndHandleEffects
import io.my.ui.presenter.mySharedPresenter
import io.my.ui.theme.ProjectTheme

private class AuthScreen: Screen() {

    @Composable
    override fun Content(modifier: Modifier) {
        val scope: AuthPresenterScope = mySharedPresenter()
        val presenter: AuthPresenter = myPresenterAndHandleEffects(scope.factory)

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
                .background(ProjectTheme.colors.backgroundPrimary)
                .semantics { testTag = AuthTestTags.authContent },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                modifier = Modifier
                    .semantics { testTag = AuthTestTags.loginField },
                value = state.login,
                onValueChange = intent.changeLogin::invoke
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                modifier = Modifier
                    .semantics { testTag = AuthTestTags.passwordField },
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
}

class AuthScreenFactory: Screen.Factory {
    override fun create(): Screen = AuthScreen()
}