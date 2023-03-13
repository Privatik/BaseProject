package io.my.auth.data.di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.mock.*
import io.ktor.http.*
import io.my.auth.data.remote.LoginAndCheckValidApiImpl
import io.my.auth.data.remote.model.LoginModelRequest
import io.my.auth.data.remote.model.LoginModelResponse
import io.my.auth.data.remote.model.UserResponse
import io.my.testing.createMockHttpClient
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Module
class TestClientModule {

    @Provides
    fun provideClient(): HttpClient {
        return createMockHttpClient { request, defaultResponseHeaders ->
            return@createMockHttpClient when(request.url.encodedPath){
                LoginAndCheckValidApiImpl.singEndPath -> {
                    val requestBody = Json.decodeFromString(
                        LoginModelRequest.serializer(),
                        request.body.toByteArray().decodeToString()
                    )

                    val response = LoginModelResponse(user = UserResponse("login", "password"))
                        .let { Json.encodeToString(it) }
                    val status = if (requestBody.email.isBlank()) HttpStatusCode.BadRequest else HttpStatusCode.OK
                    respond(response, status, defaultResponseHeaders)
                }
                else -> {
                    error("Unhandled ${request.url.encodedPath}")
                }
            }
        }
    }

}