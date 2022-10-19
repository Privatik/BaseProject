package io.my.data.remote

import io.my.data.remote.network.JWTToken

interface TokenManagerProxy{
    suspend fun updateTokens(accessToken: String?, refreshToken: String?)
    suspend fun getAccessToken(): String?
    suspend fun getRefreshToken(): String?
}

internal class TokenManagerProxyImpl(
    private val tokenManager: JWTToken.TokenManager
): TokenManagerProxy {
    override suspend fun updateTokens(accessToken: String?, refreshToken: String?) = tokenManager.updateTokens(accessToken, refreshToken)
    override suspend fun getAccessToken(): String? = tokenManager.getAccessToken()
    override suspend fun getRefreshToken(): String? = tokenManager.getRefreshToken()
}