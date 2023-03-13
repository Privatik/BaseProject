package io.my.data.di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.cio.*
import io.my.data.remote.getKtorClient
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module()
internal class RemoteModule {

    @Provides
    fun provideEngine(): HttpClientEngineFactory<*> = CIO

    @Provides
    @Singleton
    fun provideClient(
        json: Json,
        engine: HttpClientEngineFactory<*>
    ): HttpClient{
        return getKtorClient(json, engine)
    }
}