package io.my.data.remote

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.websocket.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.my.data.remote.network.JWTToken
import kotlinx.serialization.json.Json

internal fun getKtorClient(
    jwtTokenManager: JWTToken.TokenManager,
    urlEncodedWithOutToken: Set<String>,
    urlEncodedWithRefreshToken: Set<String>,
): HttpClient{
    return HttpClient(CIO) {
        expectSuccess = true

        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }

        install(JWTToken){
            tokenManager = jwtTokenManager
            urlEncodedPathWithOutToken = urlEncodedWithOutToken
            urlEncodedPathWithRefreshToken = urlEncodedWithRefreshToken
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Logger Ktor =>", message)
                }

            }
            level = LogLevel.ALL
        }

        install(DefaultRequest) {
            if (!(body is FormDataContent || body is MultiPartFormDataContent)) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}