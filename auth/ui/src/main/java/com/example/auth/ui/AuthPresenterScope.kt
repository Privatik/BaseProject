package com.example.auth.ui

import com.example.auth.ui.di.AuthComponent
import com.example.auth.ui.di.DaggerAuthComponent
import com.example.routing.route.RouteAction
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.UIPresenter
import io.my.auth.domain.di.AuthDomainDependencies

internal class AuthPresenterScope(
    routeAction: RouteAction,
    domainDependencies: AuthDomainDependencies
): UIPresenter {
    @Volatile
    private var _component: AuthComponent? = null
    val factory: PresenterFactory get() = _component!!.factory()

    init {

        _component = DaggerAuthComponent.builder()
            .domain(domainDependencies)
            .routeAction(routeAction)
            .build()
    }

    override fun clear() {
        _component = null
    }

}