package io.my.baseproject.di

import com.example.auth.ui.auth.AuthScreen
import com.example.auth.ui.profile.ProfileScreen
import com.example.routing.Path
import com.example.routing.Screen
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface AuthScreenModule {

    @Binds
    @[IntoMap PathKey(Path.FIRST_SCREEN)]
    fun bindFirstScreen(factory: AuthScreen.AuthScreenFactory): Screen.Factory

    @Binds
    @[IntoMap PathKey(Path.SECOND_SCREEN)]
    fun bindProductDescribeWorker(factory: ProfileScreen.ProfileFactory):Screen.Factory

}