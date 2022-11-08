package io.my.data.di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.my.data.local.DataStoreManager
import io.my.data.remote.BaseApiProperty
import io.my.data.remote.getKtorClient
import io.my.data.remote.token.JWTToken
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module()
internal class RemoteModule {

    @Provides
    @Singleton
    fun provideClient(
        token: JWTToken.TokenManager,
        json: Json
    ): HttpClient{
        val tokens = mapOf("http://10.0.2.2:9000" to token)
        return getKtorClient(tokens, json)
    }
}