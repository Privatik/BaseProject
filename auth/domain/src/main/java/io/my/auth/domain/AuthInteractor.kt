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

    val singInFlow: Flow<StateModel<String>>
    val isValidFlow: Flow<StateModel<Boolean>>
}

internal class AuthInteractorImpl(
    private val repository: AuthRepository
): BaseInteractor<State>(State()), AuthInteractor{

    override val singInFlow: Flow<StateModel<String>> =
        merge(
            state.map { it.singIn },
            repository.singInFlow.map { result -> result.map { it.email }.asStateModel() }
        ).distinctUntilChanged()

    override val isValidFlow: Flow<StateModel<Boolean>> =
        merge(
            state.map { it.isValid },
            repository.validFlow.map { result -> result.asStateModel() }
        ).distinctUntilChanged()

    override suspend fun sinIn(login: String, password: String) = withContext(Dispatchers.IO) {
        updateState { state -> state.copy(singIn = StateModel.Loading) }
        repository.singIn(login, password)
    }

    override suspend fun checkValid() {
        updateState { state -> state.copy(isValid = StateModel.Loading) }
        repository.checkValid()
    }

}

internal data class State(
    val singIn: StateModel<String> = StateModel.None,
    val isValid: StateModel<Boolean> = StateModel.None
)