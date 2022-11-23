package com.example.routing

sealed class Path{
    object FirstScreen: Path()
    data class SecondScreen(val email: String): Path()
}
