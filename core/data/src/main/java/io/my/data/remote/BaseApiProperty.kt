package io.my.data.remote

import io.my.data.local.DataStoreManager
import io.my.data.local.GlobalKeysForDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class BaseApiProperty(
    private val dataStoreManager: DataStoreManager
) {

    suspend fun getBaseApi(): String = dataStoreManager.data
        .map { pref -> pref[GlobalKeysForDataStore.baseApi].orEmpty() }.first()

    suspend fun setBaseApi(api: String) {
        dataStoreManager.edit { pref ->
            pref[GlobalKeysForDataStore.baseApi] = api
        }
    }
}