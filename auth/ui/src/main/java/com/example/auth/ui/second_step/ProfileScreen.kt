package com.example.auth.ui.second_step

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.routing.Route
import com.example.routing.Routing
import com.example.routing.Screen
import com.example.routing.ScreenInfo
import io.my.ui.ProjectTheme

class ProfileScreen private constructor(
    routing: Routing
): Screen(routing) {

    @Composable
    override fun Content() {

    }

    @Composable
    private fun Content(
        wrap: State<ProfileState>
    ){
        val state by wrap

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ProjectTheme.colors.backgroundPrimary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(state.validText)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {  }
            ){
                Text("Check valid", color = ProjectTheme.colors.backgroundPrimary)
            }
        }
    }

    class ProfileFactory: Screen.Factory{
        override fun <A : Any> create(routing: Routing, arg: A): Screen {
            return ProfileScreen(routing)
        }
    }

}

object ProfileScreenInfo: ScreenInfo{
    override val route: String = "profile"
    override val factory: () -> Screen.Factory = {
        ProfileScreen.ProfileFactory()
    }
}
