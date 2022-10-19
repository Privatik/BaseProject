package com.example.auth.ui.profile

import androidx.compose.runtime.Immutable
import com.example.routing.Route
import io.my.core.IntentFlog
import io.my.core.createIntent
import io.my.core.createIntentWithoutParams

class ProfileIntent: IntentFlog {
    val checkValid = createIntentWithoutParams("check-valid")
}

sealed interface ProfileEffect{
    data class Navigate(val route: Route): ProfileEffect
    data class Message(val message: String): ProfileEffect
}