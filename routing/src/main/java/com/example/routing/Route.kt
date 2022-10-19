package com.example.routing


sealed class Route {
    data class OpenNextScreen(val email: String): Route()
    object Back: Route()
}