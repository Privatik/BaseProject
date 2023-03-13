package io.my.testing

import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.mock.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.http.*

fun createMockHttpClient(
    handler: suspend MockRequestHandleScope.(request: HttpRequestData, defaultResponseHeaders: Headers) -> HttpResponseData,
): HttpClient {
    val responseHeaders = headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
    return HttpClient(MockEngine){
        engine {
            addHandler { request -> handler(request, responseHeaders) }
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer(
                kotlinx.serialization.json.Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                }
            )
        }

        install(DefaultRequest) {
            if (!(body is FormDataContent || body is MultiPartFormDataContent)) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
        }
    }
}