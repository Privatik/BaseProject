package com.example.routing

import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.io.navigation.PresenterComponentActivity
import com.io.navigation.PresenterCompositionLocalProvider

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