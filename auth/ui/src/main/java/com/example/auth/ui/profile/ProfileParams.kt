package com.example.auth.ui.profile

import com.example.routing.route.Route
import io.my.core.IntentFlag
import io.my.core.createIntentWithoutParams
import kotlinx.coroutines.CoroutineScope

internal class ProfileIntent(coroutineScope: CoroutineScope) : IntentFlag(coroutineScope) {
    val checkValid = createIntentWithoutParams("check-valid", coroutineScope)
}

internal sealed interface ProfileEffect{
    data class Navigate(val route: Route): ProfileEffect
    data class Message(val message: String): ProfileEffect
}