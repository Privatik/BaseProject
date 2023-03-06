package io.my.data.remote

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.my.core.domain.trowable.Fail
import java.lang.Exception

suspend inline fun <reified T> HttpClient.request(
    urlString: String,
    method: HttpMethod,
    block: HttpRequestBuilder.() -> Unit = {}
): Result<T> {
    return try {
        val response = this@request.request<T> {
            url.takeFrom(urlString)
            this.method = method
            block()
        }
        Result.success(response)
    }  catch (e: ResponseException){
        when (e.response.status.value){
            401 -> Result.failure(Fail.AuthFail)
            403 -> Result.failure(Fail.ForbiddenFail)
            else -> Result.failure(Fail.GlobalFail(e))
        }
    } catch (e: Exception){
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
