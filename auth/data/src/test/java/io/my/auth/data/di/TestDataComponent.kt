package io.my.auth.data.di

import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [
        TestCacheModule::class,
        TestClientModule::class,
        TestCoroutineModule::class,
        ApiModule::class,
        RepositoryModule::class
    ]
)
@Singleton
interface TestDataComponent: AuthDataComponent {
}