package io.my.core.domain

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

sealed class StateModel<out B: Any> {
    object None: StateModel<Nothing>()
    object Loading: StateModel<Nothing>()
    data class Content<B: Any>(val data: B): StateModel<B>()
    data class Error<B: Any>(val defBody: B?, val throwable: Throwable): StateModel<B>()
}

@OptIn(ExperimentalContracts::class)
fun <B: Any> StateModel<B>.isSuccess(): Boolean{
    contract {
        returns(true) implies (this@isSuccess is StateModel.Content)
    }

    return this is StateModel.Content
}

@OptIn(ExperimentalContracts::class)
fun <B: Any> StateModel<B>.isLoading(): Boolean{
    contract {
        returns(true) implies (this@isLoading is StateModel.Loading)
    }

    return this is StateModel.Loading
}

@OptIn(ExperimentalContracts::class)
fun <B: Any> StateModel<B>.isFail(): Boolean {
    contract {
        returns(true) implies (this@isFail is StateModel.Error)
    }

    return this is StateModel.Error
}

fun <B: Any, R: Any> StateModel<B>.map(block: (B) -> R): StateModel<R>{
    if (isSuccess()) return block(data).asContentStateModel()
    @Suppress("UNCHECKED_CAST")
    return this as StateModel<R>
}

fun <B: Any> B.asContentStateModel(): StateModel<B> = StateModel.Content(this)

fun <B: Any> Result<B>.asStateModel(): StateModel<B>{
    val content = getOrNull()
    if (content != null){
        return StateModel.Content(content)
    }
    return StateModel.Error(null,exceptionOrNull() ?: Throwable("Error cannot be determined"))
}