package io.my.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import io.my.data.security.CryptoManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

object GlobalKeysForDataStore{
    val baseApi = stringPreferencesKey("base-api")
    val refreshToken = stringPreferencesKey("refresh-token")
    val userId = stringPreferencesKey("user-id")
}

interface DataStoreManager{
    val data: Flow<Preferences>

    fun <T: Any> secureData(
        keyAlias: String,
        fetchValue: (value: Preferences) -> String?,
        mapper: (String) -> T
    ): Flow<T>

    suspend fun edit(transform: suspend (MutablePreferences) -> Unit): Preferences

    suspend fun securityEdit(
        keyAlias: String,
        value: String,
        editStorage: suspend (MutablePreferences, String) -> Unit
    ): Preferences
}

internal val Context.preferencesDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "GlobalPreferences"
)

internal class DataStoreManagerImpl(
    private val dataStore: DataStore<Preferences>,
    private val cryptoManager: CryptoManager
): DataStoreManager {
    override val data: Flow<Preferences> = dataStore.data

    override fun <T: Any> secureData(
        keyAlias: String,
        fetchValue: (value: Preferences) -> String?,
        mapper: (String) -> T
    ): Flow<T>{
        return data.map {
            val value = fetchValue(it)
            if (value.isNullOrBlank()) return@map mapper("")

            val decryptedValue = cryptoManager.decryptData(
                keyAlias,
                value
            )

            mapper(decryptedValue)
        }
    }

    override suspend fun edit(transform: suspend (MutablePreferences) -> Unit): Preferences{
        return dataStore.edit(transform)
    }

    override suspend fun securityEdit(
        keyAlias: String,
        value: String,
        editStorage: suspend (MutablePreferences, String) -> Unit
    ): Preferences{
        return dataStore.edit {
            if (value.isBlank()) {
                editStorage.invoke(it, "")
            } else {
                val encryptedValue = cryptoManager.encryptData(keyAlias, value)
                editStorage.invoke(it, encryptedValue)
            }
        }
    }

}