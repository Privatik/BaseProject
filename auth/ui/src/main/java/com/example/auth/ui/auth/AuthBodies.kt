package com.example.auth.ui.auth

import androidx.compose.runtime.Immutable
import io.my.core.IntentFlog
import io.my.core.createIntent
import io.my.core.createIntentWithoutParams


@Immutable
data class AuthState(
    val login: String = "",
    val password: String = ""
)

class AuthIntent: IntentFlog{
    val changeLogin = createIntent<String>("change-logic")
    val changePassword = createIntent<String>("change-password")
    val doLogin = createIntentWithoutParams("do-login")
}