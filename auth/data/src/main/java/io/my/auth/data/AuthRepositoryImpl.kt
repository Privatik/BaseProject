package io.my.auth.data

import android.util.Log
import io.my.auth.data.remote.LoginAndCheckValidApi
import io.my.auth.domain.dto.AuthModelDTO
import io.my.auth.domain.repository.AuthRepository
import io.my.data.remote.token.TokenManagerProxy
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: LoginAndCheckValidApi,
    private val tokenManager: TokenManagerProxy
): AuthRepository {
    private val _validFlow = MutableSharedFlow<Result<Boolean>>()
    override val validFlow: Flow<Result<Boolean>> = _validFlow.asSharedFlow()

    private val _singInFlow = MutableSharedFlow<Result<AuthModelDTO>>()
    override val singInFlow: Flow<Result<AuthModelDTO>> = _singInFlow.asSharedFlow()

    override suspend fun singIn(login: String, password: String) {
        _singInFlow.emit(
            api.singIn(login, password)
                .onSuccess {
                    val tokens = it.message.tokenResponse
                    tokenManager.updateTokens(
                        accessToken = tokens.accessToken,
                        refreshToken = tokens.refreshToken
                    )
                }
                .map { AuthModelDTO(it.message.user.email) }
        )
    }


    override suspend fun checkValid() {
        _validFlow.emit(
            api.valid().map { it.call.response.status.value == 200 }
        )
    }
}