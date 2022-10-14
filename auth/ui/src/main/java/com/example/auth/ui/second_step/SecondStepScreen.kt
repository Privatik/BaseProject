package com.example.auth.ui.second_step

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.routing.Route
import com.example.routing.Routing
import com.example.routing.Screen
import com.example.routing.ScreenInfo

class SecondStepScreen private constructor(
    routing: Routing,
    private val message: String
): Screen(routing) {

    @Composable
    override fun Content() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(message)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    routing.navigate(Route.Back)
                }
            ){
                Text("Back")
            }
        }
    }

    class SecondStepFactory: Screen.Factory{
        override fun <A : Any> create(routing: Routing, arg: A): Screen {
            return SecondStepScreen(routing, arg as String)
        }
    }

}

object SecondStepScreenInfo: ScreenInfo{
    override val route: String = "second-step"
    override val factory: () -> Screen.Factory = {
        SecondStepScreen.SecondStepFactory()
    }
}
