package io.my.core.domain.trowable

sealed class Fail(
    override val message: String?
): Throwable(message){
    object AuthFail: Fail(message = "Error 401")
    object ForbiddenFail: Fail(message = "Error 403")
    class GlobalFail(throwable: Throwable): Fail(throwable.message)
}