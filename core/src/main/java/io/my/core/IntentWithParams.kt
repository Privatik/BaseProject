package io.my.core

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class IntentFlag(coroutineScope: CoroutineScope) {

    protected fun <P: Any> createIntent(tag: String, coroutineScope: CoroutineScope): IntentWithParams<P> {
        return IntentWithParams<P>(tag, coroutineScope)
    }

    protected fun createIntentWithoutParams(tag: String, coroutineScope: CoroutineScope): IntentWithOutParams {
        return IntentWithOutParams(tag, coroutineScope)
    }
}

interface Intent<P: Any>{
    fun asFlow(): Flow<P>
}

class IntentWithOutParams(
    tag: String,
    coroutineScope: CoroutineScope,
): Intent<Unit> {
    private val intentWithParams: IntentWithParams<Unit> = IntentWithParams(tag, coroutineScope)

    override fun asFlow() = intentWithParams.asFlow()

    operator fun invoke() = intentWithParams(Unit)

}

class IntentWithParams<P: Any>(
    private val tag: String,
    private val coroutineScope: CoroutineScope
): Intent<P> {
    private val _data = MutableSharedFlow<P>()

    override fun asFlow(): Flow<P> = _data.asSharedFlow()

    operator fun invoke(params: P) {
        Log.d("DoIntent","intent $tag")
        coroutineScope.launch{
            _data.emit(params)
        }
    }
}