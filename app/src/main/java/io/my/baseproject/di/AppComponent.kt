package io.my.baseproject.di

import com.example.routing.di.RouteDependencies
import dagger.Component

@Component(
    dependencies = [RouteDependencies::class]
)
interface AppComponent {
}