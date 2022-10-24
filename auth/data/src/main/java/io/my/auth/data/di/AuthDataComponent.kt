package io.my.auth.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.my.auth.domain.di.AuthDataDependencies
import io.my.auth.domain.di.AuthDomainComponent
import io.my.data.di.CoreDataDependencies

@Component(
    modules = [RepositoryModule::class]
)
interface AuthDataComponent: AuthDataDependencies {

    @Component.Builder
    interface Builder {

        fun core(dependencies: CoreDataDependencies)

        fun build(): AuthDomainComponent
    }


}