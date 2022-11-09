package com.example.auth.ui.auth

import android.util.Log
import com.example.auth.ui.profile.ProfileIntent
import com.example.machine.ReducerDSL
import com.example.routing.route.Route
import com.example.routing.route.RouteAction
import io.my.auth.domain.AuthInteractor
import io.my.core.Presenter
import io.my.core.domain.StateModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

internal class AuthPresenter @Inject constructor(
    private val routeAction: RouteAction,
    private val interactor: AuthInteractor
): Presenter<AuthState, AuthIntent, AuthEffect>(
    initialState = AuthState()
) {

    override fun CoroutineScope.buildIntent(): AuthIntent = AuthIntent(this)

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
            action = { _, newState , _ ->
                interactor.sinIn(newState.login, newState.password)
            }
        )

        onEach(
            interactor.singInFlow,
            action = { _, _, payload ->
                when (payload){
                    is StateModel.Content<String> -> {
                        routeAction.navigate(Route.OpenNextScreen(payload.data))
                    }
                    is StateModel.Error -> {

                    }
                    else -> { }
                }
            }
        )
    }
}