package io.my.data.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow

object GlobalKeysForDataStore{
    val refreshToken = stringPreferencesKey("refresh-token")
    val userId = stringPreferencesKey("user-id")
}

interface DataStoreManager{
    val data: Flow<Preferences>

    suspend fun edit(transform: suspend (MutablePreferences) -> Unit): Preferences
}

internal val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "GlobalPreferences"
)

internal class DataStoreManagerImpl(
    private val dataStore: DataStore<Preferences>,
): DataStoreManager {
    override val data: Flow<Preferences> = dataStore.data

    override suspend fun edit(transform: suspend (MutablePreferences) -> Unit): Preferences{
        return dataStore.edit(transform)
    }

}