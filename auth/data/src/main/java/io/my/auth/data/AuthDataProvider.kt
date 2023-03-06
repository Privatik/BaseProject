package io.my.auth.data

import io.my.auth.data.di.DaggerAuthDataComponent
import io.my.auth.domain.di.AuthDataDependencies
import io.my.core.domain.DataProvider
import io.my.data.di.CoreDataDependencies
import javax.inject.Inject

class AuthDataProvider @Inject constructor(
    private val coreDataDependencies: CoreDataDependencies,
): DataProvider<AuthDataDependencies> {
    override fun get(): AuthDataDependencies {
        return DaggerAuthDataComponent.builder()
            .dataDependency(coreDataDependencies)
            .build()
    }

}