package io.my.auth.domain.repository

import io.my.auth.domain.dto.AuthModelDTO
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun singIn(login: String, password: String): Result<AuthModelDTO>
    suspend fun checkValid(): Result<Boolean>
}