package com.example.auth.ui.auth

import com.example.machine.ReducerDSL
import com.example.routing.DefaultEffectHandler
import com.example.routing.route.Route
import io.my.auth.domain.AuthInteractor
import io.my.core.domain.StateModel
import io.my.ui.effect.Effect
import io.my.ui.presenter.Presenter
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal class AuthPresenter @Inject constructor(
    private val interactor: AuthInteractor
): Presenter<AuthState, AuthIntent, Effect>(
    initialState = AuthState()
) {

    override fun CoroutineScope.buildIntent(): AuthIntent = AuthIntent(this)

    override fun ReducerDSL<AuthState, Effect>.reducer() {
        onEach(
            intent.changeLogin.asFlow(),
            changeState = { oldState, payload ->
                oldState.copy(login = payload)
            }
        )

        onEach(
            intent.changePassword.asFlow(),
            changeState = { oldState, payload ->
                oldState.copy(password = payload)
            }
        )

        onEach(
            intent.doLogin.asFlow(),
            action = { _, newState , _ ->
                interactor.sinIn(newState.login, newState.password.toString())
            }
        )

        onEach(
            interactor.singInFlow,
            effect = { _, _, payload ->
                when (payload){
                    is StateModel.Content<String> -> {
                        DefaultEffectHandler.DefaultEffect.Navigate(Route.OpenNextScreen(payload.data))
                    }
                    is StateModel.Error -> {
                        DefaultEffectHandler.DefaultEffect.SnackBar(payload.throwable.toString())
                    }
                    StateModel.Loading,
                    StateModel.None -> { null }
                }
            },
        )
    }
}