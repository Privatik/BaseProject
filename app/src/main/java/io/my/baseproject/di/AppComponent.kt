package io.my.baseproject.di

import com.example.routing.Path
import com.example.routing.Screen
import dagger.Component

@Component(
    modules = [
        AuthScreenModule::class
    ]
)
interface AppComponent {

    fun screens(): Map<Path, Screen.Factory>
}