package com.example.auth.ui.di

import com.example.auth.ui.auth.AuthPresenter
import com.example.auth.ui.auth.AuthScreen
import com.example.auth.ui.profile.ProfilePresenter
import com.example.auth.ui.profile.ProfileScreen
import com.example.routing.Path
import com.example.routing.Screen
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.my.core.Presenter
import io.my.core.PresenterKey

@Module
internal interface PresenterModule {

    @Binds
    @[IntoMap PresenterKey(AuthPresenter::class)]
    fun bindAuthPresenter(presenter: AuthPresenter): Presenter<*,*,*>

    @Binds
    @[IntoMap PresenterKey(ProfilePresenter::class)]
    fun bindProfilePresenter(presenter: ProfilePresenter): Presenter<*,*,*>

}