package com.example.routing.di

import com.example.routing.managers.ArgumentsManager
import com.example.routing.managers.GoogleArgumentsManagerImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
internal interface ArgumentModule {

    @Binds
    @Singleton
    fun provideArgument(argument: GoogleArgumentsManagerImpl): ArgumentsManager<String>
}