package com.example.auth.ui

import com.example.auth.ui.di.AuthComponent
import com.example.auth.ui.di.DaggerAuthComponent
import com.example.routing.route.RouteActionHandler
import io.my.auth.domain.di.AuthDomainDependencies
import io.my.core.domain.DomainProvider
import io.my.ui.presenter.MyPresenter
import io.my.ui.presenter.MyPresenterFactory

internal class AuthPresenterScope constructor(
    domainProvider: DomainProvider<AuthDomainDependencies>,
): MyPresenter() {
    @Volatile
    private var _component: AuthComponent? = null
    val factory: MyPresenterFactory get() = _component!!.factory()

    init {
        _component = DaggerAuthComponent.builder()
            .domain(domainProvider.get())
            .build()
    }

    override fun clear() {
        _component = null
    }

}