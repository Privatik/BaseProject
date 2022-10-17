package io.my.core.domain

sealed class StateModel {
    object None: StateModel()
    object Loading: StateModel()
    data class Content<B: Any>(val data: B): StateModel()
    data class Error(val throwable: Throwable): StateModel()
}

fun <B: Any> Result<B>.asStateModel(): StateModel{
    return  if (isSuccess) StateModel.Content(getOrNull()!!)
            else StateModel.Error(exceptionOrNull()!!)
}