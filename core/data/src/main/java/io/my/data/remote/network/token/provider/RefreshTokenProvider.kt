package io.my.data.remote.network.token.provider

import androidx.datastore.preferences.core.Preferences
import io.my.data.local.DataStoreManager
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.flow.first

internal class RefreshTokenProvider(
    coroutineScope: CoroutineScope,
    private val dataStoreManager: DataStoreManager,
    private val keyAlias: String,
    private val keyDataStore: Preferences.Key<String>
): TokenProvider {
    private var channel: SendChannel<Action> = actionActor(coroutineScope)

    override suspend fun updateToken(token: String?) {
        channel.send(Action.TokenSet(token))
    }

    override suspend fun getToken(): String?  {
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
        var isUpdate = false

        for (token in channel) {
            when(token) {
                is Action.TokenGet -> {
                    if (isUpdate) {
                        token.deferred.complete(null)
                        return@actor
                    }
                    val refreshToken = dataStoreManager
                        .secureData(
                            keyAlias = keyAlias,
                            fetchValue = { preference ->
                                preference[keyDataStore].orEmpty()
                            },
                            mapper = { it }
                        )
                        .first()

                    if (refreshToken.isNotBlank()){
                        token.deferred.complete(refreshToken)
                    }
                    isUpdate = true
                }
                is Action.TokenSet -> {
                    dataStoreManager.securityEdit(keyAlias, token.body.orEmpty()) { preference, encryptedValue ->
                        preference[keyDataStore] = encryptedValue
                    }
                    isUpdate = false
                }
            }
        }
    }
}