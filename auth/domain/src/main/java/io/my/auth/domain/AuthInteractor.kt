package io.my.auth.domain

import android.util.Log
import io.my.auth.domain.repository.AuthRepository
import io.my.core.domain.BaseInteractor
import io.my.core.domain.StateModel
import io.my.core.domain.asStateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AuthInteractor{

    suspend fun sinIn(
        login: String,
        password: String
    )

    suspend fun checkValid()

    val singInFlow: Flow<StateModel<String>>
    val isValidFlow: Flow<StateModel<Boolean>>
}

internal class AuthInteractorImpl @Inject constructor(
    private val repository: AuthRepository
): BaseInteractor<State>(State()), AuthInteractor{

    override val handleDataFromOutSide: Flow<State> = merge(
        repository.singInFlow.updateState { state, payload ->
            state.copy(singIn = payload.map { it.email }.asStateModel())
        },
        repository.validFlow.updateState { state, payload ->
            state.copy(isValid = payload.asStateModel())
        }
    )

    override val singInFlow: Flow<StateModel<String>> = state.map { it.singIn }.distinctUntilChanged()
    override val isValidFlow: Flow<StateModel<Boolean>> = state.map { it.isValid }.distinctUntilChanged()

    override suspend fun sinIn(login: String, password: String) = withContext(Dispatchers.IO) {
        updateState { state -> state.copy(singIn = StateModel.Loading) }
        repository.singIn(login, password)
    }

    override suspend fun checkValid() = withContext(Dispatchers.IO) {
        updateState { state -> state.copy(isValid = StateModel.Loading) }
        repository.checkValid()
    }

}

internal data class State(
    val singIn: StateModel<String> = StateModel.None,
    val isValid: StateModel<Boolean> = StateModel.None
)