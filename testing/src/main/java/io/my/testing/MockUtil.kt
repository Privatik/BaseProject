@file:OptIn(ExperimentalCoroutinesApi::class)

package io.my.testing

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.runTest
import org.mockito.kotlin.whenever

fun <T, R> suspendWhen(
    dispatcher: TestDispatcher,
    methodCall: suspend () -> T,
    methodAnswer: suspend () -> R
) = runTest(dispatcher) {
    whenever(methodCall()).then{
        runTest(dispatcher) { methodAnswer() }
    }
}