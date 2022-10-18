package com.example.routing


sealed class Route {
    object OpenNextScreen: Route()
    object Back: Route()
}