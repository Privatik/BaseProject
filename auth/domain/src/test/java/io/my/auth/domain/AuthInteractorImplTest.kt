@file:OptIn(ExperimentalCoroutinesApi::class)

package io.my.auth.domain

import com.google.common.truth.Truth.assertThat
import io.my.auth.domain.dto.AuthModelDTO
import io.my.auth.domain.repository.AuthRepository
import io.my.testing.MainCoroutineRule
import io.my.testing.awaitTerminateFlow
import io.my.testing.suspendWhen
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.checkerframework.checker.units.qual.A
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

class AuthInteractorImplTest{

    private lateinit var interactor: AuthInteractor
    private lateinit var repository: AuthRepository

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()
    var singFlow = MutableSharedFlow<Result<AuthModelDTO>>()

    @Before
    fun setUp(){
        singFlow = MutableSharedFlow<Result<AuthModelDTO>>(replay = 1)

        repository = mock<AuthRepository>()
        setupMockito()

        interactor = AuthInteractorImpl(repository)

    }

    @Test
    fun doAuthWithSuccess() = runTest(mainDispatcherRule.dispatcher) {
        launch {
            interactor.sinIn("login","password")
        }

        val lastValue = singFlow.awaitTerminateFlow()

        assertThat(lastValue?.isSuccess).isTrue()

    }

    @Test
    fun doAuthWithError() = runTest(mainDispatcherRule.dispatcher) {
        launch {
            interactor.sinIn("","")
        }

        val lastValue = singFlow.awaitTerminateFlow()

        assertThat(lastValue?.isFailure).isTrue()
    }

    private fun setupMockito(){
        Mockito.`when`(repository.singInFlow).thenReturn(singFlow)

        suspendWhen(
            dispatcher = mainDispatcherRule.dispatcher,
            methodCall = {
                repository.singIn("","")
            },
            methodAnswer = {
                singFlow.emit(Result.failure(Exception()))
            }
        )

        suspendWhen(
            dispatcher = mainDispatcherRule.dispatcher,
            methodCall = {
                repository.singIn("login","password")
            },
            methodAnswer = {
                singFlow.emit(Result.success(AuthModelDTO("login")))
            }
        )
    }
}