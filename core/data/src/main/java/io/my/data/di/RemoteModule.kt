package io.my.data.di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.my.data.remote.getKtorClient
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module()
internal class RemoteModule {

    @Provides
    @Singleton
    fun provideClient(
        json: Json
    ): HttpClient{
        return getKtorClient(json)
    }
}