package io.my.core

import android.util.Log
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

interface IntentFlog
class EmptyIntent: IntentFlog

interface Intent

class IntentWithOutParams(
    private val tag: String,
    internal val intentWithParams: IntentWithParams<Unit> = IntentWithParams(tag)
): Intent {
    operator fun invoke() {
        intentWithParams(Unit)
    }
}

class IntentWithParams<P: Any>(
    private val tag: String
): Intent {
    private val _data = MutableSharedFlow<P>(replay = 1, onBufferOverflow = BufferOverflow.DROP_LATEST)
    internal val data = _data.asSharedFlow()

    operator fun invoke(params: P){
        Log.d("DoIntent","try put $tag")
        _data.tryEmit(params).also { if (it) Log.d("DoIntent","put $tag") }
    }
}

fun <P: Any> createIntent(tag: String): IntentWithParams<P>{
    return IntentWithParams<P>(tag)
}

fun createIntentWithoutParams(tag: String): IntentWithOutParams{
    return IntentWithOutParams(tag)
}

fun <P: Any> IntentWithParams<P>.asFlow(): Flow<P> = data
fun IntentWithOutParams.asFlow(): Flow<Unit> = intentWithParams.asFlow()