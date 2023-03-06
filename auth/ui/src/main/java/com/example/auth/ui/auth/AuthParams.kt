package com.example.auth.ui.auth

import androidx.compose.runtime.Immutable
import com.example.routing.route.Route
import io.my.core.IntentFlag
import kotlinx.coroutines.CoroutineScope


@Immutable
internal data class AuthState(
    val login: String = "",
    val password: List<Char> = emptyList()
)

internal class AuthIntent(coroutineScope: CoroutineScope) : IntentFlag(coroutineScope){
    val changeLogin = createIntent<String>("change-logic", coroutineScope)
    val changePassword = createIntent<List<Char>>("change-password", coroutineScope)
    val doLogin = createIntentWithoutParams("do-login", coroutineScope)
}