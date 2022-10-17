package io.my.auth.data

import io.my.auth.domain.dto.AuthModelDTO
import io.my.auth.domain.repository.AuthRepository

class AuthRepositoryImpl(

): AuthRepository {

    override suspend fun singIn(login: String, password: String): AuthModelDTO {

    }
}