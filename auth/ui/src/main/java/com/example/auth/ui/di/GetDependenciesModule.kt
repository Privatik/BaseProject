package com.example.auth.ui.di

import dagger.Module
import dagger.Provides
import io.my.auth.domain.AuthInteractor
import io.my.auth.domain.di.AuthDomainDependencies

@Module
internal class GetDependenciesModule {

    @Provides
    fun provideInteractor(dependencies: AuthDomainDependencies): AuthInteractor{
        return dependencies.interactor()
    }
}