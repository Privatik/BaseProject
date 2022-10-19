package io.my.auth.data.remote.model

import io.my.data.remote.model.TokenResponse

@kotlinx.serialization.Serializable
data class LoginModelRemote(
    val tokenResponse: TokenResponse,
    val user: UserResponse
)

@kotlinx.serialization.Serializable
data class UserResponse(
    val email: String
)
