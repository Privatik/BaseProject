package io.my.data.remote

import android.util.Log
import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.lang.Exception

suspend inline fun <reified T> HttpClient.requestAsResult(
    urlString: String,
    method: HttpMethod,
    block: HttpRequestBuilder.() -> Unit = {}
): Result<T> {
    return try {
        val response = request<T> {
            url.takeFrom(urlString)
            this.method = method
            block()
        }
        Result.success(response)
    } catch (e: ResponseException){
        Log.e("Ktor","$e")
        Result.failure(e)
    } catch (e: Exception){
        Log.e("Ktor","$e")
        Result.failure(e)
    }
}

suspend inline fun <reified T> HttpClient.getAsResult(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit
): Result<T> = requestAsResult(
    urlString = urlString,
    method = HttpMethod.Get,
    block = block
)

suspend inline fun <reified T> HttpClient.postAsResult(
    urlString: String,
    block: HttpRequestBuilder.() -> Unit
): Result<T> = requestAsResult(
    urlString = urlString,
    method = HttpMethod.Post,
    block = block
)
