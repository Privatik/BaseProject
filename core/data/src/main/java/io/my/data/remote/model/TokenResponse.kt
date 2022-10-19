package io.my.data.remote.model

@kotlinx.serialization.Serializable
data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)
