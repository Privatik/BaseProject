package com.example.auth.ui.di

import dagger.Component
import io.my.auth.domain.di.AuthDomainDependencies
import io.my.ui.presenter.DefaultPresenterFactory

@Component(
    dependencies = [AuthDomainDependencies::class],
    modules = [
        PresenterFactoryModule::class,
        PresenterModule::class,
    ]
)
@AuthScope
interface AuthComponent {

    fun factory(): DefaultPresenterFactory

    @Component.Builder
    interface Builder {

        fun domain(dependencies: AuthDomainDependencies): Builder

        fun build(): AuthComponent
    }
}