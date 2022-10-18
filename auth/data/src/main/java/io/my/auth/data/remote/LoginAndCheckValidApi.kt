package io.my.auth.data.remote

import io.ktor.client.*
import io.my.data.remote.getAsResult
import io.my.data.remote.postAsResult

interface LoginAndCheckValidApi{

    suspend fun singIn(
        login: String,
        password: String
    ): Result<LoginAndCheckValidApi>

    suspend fun valid(): Result<Boolean>
}

internal class LoginAndCheckValidApiImpl(
    private val client: HttpClient
): LoginAndCheckValidApi{

    override suspend fun singIn(
        login: String,
        password: String
    ): Result<LoginAndCheckValidApi> = client.postAsResult(
        urlString = ""
    ){
    }

    override suspend fun valid(): Result<Boolean> = client.getAsResult(
        urlString = ""
    ){
    }

}