package com.example.auth.ui.profile

import com.example.machine.ReducerDSL
import com.example.routing.route.Route
import com.example.routing.route.RouteAction
import io.my.auth.domain.AuthInteractor
import io.my.core.Presenter
import io.my.core.domain.StateModel
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

internal class ProfilePresenter @Inject constructor(
    private val routeAction: RouteAction,
    private val interactor: AuthInteractor
): Presenter<Any, ProfileIntent, ProfileEffect>(Any()) {

    override fun CoroutineScope.buildIntent(): ProfileIntent = ProfileIntent(this)

    override fun ReducerDSL<Any, ProfileEffect>.reducer() {
        onEach(
            intent.checkValid.asFlow(),
            action = { _, _, _->
                interactor.checkValid()
            }
        )

        onEach(
            interactor.isValidFlow,
            action = { _, _, payload ->
                when (payload){
                    is StateModel.Content<Boolean> -> {

                    }
                    is StateModel.Error -> {
                        routeAction.navigate(Route.Back)
                    }
                    else -> { }
                }
            }
        )
    }
}