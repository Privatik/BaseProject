package io.my.auth.data.remote

import io.ktor.client.*
import io.ktor.client.statement.*
import io.my.auth.data.remote.model.LoginModelRequest
import io.my.auth.data.remote.model.LoginModelResponse
import io.my.data.remote.*
import javax.inject.Inject

interface LoginAndCheckValidApi{

    suspend fun singIn(
        login: String,
        password: String
    ): Result<LoginModelResponse>

    suspend fun valid(): Result<HttpResponse>
}

internal class LoginAndCheckValidApiImpl @Inject constructor(
    private val client: HttpClient,
): LoginAndCheckValidApi{

    override suspend fun singIn(
        login: String,
        password: String
    ): Result<LoginModelResponse> = client.post(
        urlString = "127.0.0.1$singEndPath"
    ){
        body = LoginModelRequest(login, password)
    }

    override suspend fun valid(): Result<HttpResponse> = client.get(
        urlString = "127.0.0.1$validEndPath"
    ){

    }

    companion object{
        private const val singEndPath = "/api/login"
        private const val validEndPath = "/api/valid"
    }

}