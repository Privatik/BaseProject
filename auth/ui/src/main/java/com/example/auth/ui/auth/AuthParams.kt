package com.example.auth.ui.auth

import androidx.compose.runtime.Immutable
import com.example.routing.route.Route
import io.my.core.IntentFlag
import io.my.core.createIntent
import io.my.core.createIntentWithoutParams
import kotlinx.coroutines.CoroutineScope


@Immutable
internal data class AuthState(
    val login: String = "kurak",
    val password: String = "123456"
)

internal class AuthIntent(coroutineScope: CoroutineScope) : IntentFlag(coroutineScope){
    val changeLogin = createIntent<String>("change-logic", coroutineScope)
    val changePassword = createIntent<String>("change-password", coroutineScope)
    val doLogin = createIntentWithoutParams("do-login", coroutineScope)
}

internal sealed interface AuthEffect{
    data class Navigate(val route: Route): AuthEffect
    data class Message(val message: String): AuthEffect
}