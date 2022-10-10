package io.my.data.remote.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.util.*

public class JWTToken() {
    public var tokenManager: TokenManager? = null
    public var urlEncodedPathWithOutToken: Set<String> = setOf()
    public var urlEncodedPathWithRefreshToken: Set<String> = setOf()

    public companion object Feature : HttpClientFeature<JWTToken, JWTToken> {
        override val key: AttributeKey<JWTToken> = AttributeKey("JWTToken")

        override fun prepare(block: JWTToken.() -> Unit): JWTToken =
            JWTToken().apply(block)

        override fun install(feature: JWTToken, scope: HttpClient) {
            scope.requestPipeline.intercept(HttpRequestPipeline.Before) {
                if (feature.tokenManager == null) return@intercept

                val endPath = context.url.encodedPath
                when {
                    feature.urlEncodedPathWithOutToken.contains(endPath) -> {}
                    feature.urlEncodedPathWithRefreshToken.contains(endPath) -> {
                        context.updateJWTToken(feature.tokenManager?.getRefreshToken() ?: "")
                    }
                    else -> {
                        val accessToken = feature.tokenManager?.getAccessToken() ?: kotlin.run {
                            feature.tokenManager?.getNewIfNeedToken(scope, "Bearer ${null}") ?: ""
                        }
                        context.updateJWTToken(accessToken)
                    }
                }
            }

            scope.feature(HttpSend)!!.intercept { call, builder ->
                feature.tokenManager?.let { manager ->
                    if (call.response.status.value == 401) {
                        val accessToken = manager.getNewIfNeedToken(
                            scope,
                            builder.headers[HttpHeaders.Authorization]
                        ) ?: return@intercept call

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
        suspend fun getNewIfNeedToken(client: HttpClient, oldToken: String?): String?
        suspend fun updateTokens(accessToken: String?, refreshToken: String?)
        suspend fun getAccessToken(): String?
        suspend fun getRefreshToken(): String?
    }
}