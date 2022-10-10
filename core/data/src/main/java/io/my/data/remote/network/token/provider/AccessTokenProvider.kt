package io.my.data.remote.network.token.provider

import android.util.Log
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor

class AccessTokenProvider(
    private val coroutineScope: CoroutineScope
): TokenProvider{

    private val channel: SendChannel<Action> = actionActor()

    override suspend fun updateToken(token: String?) {
        channel.send(Action.TokenSet(token))
    }

    override suspend fun getToken(): String?  {
        val deferred = CompletableDeferred<String?>()
        channel.send(Action.TokenGet(deferred))
        return deferred.await()
    }

    private sealed class Action(){
        data class TokenGet(val deferred: CompletableDeferred<String?>): Action()
        data class TokenSet(val body: String?): Action()
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun actionActor() = coroutineScope.actor<Action>{
        var token: String? = null

        for (action in channel) {
            when(action) {
                is Action.TokenGet -> {
                    Log.d("Token","access Get")
                    action.deferred.complete(token)
                }
                is Action.TokenSet -> {
                    Log.d("Token","access Set")
                    token = action.body
                }
            }
        }
    }
}