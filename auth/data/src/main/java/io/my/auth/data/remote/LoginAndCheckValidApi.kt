package io.my.auth.data.remote

import io.ktor.client.*
import io.ktor.client.statement.*
import io.my.auth.data.remote.model.LoginModelRequest
import io.my.auth.data.remote.model.LoginModelResponse
import io.my.auth.data.remote.model.Wrap
import io.my.data.remote.*
import io.my.data.remote.token.JWTAuthorization
import io.my.data.remote.token.jwtAuthorizationAttribute
import kotlinx.coroutines.launch
import javax.inject.Inject

interface LoginAndCheckValidApi{

    suspend fun singIn(
        login: String,
        password: String
    ): Result<Wrap<LoginModelResponse>>

    suspend fun valid(): Result<HttpResponse>
}

internal class LoginAndCheckValidApiImpl @Inject constructor(
    private val client: HttpClient,
    private val baseApiProperty: BaseApiProperty
): LoginAndCheckValidApi{

    init {
        client.launch {
            baseApiProperty.setBaseApi("http://10.0.2.2:9000")
        }
    }

    override suspend fun singIn(
        login: String,
        password: String
    ): Result<Wrap<LoginModelResponse>> = client.postAsResult(
        urlString = "${baseApiProperty.getBaseApi()}$singEndPath"
    ){
        attributes.put(jwtAuthorizationAttribute, JWTAuthorization.NONE)
        body = LoginModelRequest(login, password)
    }

    override suspend fun valid(): Result<HttpResponse> = client.getAsResult(
        urlString = "${baseApiProperty.getBaseApi()}$validEndPath"
    ){

    }

    companion object{
        private const val singEndPath = "/api/login"
        private const val validEndPath = "/api/valid"
    }

}