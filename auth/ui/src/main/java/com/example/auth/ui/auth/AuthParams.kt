package com.example.auth.ui.auth

import androidx.compose.runtime.Immutable
import com.example.routing.route.Route
import io.my.core.IntentFlag
import io.my.core.createIntent
import io.my.core.createIntentWithoutParams
import kotlinx.coroutines.CoroutineScope


@Immutable
data class AuthState(
    val login: String = "",
    val password: String = ""
)

class AuthIntent(coroutineScope: CoroutineScope) : IntentFlag(coroutineScope){
    val changeLogin = createIntent<String>("change-logic", coroutineScope)
    val changePassword = createIntent<String>("change-password", coroutineScope)
    val doLogin = createIntentWithoutParams("do-login", coroutineScope)
}

sealed interface AuthEffect{
    data class Navigate(val route: Route): AuthEffect
    data class Message(val message: String): AuthEffect
}