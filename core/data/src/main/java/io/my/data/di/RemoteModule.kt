package io.my.data.di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.my.data.local.DataStoreManager
import io.my.data.remote.BaseApiProperty
import io.my.data.remote.getKtorClient
import io.my.data.remote.network.JWTToken
import io.my.data.remote.token.manager.PublicTokenManager
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module()
internal class RemoteModule {

    @Provides
    @Singleton
    fun provideBaseApi(dataStoreManager: DataStoreManager): BaseApiProperty{
        return BaseApiProperty(dataStoreManager)
    }

    @Provides
    @Singleton
    fun provideClient(token: JWTToken.TokenManager, json: Json): HttpClient{
        val tokens = mapOf("https://127.0.0.1/" to token)
        return getKtorClient(tokens, json)
    }
}