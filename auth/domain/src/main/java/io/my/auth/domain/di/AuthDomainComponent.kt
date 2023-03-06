package io.my.auth.domain.di

import dagger.Component
import io.my.auth.domain.AuthInteractor
import io.my.auth.domain.repository.AuthRepository

interface AuthDataDependencies {
    fun repository(): AuthRepository
}

interface AuthDomainDependencies {
    fun interactor(): AuthInteractor
}

@Component(
    dependencies = [AuthDataDependencies::class],
    modules = [
        InteractorModule::class,
    ]
)
interface AuthDomainComponent: AuthDomainDependencies {

    @Component.Builder
    interface Builder {

        fun dataDependency(dataDependencies: AuthDataDependencies): Builder

        fun build(): AuthDomainComponent
    }

}