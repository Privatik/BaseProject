package com.example.routing.di

import com.example.routing.Argument
import com.example.routing.GoogleArgumentImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal interface ArgumentModule {

    @Binds
    @Singleton
    fun provideArgument(argument: GoogleArgumentImpl): Argument<String>
}