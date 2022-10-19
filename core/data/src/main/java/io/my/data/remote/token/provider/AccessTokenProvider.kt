package io.my.data.remote.token.provider

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor

internal class AccessTokenProvider(
    coroutineScope: CoroutineScope
): TokenProvider {

    private val channel: SendChannel<Action> = actionActor(coroutineScope)

    override suspend fun updateToken(token: String?) {
        channel.send(Action.TokenSet(token))
    }

    override suspend fun getToken(): String? {
        val deferred = CompletableDeferred<String?>()
        channel.send(Action.TokenGet(deferred))
        return deferred.await()
    }

    private sealed interface Action{
        data class TokenGet(val deferred: CompletableDeferred<String?>): Action
        data class TokenSet(val body: String?): Action
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun actionActor(coroutineScope: CoroutineScope) = coroutineScope.actor<Action>{
        var token: String? = null

        for (action in channel) {
            when(action) {
                is Action.TokenGet -> {
                    action.deferred.complete(token)
                }
                is Action.TokenSet -> {
                    token = action.body
                }
            }
        }
    }
}