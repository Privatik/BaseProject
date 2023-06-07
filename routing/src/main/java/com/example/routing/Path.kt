package com.example.routing

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Path: Parcelable {
    @Parcelize
    object FirstScreen: Path()
    @Parcelize
    data class SecondScreen(val email: String): Path()
}
