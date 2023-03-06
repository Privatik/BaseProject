package io.my.data.di

import android.content.Context
import dagger.Module
import dagger.Provides
import io.my.data.local.DataStoreManager
import io.my.data.local.DataStoreManagerImpl
import io.my.data.local.preferencesDataStore
import javax.inject.Singleton

@Module
internal class CacheModule {

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStoreManager{
        return DataStoreManagerImpl(context.preferencesDataStore)
    }
}