package io.my.auth.data.remote

import io.ktor.client.*
import io.ktor.client.statement.*
import io.ktor.util.*
import io.my.auth.data.remote.model.LoginModelRemote
import io.my.data.remote.*
import javax.inject.Inject

interface LoginAndCheckValidApi{

    suspend fun singIn(
        login: String,
        password: String
    ): Result<LoginModelRemote>

    suspend fun valid(): Result<HttpResponse>
}

internal class LoginAndCheckValidApiImpl @Inject constructor(
    private val client: HttpClient,
    private val baseApiProperty: BaseApiProperty
): LoginAndCheckValidApi{

    override suspend fun singIn(
        login: String,
        password: String
    ): Result<LoginModelRemote> = client.postAsResult(
        urlString = "${baseApiProperty.getBaseApi()}$singEndPath"
    ){
        attributes.put(jwtAuthorizationAttribute, JWTAuthorization.NONE)
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