package com.example.auth.ui.di

import com.example.auth.ui.auth.AuthPresenter
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import io.my.ui.presenter.Presenter
import io.my.ui.presenter.PresenterKey

@Module
internal interface PresenterModule {

    @Binds
    @[IntoMap PresenterKey(AuthPresenter::class)]
    fun bindAuthPresenter(presenter: AuthPresenter): Presenter<*, *, *>

}