package com.example.auth.ui.di

import com.io.navigation_common.PresenterFactory
import dagger.BindsInstance
import dagger.Component
import io.my.auth.domain.di.AuthDataDependencies
import io.my.auth.domain.di.AuthDomainComponent
import io.my.auth.domain.di.AuthDomainDependencies
import io.my.core.DefaultPresenterFactory

@Component(
    modules = [PresenterFactoryModule::class, PresenterModule::class]
)
@AuthScope
interface AuthComponent {

    fun factory(): DefaultPresenterFactory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun domain(dependencies: AuthDomainDependencies): Builder

        fun build(): AuthComponent
    }
}