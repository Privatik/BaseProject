package io.my.data.remote

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import kotlinx.serialization.json.Json

internal fun getKtorClient(
    json: Json,
): HttpClient{
    return HttpClient(CIO) {
        expectSuccess = true

        install(JsonFeature) {
            serializer = KotlinxSerializer(json)
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