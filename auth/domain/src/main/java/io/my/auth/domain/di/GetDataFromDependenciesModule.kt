package io.my.auth.domain.di

import dagger.Module
import dagger.Provides
import io.my.auth.domain.repository.AuthRepository

@Module
internal class GetDataFromDependenciesModule {

    @Provides
    fun provideRepository(dependencies: AuthDataDependencies): AuthRepository {
        return dependencies.repository()
    }

}