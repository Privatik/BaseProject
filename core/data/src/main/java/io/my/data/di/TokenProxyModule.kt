package io.my.data.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import io.my.data.remote.TokenManagerProxy
import io.my.data.remote.TokenManagerProxyImpl
import io.my.data.remote.network.JWTToken
import io.my.data.remote.token.manager.PublicTokenManager

@Module()
internal interface TokenProxyModule {

    @Binds
    fun provideTokenProxy(manager: TokenManagerProxyImpl): TokenManagerProxy

}