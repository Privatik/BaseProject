package io.my.auth.domain

import io.my.auth.domain.repository.AuthRepository
import io.my.core.domain.StateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.withContext

interface AuthInteractor{

    suspend fun sinIn(
        login: String,
        password: String
    )

    val singInFlow: Flow<StateModel>
}

internal class AuthInteractorImpl(
    private val repository: AuthRepository
): AuthInteractor{
    private val _singInFlow = MutableSharedFlow<StateModel>()
    override val singInFlow: Flow<StateModel> = _singInFlow


    override suspend fun sinIn(login: String, password: String) = withContext(Dispatchers.IO) {
        TODO("Not yet implemented")
    }

}