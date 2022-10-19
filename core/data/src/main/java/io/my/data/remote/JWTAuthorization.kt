package io.my.data.remote

import io.ktor.util.*

enum class JWTAuthorization {
    NONE,
    REFRESH,
    ACCESS
}

val jwtAuthorizationAttribute = AttributeKey<JWTAuthorization>("JWTAuthorization")

