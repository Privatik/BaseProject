package com.example.routing

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

abstract class Screen(protected val routing: Routing){

    @Composable
    abstract fun Content()

    interface Factory{
         fun <A: Any> create(
            routing: Routing,
            arg: A
        ): Screen
    }

}

interface ScreenInfo{
    val route: String
    val factory: () -> Screen.Factory
}