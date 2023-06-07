package io.my.auth.domain

import io.my.auth.domain.repository.AuthRepository
import io.my.core.domain.BaseInteractor
import io.my.core.domain.StateModel
import io.my.core.domain.asStateModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface AuthInteractor{

    suspend fun sinIn(
        login: String,
        password: String
    )

    val singInFlow: Flow<StateModel<String>>
}

internal class AuthInteractorImpl @Inject constructor(
    private val repository: AuthRepository
): BaseInteractor<State>(State()), AuthInteractor{

    override val handleDataFromOutSide: Flow<State> = merge(
        repository.singInFlow.updateState { state, payload ->
            state.copy(singIn = payload.map { it.email }.asStateModel())
        },
    )

    override suspend fun sinIn(login: String, password: String) = withContext(Dispatchers.IO) {
        updateState { state -> state.copy(singIn = StateModel.Loading) }
        repository.singIn(login, password)
    }

    override val singInFlow: Flow<StateModel<String>> by lazy {
        state { it.singIn }.distinctUntilChanged()
    }


}

internal data class State(
    val singIn: StateModel<String> = StateModel.None,
)