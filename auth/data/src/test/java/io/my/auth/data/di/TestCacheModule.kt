package io.my.auth.data.di

import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import io.my.data.local.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Singleton

@Module
class TestCacheModule {

    @Provides
    @Singleton
    fun provideDataStore(): DataStoreManager {
        return object : DataStoreManager{
            override val data: Flow<Preferences>
                get() = flow {  }

            override suspend fun edit(transform: suspend (MutablePreferences) -> Unit): Preferences {
                TODO()
            }

        }
    }

}