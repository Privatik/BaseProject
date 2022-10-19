package io.my.auth.domain.repository

import io.my.auth.domain.dto.AuthModelDTO
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val validFlow: Flow<Result<Boolean>>
    val singInFlow: Flow<Result<AuthModelDTO>>

    suspend fun singIn(login: String, password: String)
    suspend fun checkValid()
}