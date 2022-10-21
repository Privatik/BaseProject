package com.example.auth.ui.profile

import com.example.machine.ReducerDSL
import com.example.routing.route.Route
import io.my.core.Presenter
import io.my.core.asFlow

class ProfilePresenter(): Presenter<Any, ProfileIntent, ProfileEffect>(Any()) {

    override fun buildIntent() = ProfileIntent()

    override fun ReducerDSL<Any, ProfileEffect>.reducer() {
        onEach(
            intent.checkValid.asFlow(),
            effect = { _, _, _->
                ProfileEffect.Navigate(Route.Back)
            }
        )

//        onEach(
//            interactor.isValidFlow,
//            effect = { _, _, payload ->
//                when (payload){
//                    is StateModel.Content<Boolean> -> {
//                        ProfileEffect.Message("connect")
//                    }
//                    is StateModel.Error -> {
//                        ProfileEffect.Navigate(Route.Back)
//                    }
//                    else -> null
//                }
//            }
//        )
    }
}