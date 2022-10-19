package io.my.core.domain

sealed class StateModel<out B: Any> {
    object None: StateModel<Nothing>()
    object Loading: StateModel<Nothing>()
    data class Content<B: Any>(val data: B): StateModel<B>()
    data class Error(val throwable: Throwable): StateModel<Nothing>()
}

fun <B: Any> Result<B>.asStateModel(): StateModel<B>{
    return  if (isSuccess) StateModel.Content(getOrNull()!!)
            else StateModel.Error(exceptionOrNull()!!)
}