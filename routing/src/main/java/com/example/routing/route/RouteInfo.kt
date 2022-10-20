package com.example.routing.route

import androidx.navigation.NavGraphBuilder

data class RouteInfo(
    val route: String,
    val screen: NavGraphBuilder.() -> Unit
)