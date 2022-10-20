package com.example.routing.route

sealed class Route {
    data class OpenNextScreen(val email: String): Route()
    object Back: Route()
}