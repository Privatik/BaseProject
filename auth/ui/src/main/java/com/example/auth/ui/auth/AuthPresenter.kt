package com.example.auth.ui.auth

import com.example.machine.ReducerDSL
import com.example.routing.route.Route
import io.my.auth.domain.AuthInteractor
import io.my.core.Presenter
import io.my.core.domain.StateModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal class AuthPresenter @Inject constructor(
    private val interactor: AuthInteractor
): Presenter<AuthState, AuthIntent, AuthEffect>(
    initialState = AuthState()
) {

    override fun buildIntent(coroutineScope: CoroutineScope): AuthIntent = AuthIntent(coroutineScope)

    override fun ReducerDSL<AuthState, AuthEffect>.reducer() {
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
            effect = { _, newState , _ ->
                AuthEffect.Navigate(Route.OpenNextScreen(newState.login))
            }
        )

        onEach(
            interactor.singInFlow,
            effect = { _, _, payload ->
                when (payload){
                    is StateModel.Content<String> -> {
                        AuthEffect.Navigate(Route.OpenNextScreen(payload.data))
                    }
                    is StateModel.Error -> {
                        AuthEffect.Message(payload.throwable.toString())
                    }
                    else -> null
                }
            }
        )
    }
}