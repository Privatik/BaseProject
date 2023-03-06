package io.my.auth.domain

import io.my.auth.domain.di.AuthDataDependencies
import io.my.auth.domain.di.AuthDomainDependencies
import io.my.auth.domain.di.DaggerAuthDomainComponent
import io.my.core.domain.DataProvider
import io.my.core.domain.DomainProvider
import javax.inject.Inject

class AuthDomainProvider @Inject constructor(
    private val dataProvider: DataProvider<AuthDataDependencies>
): DomainProvider<AuthDomainDependencies> {
    override fun get(): AuthDomainDependencies {
        return DaggerAuthDomainComponent.builder()
            .dataDependency(dataProvider.get())
            .build()
    }
}