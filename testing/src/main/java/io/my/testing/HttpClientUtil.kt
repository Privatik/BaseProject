package io.my.testing

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

fun createMockHttpClient(
    handler: suspend MockRequestHandleScope.(request: HttpRequestData, defaultResponseHeaders: Headers) -> HttpResponseData,
): HttpClient {
    val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
    return HttpClient(MockEngine){
        engine {
            addHandler { request -> handler(request, responseHeaders) }
        }

        install(ContentNegotiation) {
            json(
                json = Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                },
                contentType = ContentType.Application.Json
            )
        }
    }
}