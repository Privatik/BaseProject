package io.my.auth.domain

import io.my.auth.domain.repository.AuthRepository
import io.my.core.domain.BaseInteractor
import io.my.core.domain.StateModel
import io.my.core.domain.asStateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

interface AuthInteractor{

    suspend fun sinIn(
        login: String,
        password: String
    )

    suspend fun checkValid()

    val singInFlow: Flow<StateModel>
    val isValidFlow: Flow<StateModel>
}

internal class AuthInteractorImpl(
    private val repository: AuthRepository
): BaseInteractor<State>(State()), AuthInteractor{

    override val singInFlow: Flow<StateModel> = state.map { it.singIn }.distinctUntilChanged()
    override val isValidFlow: Flow<StateModel> = state.map { it.isValid }.distinctUntilChanged()

    override suspend fun sinIn(login: String, password: String) = withContext(Dispatchers.IO) {
        updateState { state -> state.copy(singIn = StateModel.Loading) }

        val newSing = repository.singIn(login, password).asStateModel()

        updateState { state -> state.copy(singIn = newSing) }
    }

    override suspend fun checkValid() {
        updateState { state -> state.copy(isValid = StateModel.Loading) }

        val newValid = repository.checkValid().asStateModel()

        updateState { state -> state.copy(isValid = newValid) }
    }

}

internal data class State(
    val singIn: StateModel = StateModel.None,
    val isValid: StateModel = StateModel.None
)