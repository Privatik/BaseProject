package com.example.routing

import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.io.navigation.AndroidPresenterStoreOwner
import com.io.navigation.PresenterComponentActivity
import com.io.navigation.PresenterCompositionLocalProvider
import com.io.navigation.setContentWithPresenter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.contracts.contract

fun PresenterComponentActivity.setContentPerJetpack(
    content: @Composable (controller: NavHostController) -> Unit
){
    setContent {
        val controller = rememberNavController()

        PresenterCompositionLocalProvider(
            controller = GooglePresenterController(presenterStoreOwner, controller),
            owner = presenterStoreOwner,
            canUpdate = true,
            canSaveStateKey = true,
            content = {
                content(controller)
            }
        )
    }
}

@Composable
fun Routing(
    controller: NavHostController,
    startScreenRoute: String,
    screens: Map<Path, ScreenInfo>
){
    val argument = GoogleArgumentImpl()
    val routing = RoutingImpl(controller, screens, argument)

    NavHost(
        navController = controller,
        startDestination = startScreenRoute
    ){
        screens.values.forEach { screen ->
            composable(screen.route){
                val arg = rememberArgument(
                    key = screen.route,
                    value = argument.get(screen.route) ?: Unit
                )

                screen.factory().create(routing, arg).Content()
            }
        }
    }
}