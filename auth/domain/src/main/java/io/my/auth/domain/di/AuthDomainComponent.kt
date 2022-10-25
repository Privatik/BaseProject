package io.my.auth.domain.di

import dagger.BindsInstance
import dagger.Component
import io.my.auth.domain.AuthInteractor
import io.my.auth.domain.repository.AuthRepository


interface AuthDataDependencies {
    fun repository(): AuthRepository
}


interface AuthDomainDependencies{
    fun interactor(): AuthInteractor
}

@Component(
    modules = [
        InteractorModule::class,
        GetDependenciesModule::class
    ]
)
interface AuthDomainComponent: AuthDomainDependencies {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun dto(dependencies: AuthDataDependencies): Builder

        fun build(): AuthDomainComponent
    }

}