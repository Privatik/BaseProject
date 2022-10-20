package io.my.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import io.my.data.local.DataStoreManager
import io.my.data.local.DataStoreManagerImpl
import io.my.data.local.preferencesDataStore
import io.my.data.security.CryptoManager
import javax.inject.Singleton

@Module
class LocalModule {

    @Provides
    @Singleton
    fun provideDataStore(context: Context): DataStoreManager{
        val cryptoManager = CryptoManager()
        return DataStoreManagerImpl(context.preferencesDataStore, cryptoManager)
    }
}