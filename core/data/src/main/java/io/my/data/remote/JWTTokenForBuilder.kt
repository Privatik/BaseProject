package io.my.data.remote.network

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*
import io.my.data.remote.JWTAuthorization
import io.my.data.remote.jwtAuthorizationAttribute

internal class JWTToken() {
    var tokenManagerMap: Map<String, TokenManager> = emptyMap()

    companion object Feature : HttpClientFeature<JWTToken, JWTToken> {
        override val key: AttributeKey<JWTToken> = AttributeKey("JWTToken")

        override fun prepare(block: JWTToken.() -> Unit): JWTToken =
            JWTToken().apply(block)

        override fun install(feature: JWTToken, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                val tokenManager = feature.tokenManagerMap[context.url.host] ?: return@intercept

                when (context.attributes[jwtAuthorizationAttribute]){
                    JWTAuthorization.NONE -> { }
                    JWTAuthorization.REFRESH -> {
                        context.updateJWTToken(tokenManager.getRefreshToken() ?: "")
                    }
                    else -> {
                        val accessToken = tokenManager.getAccessToken() ?: kotlin.run {
                            tokenManager.getNewIfNeedToken(scope, "Bearer ${null}") ?: ""
                        }
                        context.updateJWTToken(accessToken)
                    }
                }
            }

            scope.feature(HttpSend)!!.intercept { call, builder ->
                val tokenManager = feature.tokenManagerMap[call.request.url.host]

                tokenManager?.let { manager ->
                    if (call.response.status.value == 401) {
                        val accessToken = manager
                            .getNewIfNeedToken(scope, builder.headers[HttpHeaders.Authorization])
                                ?: return@intercept call

                        builder.updateJWTToken(accessToken)
                        return@intercept execute(builder)
                    }
                    call
                } ?: call
            }
        }

        private fun HttpRequestBuilder.updateJWTToken(token: String){
            headers.remove(HttpHeaders.Authorization)
            header(HttpHeaders.Authorization, "Bearer $token")
        }
    }

    interface TokenManager{
        suspend fun getNewIfNeedToken(
            client: HttpClient,
            oldToken: String?
        ): String?

        suspend fun updateTokens(accessToken: String?, refreshToken: String?)
        suspend fun getAccessToken(): String?
        suspend fun getRefreshToken(): String?
    }
}