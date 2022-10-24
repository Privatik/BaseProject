package io.my.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.ktor.client.*
import io.my.data.local.DataStoreManager
import io.my.data.remote.TokenManagerProxy
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

interface CoreDataDependencies{
    fun tokenProxy(): TokenManagerProxy

    fun dataStoreManager(): DataStoreManager

    fun client(): HttpClient
}

@Component(
    modules = [
        SerializeModule::class,
        RemoteModule::class,
        LocalModule::class,
        TokenModule::class
    ]
)
@Singleton
interface CoreDataComponent: CoreDataDependencies {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun route(context: Context): Builder

        @BindsInstance
        fun globalCoroutineScope(scope: CoroutineScope): Builder

        fun build(): CoreDataComponent
    }
}