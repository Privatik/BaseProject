package io.my.data.di

import dagger.Module
import dagger.Provides
import io.my.data.BuildConfig
import io.my.data.local.DataStoreManager
import io.my.data.local.GlobalKeysForDataStore
import io.my.data.remote.RefreshApi
import io.my.data.remote.token.JWTToken
import io.my.data.remote.token.manager.MyJWTTokenManager
import io.my.data.remote.token.provider.AccessTokenProvider
import io.my.data.remote.token.provider.RefreshTokenProvider
import io.my.data.remote.token.provider.TokenProvider
import kotlinx.coroutines.CoroutineScope
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class RefreshToken

@Retention(AnnotationRetention.BINARY)
@Qualifier
internal annotation class AccessToken


@Module()
internal class TokenModule {

    @Provides
    @Singleton
    fun provideManager(
        coroutineScope: CoroutineScope,
        @RefreshToken refreshTokenProvider: TokenProvider,
        @AccessToken accessTokenProvider: TokenProvider,
        refreshApi: RefreshApi
    ): JWTToken.TokenManager{
        return MyJWTTokenManager(
            coroutineScope = coroutineScope,
            accessTokenProvider = accessTokenProvider,
            refreshTokenProvider = refreshTokenProvider
        ) { client ->
            refreshApi.refresh(client)
                .map { it.accessToken to it.refreshToken }
                .getOrElse { Pair(null, null) }
        }
    }

    @RefreshToken
    @Provides
    @Singleton
    fun provideRefreshTokenProvider(
        coroutineScope: CoroutineScope,
        dataStoreManager: DataStoreManager
    ): TokenProvider{
        return RefreshTokenProvider(
            coroutineScope = coroutineScope,
            dataStoreManager = dataStoreManager,
            keyAlias = BuildConfig.KeyForRefreshToken,
            keyDataStore = GlobalKeysForDataStore.refreshToken
        )
    }

    @AccessToken
    @Provides
    @Singleton
    fun provideAccessTokenProvider(
        coroutineScope: CoroutineScope
    ): TokenProvider{
        return AccessTokenProvider(
            coroutineScope = coroutineScope
        )
    }
}