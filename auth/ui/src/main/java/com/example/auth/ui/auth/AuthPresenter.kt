package com.example.auth.ui.auth

import com.example.machine.ReducerDSL
import io.my.core.IntentFlog
import io.my.core.Presenter
import io.my.core.asFlow

class AuthPresenter: Presenter<AuthState, AuthIntent, AuthIntent>(
    initialState = AuthState()
) {

    override fun buildIntent() = AuthIntent()

    override fun ReducerDSL<AuthState, AuthIntent>.reducer() {
        onEach(
            intent.changeLogin.asFlow(),
            updateState = { oldState, payload ->
                oldState.copy(login = payload)
            }
        )

        onEach(
            intent.changePassword.asFlow(),
            updateState = { oldState, payload ->
                oldState.copy(password = payload)
            }
        )

        onEach(
            intent.doLogin.asFlow(),
            action = { _, _, _ ->
                //to do
            }
        )
    }
}