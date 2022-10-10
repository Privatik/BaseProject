package io.my.data.remote.network.token.manager

import io.ktor.client.*
import io.my.data.remote.network.JWTToken
import kotlinx.coroutines.CoroutineScope

class MyJWTTokenManager(
    coroutineScope: CoroutineScope
): JWTToken.TokenManager {
    override suspend fun getNewIfNeedToken(client: HttpClient, oldToken: String?): String? {
        TODO("Not yet implemented")
    }

    override suspend fun updateTokens(accessToken: String?, refreshToken: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getAccessToken(): String? {
        TODO("Not yet implemented")
    }

    override suspend fun getRefreshToken(): String? {
        TODO("Not yet implemented")
    }
}