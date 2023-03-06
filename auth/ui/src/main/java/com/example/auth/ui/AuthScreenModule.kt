package com.example.auth.ui

import com.example.auth.ui.auth.AuthScreenInfo
import com.example.routing.Path
import com.example.routing.ScreenInfo
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoSet

@Module
interface AuthScreenModule {

    @Binds
    @IntoSet
    fun bindAuth(info: AuthScreenInfo): ScreenInfo<out Path, out Any>

}