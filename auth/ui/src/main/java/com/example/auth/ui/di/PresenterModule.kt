package com.example.auth.ui.di

import com.example.auth.ui.auth.AuthPresenter
import com.example.auth.ui.profile.ProfilePresenter
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