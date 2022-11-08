package io.my.data.di

import dagger.Binds
import dagger.Module
import io.my.data.remote.token.TokenManagerProxy
import io.my.data.remote.token.TokenManagerProxyImpl

@Module()
internal interface TokenProxyModule {

    @Binds
    fun provideTokenProxy(manager: TokenManagerProxyImpl): TokenManagerProxy

}