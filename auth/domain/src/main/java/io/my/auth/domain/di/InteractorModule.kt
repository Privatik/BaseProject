package io.my.auth.domain.di

import dagger.Binds
import dagger.Module
import io.my.auth.domain.AuthInteractor
import io.my.auth.domain.AuthInteractorImpl

@Module
internal interface InteractorModule {

    @Binds
    fun bindInteractor(interactorImpl: AuthInteractorImpl): AuthInteractor
}