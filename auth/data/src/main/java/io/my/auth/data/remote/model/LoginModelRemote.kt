package io.my.auth.data.remote.model

import io.my.data.remote.model.TokenResponse
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginModelRequest(
    val email: String,
    val password: String
)

@Serializable
data class LoginModelResponse(
    @SerialName("tokens")
    val tokenResponse: TokenResponse,
    val user: UserResponse
)

@Serializable
data class UserResponse(
    val email: String,
    @SerialName("id")
    val userId: String
)
