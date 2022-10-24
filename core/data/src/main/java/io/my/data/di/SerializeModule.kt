package io.my.data.di

import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
class SerializeModule {

    @Provides
    @Singleton
    fun provideJson(): Json{
        return Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
        }
    }
}