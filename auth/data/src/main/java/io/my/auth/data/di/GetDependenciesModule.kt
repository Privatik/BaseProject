package io.my.auth.data.di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.my.data.di.CoreDataDependencies
import io.my.data.local.DataStoreManager
import io.my.data.remote.BaseApiProperty
import io.my.data.remote.token.TokenManagerProxy

@Module
internal class GetDependenciesModule {

    @Provides
    fun provideClient(dependencies: CoreDataDependencies): HttpClient{
        return dependencies.client()
    }

    @Provides
    fun provideTokeProxy(dependencies: CoreDataDependencies): TokenManagerProxy {
        return dependencies.tokenProxy()
    }

    @Provides
    fun provideDataStore(dependencies: CoreDataDependencies): DataStoreManager{
        return dependencies.dataStoreManager()
    }

    @Provides
    fun provideBaseApi(dependencies: CoreDataDependencies): BaseApiProperty{
        return dependencies.baseApi()
    }
}