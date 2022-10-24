package com.example.auth.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.auth.ui.AuthPresenterScope
import com.example.routing.RoutingAction
import com.example.routing.Screen
import com.io.navigation.presenter
import com.io.navigation.sharedPresenter
import io.my.ui.ProjectTheme
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ProfileScreen private constructor(
    routingAction: RoutingAction,
    private val email: String
): Screen(routingAction) {

    @Composable
    override fun Content() {
        val scope: AuthPresenterScope = sharedPresenter()
        val presenter: ProfilePresenter = presenter(scope.factory)
        LaunchedEffect(Unit){
            presenter.state.launchIn(this)

            presenter.singleEffect
                .onEach { effect ->
                    when(effect){
                        is ProfileEffect.Message -> { }
                        is ProfileEffect.Navigate -> routingAction.navigate(effect.route)
                    }
                }
                .launchIn(this)
        }
        Content(intent = presenter.intent)
    }

    @Composable
    private fun Content(
        intent: ProfileIntent
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ProjectTheme.colors.backgroundPrimary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(email)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { intent.checkValid() }
            ){
                Text("Check valid", color = ProjectTheme.colors.backgroundPrimary)
            }
        }
    }

    class ProfileFactory @Inject constructor(): Factory{
        override val route: String = "profile"

        override fun <A : Any> create(routingAction: RoutingAction, arg: A): Screen {
            return ProfileScreen(routingAction, arg as String)
        }
    }
}
