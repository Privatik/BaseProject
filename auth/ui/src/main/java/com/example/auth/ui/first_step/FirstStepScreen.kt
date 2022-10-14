package com.example.auth.ui.first_step

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.routing.Route
import com.example.routing.Routing
import com.example.routing.Screen
import com.example.routing.ScreenInfo
import com.io.navigation.presenter
import com.io.navigation.sharedPresenter

class FirstStepScreen private constructor(
    routing: Routing,
): Screen(routing) {

    @Composable
    override fun Content() {
        val firstPresenter: FirstStepPresenter = sharedPresenter()
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    routing.navigate(Route.OpenNextScreen("Hello"))
                }
            ) {
                Text("Open nex screen")
            }
        }
    }

    class FirstStepFactory: Screen.Factory{

        override fun <A : Any> create(routing: Routing, arg: A): Screen {
            return FirstStepScreen(routing)
        }
    }
}

object FirstStepScreenInfo: ScreenInfo{
    override val route: String = "first-step"
    override val factory: () -> Screen.Factory = {
        FirstStepScreen.FirstStepFactory()
    }
}