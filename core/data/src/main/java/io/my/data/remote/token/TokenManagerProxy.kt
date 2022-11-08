package io.my.data.remote.token

import javax.inject.Inject

interface TokenManagerProxy{
    suspend fun updateTokens(accessToken: String?, refreshToken: String?)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
}

internal class TokenManagerProxyImpl @Inject constructor(
    private val tokenManager: JWTToken.TokenManager
): TokenManagerProxy {
    override suspend fun updateTokens(accessToken: String?, refreshToken: String?) = tokenManager.updateTokens(accessToken, refreshToken)
    override suspend fun getAccessToken(): String? = tokenManager.getAccessToken()
    override suspend fun getRefreshToken(): String? = tokenManager.getRefreshToken()
}