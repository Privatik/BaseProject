package io.my.data.remote.token.manager

import io.my.data.remote.network.JWTToken
import io.my.data.remote.token.provider.TokenProvider
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor

internal class MyJWTTokenManager(
    coroutineScope: CoroutineScope,
    private val accessTokenProvider: TokenProvider,
    private val refreshTokenProvider: TokenProvider,
    private val doRequestOnNewTokens: (refreshToken: String?) -> Pair<String, String>
): JWTToken.TokenManager {
    private val channel: SendChannel<Action> = actionActor(coroutineScope)

    override suspend fun getNewIfNeedToken(
        oldToken: String?
    ): String? {
        val deferred = CompletableDeferred<String?>()
        val action = Action.UpdateToken(
            oldToken,
            deferred
        )
        channel.send(action)
        return deferred.await()
    }

    override suspend fun updateTokens(accessToken: String?, refreshToken: String?) {
        accessTokenProvider.updateToken(accessToken)
        refreshTokenProvider.updateToken(refreshToken)
    }

    override suspend fun getAccessToken(): String? = accessTokenProvider.getToken()
    override suspend fun getRefreshToken(): String? = refreshTokenProvider.getToken()

    private sealed interface Action{
        data class UpdateToken(
            val oldToken: String?,
            val deferred: CompletableDeferred<String?>
        ): Action
    }

    @OptIn(ObsoleteCoroutinesApi::class)
    private fun actionActor(coroutineScope: CoroutineScope) = coroutineScope.actor<Action>{
        for (action in channel) {
            when(action) {
                is Action.UpdateToken -> {
                    if ("Bearer ${accessTokenProvider.getToken()}" == action.oldToken){
                        accessTokenProvider.updateToken(null)

                        val (accessToken, refreshToken) = doRequestOnNewTokens(
                            refreshTokenProvider.getToken()
                        )

                        updateTokens(accessToken, refreshToken)

                        action.deferred.complete(accessToken)
                    } else {
                        action.deferred.complete(accessTokenProvider.getToken())
                    }
                }
            }
        }
    }
}