package io.my.data.remote

import io.ktor.client.*
import io.my.data.remote.model.TokenResponse
import javax.inject.Inject

internal class RefreshApi @Inject constructor(
    private val client: HttpClient,
    private val baseApiProperty: BaseApiProperty
) {

    suspend fun refresh(): Result<TokenResponse> = client.postAsResult(
        urlString = "$baseApiProperty$refreshEndPath"
    ){
        attributes.put(jwtAuthorizationAttribute,  JWTAuthorization.REFRESH)
    }

    companion object {
        private const val refreshEndPath = "/api/refresh_token"
    }
}