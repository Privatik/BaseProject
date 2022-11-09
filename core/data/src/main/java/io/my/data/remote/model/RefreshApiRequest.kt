package io.my.data.remote.model

@kotlinx.serialization.Serializable
data class RefreshApiRequest(
    val userId: String
)
