package com.example.auth.ui

import com.example.auth.ui.di.AuthComponent
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.UIPresenter
import io.my.core.Presenter
import javax.inject.Inject
import javax.inject.Provider

class AuthPresenterScope: UIPresenter {
    @Volatile
    private var _component: AuthComponent? = null
    val factory: PresenterFactory get() = _component!!.factory()

    init {

    }

    override fun clear() {
        _component == null
    }

}