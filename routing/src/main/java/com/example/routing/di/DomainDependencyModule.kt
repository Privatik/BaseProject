package com.example.routing.di

import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import io.my.auth.domain.AuthDomainProvider
import io.my.core.domain.DomainProvider
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class DomainProviderKey(val value: KClass<out DomainProvider<*>>)

@Module
interface DomainDependencyModule {

    @Binds @[IntoMap DomainProviderKey(AuthDomainProvider::class)]
    fun bindAuthProvider(provider: AuthDomainProvider): DomainProvider<*>

}