package com.example.routing


sealed class Route(internal val route: String? = null) {
    data class OpenNextScreen(val message: String): Route()
    object Back: Route()
}