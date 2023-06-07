package com.example.auth.ui

import androidx.annotation.MainThread
import com.example.auth.ui.di.AuthComponent
import com.example.auth.ui.di.DaggerAuthComponent
import io.my.auth.domain.di.AuthDomainDependencies
import io.my.core.domain.DomainProvider
import io.my.ui.presenter.MyPresenter
import io.my.ui.presenter.MyPresenterFactory

internal class AuthPresenterScope constructor(
    private val domainProvider: DomainProvider<AuthDomainDependencies>,
): MyPresenter() {

    private var _component: AuthComponent? = null
        get() {
            if (field == null){
                field = DaggerAuthComponent.builder()
                    .domain(domainProvider.get())
                    .build()
            }
            return field
        }

    val factory: MyPresenterFactory
    @MainThread get() = _component!!.factory()


}