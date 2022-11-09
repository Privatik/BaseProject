package io.my.data.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class Wrap<T>(
    val isSuccessful: Boolean,
    val message: T
)
