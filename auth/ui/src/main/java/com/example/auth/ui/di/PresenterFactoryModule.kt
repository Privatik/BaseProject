package com.example.auth.ui.di

import com.io.navigation_common.PresenterFactory
import dagger.Binds
import dagger.Module
import io.my.core.DefaultPresenterFactory
import javax.inject.Singleton

@Module
internal interface PresenterFactoryModule {

    @Binds
    @Singleton
    fun bindPresenter(factory: DefaultPresenterFactory): PresenterFactory
}