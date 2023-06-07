package io.my.data.remote

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

internal fun getKtorClient(
    json: Json,
    engine: HttpClientEngineFactory<*>,
): HttpClient {
    val client = HttpClient(engine) {
        expectSuccess = true

        install(ContentNegotiation) {
            json(
                json = json,
                contentType = ContentType.Application.Json
            )
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d("Logger Ktor =>", message)
                }
            }
            level = LogLevel.ALL
        }
    }
    return client
}