package io.my.data.remote

import io.ktor.client.*
import io.my.data.local.DataStoreManager
import io.my.data.local.GlobalKeysForDataStore
import io.my.data.remote.model.RefreshApiRequest
import io.my.data.remote.model.TokenResponse
import io.my.data.remote.model.Wrap
import io.my.data.remote.token.JWTAuthorization
import io.my.data.remote.token.jwtAuthorizationAttribute
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class RefreshApi @Inject constructor(
    private val baseApiProperty: BaseApiProperty,
    private val dataStoreManager: DataStoreManager
) {

    suspend fun refresh(client: HttpClient): Result<Wrap<TokenResponse>> = client.postAsResult(
        urlString = "${baseApiProperty.getBaseApi()}$refreshEndPath"
    ) {
        attributes.put(jwtAuthorizationAttribute,  JWTAuthorization.REFRESH)
        body = RefreshApiRequest(dataStoreManager.data.map { pref -> pref[GlobalKeysForDataStore.userId].orEmpty() }.first())
    }

    companion object {
        private const val refreshEndPath = "/api/refresh_token"
    }
}