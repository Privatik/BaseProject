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
import io.my.data.remote.network.token.manager.MyJWTTokenManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.serialization.json.Json

internal fun getKtorClient(
    jwtTokenManagerMap: Map<String, JWTToken.TokenManager>,
    json: Json
): HttpClient{
    return HttpClient(CIO) {
        expectSuccess = true

        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
        }

        install(JWTToken){
            tokenManagerMap = jwtTokenManagerMap
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