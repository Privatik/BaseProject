package io.my.auth.data.di

import dagger.Binds
import dagger.Module
import io.my.auth.data.remote.LoginAndCheckValidApi
import io.my.auth.data.remote.LoginAndCheckValidApiImpl

@Module
internal interface ApiModule {

    @Binds
    fun bindLoginApi(api: LoginAndCheckValidApiImpl): LoginAndCheckValidApi
}