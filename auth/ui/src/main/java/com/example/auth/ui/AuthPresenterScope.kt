package com.example.auth.ui

import com.example.auth.ui.di.AuthComponent
import com.example.auth.ui.di.DaggerAuthComponent
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.UIPresenter
import io.my.auth.domain.di.AuthDomainDependencies
import io.my.auth.domain.di.DaggerAuthDomainComponent
import io.my.core.DomainDependencies
import io.my.core.GlobalDependencies
import io.my.core.Presenter
import javax.inject.Inject
import javax.inject.Provider

internal class AuthPresenterScope(
    dependencies: DomainDependencies
): UIPresenter {
    @Volatile
    private var _component: AuthComponent? = null
    val factory: PresenterFactory get() = _component!!.factory()

    init {

        _component = DaggerAuthComponent.builder()
            .domain(dependencies as AuthDomainDependencies)
            .build()
    }

    override fun clear() {
        _component = null
    }

}