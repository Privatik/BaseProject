package io.my.data.di

import dagger.Module
import dagger.Provides
import io.my.data.local.DataStoreManager
import io.my.data.local.GlobalKeysForDataStore
import io.my.data.remote.RefreshApi
import io.my.data.remote.TokenManagerProxy
import io.my.data.remote.TokenManagerProxyImpl
import io.my.data.remote.network.JWTToken
import io.my.data.remote.token.manager.MyJWTTokenManager
import io.my.data.remote.token.provider.AccessTokenProvider
import io.my.data.remote.token.provider.RefreshTokenProvider
import io.my.data.remote.token.provider.TokenProvider
import kotlinx.coroutines.CoroutineScope
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class RefreshToken

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AccessToken


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
        ) {
            refreshApi.refresh()
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
            keyAlias = "",
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

    @Provides
    fun provideTokenProxy(
        manager: JWTToken.TokenManager
    ): TokenManagerProxy{
        return TokenManagerProxyImpl(manager)
    }
}