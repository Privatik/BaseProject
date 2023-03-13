package io.my.testing

import app.cash.turbine.Event
import app.cash.turbine.test
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

@Suppress("ControlFlowWithEmptyBody")
suspend fun <T> Flow<T>.awaitTerminateFlow(): T?{
    val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
        if (exception !is AssertionError) throw exception
    }

    var lastValue: T? = null
    supervisorScope {
        launch(coroutineExceptionHandler) {
            test {
                while (true){
                    val event = awaitEvent()
                    if (event is Event.Item){
                        lastValue = event.value
                    }
                    if (event.isTerminal) break
                }
            }
        }
    }
    return lastValue
}