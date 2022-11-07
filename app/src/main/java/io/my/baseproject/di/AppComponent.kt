package io.my.baseproject.di

import com.example.auth.ui.AuthScreenModule
import com.example.routing.Path
import com.example.routing.Screen
import com.example.routing.ScreenInfo
import dagger.Component

@Component(
    modules = [
        AuthScreenModule::class
    ]
)
interface AppComponent {

    fun screens(): Set<ScreenInfo>
}