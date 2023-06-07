package io.my.core.domain

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface CoroutineDispatchersContext{
    val IO: CoroutineContext
    val CPU: CoroutineContext
}

fun getCoroutineDispatchersContext(): CoroutineDispatchersContext = CoroutineDispatchersContextImpl

private object CoroutineDispatchersContextImpl: CoroutineDispatchersContext {
    override val IO: CoroutineContext = Dispatchers.IO
    override val CPU: CoroutineContext = Dispatchers.Default
}