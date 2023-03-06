package com.example.auth.ui.di

import dagger.Binds
import dagger.Module
import io.my.ui.presenter.DefaultPresenterFactory
import io.my.ui.presenter.MyPresenterFactory
import javax.inject.Singleton

@Module
internal interface PresenterFactoryModule {

    @Binds
    @Singleton
    fun bindPresenter(factory: DefaultPresenterFactory): MyPresenterFactory
}