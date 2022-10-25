package io.my.data.remote.token.provider

internal interface TokenProvider {
    suspend fun updateToken(token: String?)
    suspend fun getToken(): String?
}