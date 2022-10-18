package io.my.auth.data

import io.my.auth.data.remote.LoginAndCheckValidApi
import io.my.auth.domain.dto.AuthModelDTO
import io.my.auth.domain.repository.AuthRepository
import io.my.data.local.DataStoreManager

class AuthRepositoryImpl(
    private val api: LoginAndCheckValidApi,
    private val dataStoreManager: DataStoreManager
): AuthRepository {

    override suspend fun singIn(login: String, password: String): Result<AuthModelDTO> =
        api.singIn(login, password)
            .onSuccess {

            }
            .map { AuthModelDTO(true) }
}