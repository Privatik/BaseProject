package io.my.data.di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.my.data.local.DataStoreManager
import io.my.data.remote.BaseApiProperty
import io.my.data.remote.TokenManagerProxy
import io.my.data.remote.TokenManagerProxyImpl
import io.my.data.remote.getKtorClient
import io.my.data.remote.network.JWTToken
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module(
    includes = [LocalModule::class, TokenModule::class]
)
internal class RemoteModule {

    @Provides
    @Singleton
    fun provideBaseApi(dataStoreManager: DataStoreManager): BaseApiProperty{
        return BaseApiProperty(dataStoreManager)
    }

    @Provides
    fun provideToken(token: JWTToken.TokenManager): TokenManagerProxy{
        return TokenManagerProxyImpl(token)
    }

    @Provides
    @Singleton
    fun provideClient(tokens: Map<String,JWTToken.TokenManager>, json: Json): HttpClient{
        return getKtorClient(tokens, json)
    }
}