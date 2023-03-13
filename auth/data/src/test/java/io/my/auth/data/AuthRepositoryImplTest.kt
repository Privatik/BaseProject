@file:OptIn(ExperimentalCoroutinesApi::class)

package io.my.auth.data

import com.google.common.truth.Truth.assertThat
import io.my.auth.data.di.DaggerTestDataComponent
import io.my.auth.domain.repository.AuthRepository
import io.my.testing.MainCoroutineRule
import io.my.testing.awaitTerminateFlow
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AuthRepositoryTest{

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    lateinit var repository: AuthRepository

    @Before
    fun setUp(){
        val component = DaggerTestDataComponent.builder()
            .build()

        repository = component.repository()
    }

    @Test
    fun authSuccess() = runTest(mainDispatcherRule.dispatcher) {
        launch { repository.singIn("login","password") }

        val lastValue = repository.singInFlow.awaitTerminateFlow()

        assertThat(lastValue?.isSuccess).isTrue()
    }

    @Test
    fun authError() = runTest(mainDispatcherRule.dispatcher) {
        launch { repository.singIn("","") }

        val lastValue = repository.singInFlow.awaitTerminateFlow()

        assertThat(lastValue?.isSuccess).isFalse()
    }

}