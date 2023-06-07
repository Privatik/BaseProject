package io.my.auth.data

import io.my.auth.data.remote.LoginAndCheckValidApi
import io.my.auth.domain.dto.AuthModelDTO
import io.my.auth.domain.repository.AuthRepository
import io.my.data.cache.DataStoreManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: LoginAndCheckValidApi,
    private val dataStoreManager: DataStoreManager
): AuthRepository {
    private val _validFlow = MutableSharedFlow<Result<Boolean>>(replay = 1)
    override val validFlow: Flow<Result<Boolean>> = _validFlow.asSharedFlow()

    private val _singInFlow = MutableSharedFlow<Result<AuthModelDTO>>(replay = 1)
    override val singInFlow: Flow<Result<AuthModelDTO>> = _singInFlow.asSharedFlow()

    override suspend fun singIn(login: String, password: String) {
        api.singIn(login, password).map { AuthModelDTO(login) }.also { _singInFlow.emit(it) }
    }

}