package io.my.auth.data.di

import dagger.Component
import io.my.auth.domain.di.AuthDataDependencies
import io.my.data.di.CoreDataDependencies

@Component(
    dependencies = [CoreDataDependencies::class],
    modules = [
        ApiModule::class,
        RepositoryModule::class
    ]
)
interface AuthDataComponent: AuthDataDependencies {

    @Component.Builder
    interface Builder {

        fun dataDependency(dataDependencies: CoreDataDependencies): Builder

        fun build(): AuthDataComponent
    }

}