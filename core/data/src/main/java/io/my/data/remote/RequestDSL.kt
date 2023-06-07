package io.my.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.my.core.domain.trowable.Fail

suspend inline fun <reified T> HttpClient.request(
    urlString: String,
    method: HttpMethod,
    block: HttpRequestBuilder.() -> Unit = {}
): Result<T> {
    return try {
        val response = request {
            url.takeFrom(urlString)
            this.method = method
            block()
        }

        return if (T::class is HttpResponse) Result.success(response as T)
        else Result.success(response.body())
    } catch (e: Exception) {
        Result.failure(Fail.GlobalFail(e))
    }
}

suspend inline fun <reified T> HttpClient.get(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit
): Result<T> = request(
    urlString = urlString,
    method = HttpMethod.Get,
    block = block
)

suspend inline fun <reified T> HttpClient.post(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit
): Result<T> = request(
    urlString = urlString,
    method = HttpMethod.Post,
    block = block
)