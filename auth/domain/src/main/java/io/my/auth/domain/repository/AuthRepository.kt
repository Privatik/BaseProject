package io.my.auth.domain.repository

import io.my.auth.domain.dto.AuthModelDTO

interface AuthRepository {
    suspend fun singIn(login: String, password: String): AuthModelDTO
}