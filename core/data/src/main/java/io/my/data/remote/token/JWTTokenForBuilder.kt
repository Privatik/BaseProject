package io.my.data.remote.token

import android.util.Log
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

internal class JWTTokenBuilder{
    var tokenManager: JWTToken.TokenManager? = null
}

internal class JWTToken(val tokenManager: TokenManager) {

    companion object Feature : HttpClientFeature<JWTTokenBuilder, JWTToken> {
        override val key: AttributeKey<JWTToken> = AttributeKey("JWTToken")

        override fun prepare(block: JWTTokenBuilder.() -> Unit): JWTToken =
            JWTToken(JWTTokenBuilder().apply(block).tokenManager!!)

        override fun install(feature: JWTToken, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                val tokenManager = feature.tokenManager

                when (context.attributes.getOrNull(jwtAuthorizationAttribute)){
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
                feature.tokenManager.let { manager ->
                    if (call.response.status.value == 401) {
                        val accessToken = manager
                            .getNewIfNeedToken(scope, builder.headers[HttpHeaders.Authorization])
                                ?: return@intercept call

                        builder.updateJWTToken(accessToken)
                        return@intercept execute(builder)
                    }
                    call
                }
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
            oldToken: String? = null
        ): String?
        suspend fun updateTokens(accessToken: String?, refreshToken: String?)
        suspend fun getAccessToken(): String?
        suspend fun getRefreshToken(): String?
    }
}