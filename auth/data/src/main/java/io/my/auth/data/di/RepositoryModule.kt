package io.my.auth.data.di

import dagger.Binds
import dagger.Module
import io.my.auth.data.AuthRepositoryImpl
import io.my.auth.domain.repository.AuthRepository

@Module
internal interface RepositoryModule {

    @Binds
    fun bindRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository
}