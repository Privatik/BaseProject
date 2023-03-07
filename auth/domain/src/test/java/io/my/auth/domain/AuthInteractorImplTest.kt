@file:OptIn(ExperimentalCoroutinesApi::class)

package io.my.auth.domain

import io.my.auth.domain.repository.AuthRepository
import io.my.testing.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock

class AuthInteractorImplTest{

    private lateinit var interactor: AuthInteractor

    @get:Rule
    val mainDispatcherRule = MainCoroutineRule()

    @Before
    fun setUp(){


        val repository = mock<AuthRepository> {
            onBlocking {
                singIn("","")
            }
        }
        interactor = AuthInteractorImpl(repository)

    }

    @Test
    fun doAuthWithSuccess() = runTest(mainDispatcherRule.dispatcher) {

    }

    @Test
    fun doAuthWithError(){

    }

}