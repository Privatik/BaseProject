package com.example.routing.di

import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import io.my.auth.data.AuthDataProvider
import io.my.auth.domain.AuthDomainProvider
import io.my.auth.domain.di.AuthDataDependencies
import io.my.core.domain.DataProvider
import io.my.core.domain.DomainProvider
import kotlin.reflect.KClass

@Module
interface DataDependencyModule {

    @Binds
    fun bindAuthProvider(provider: AuthDataProvider): DataProvider<AuthDataDependencies>

}