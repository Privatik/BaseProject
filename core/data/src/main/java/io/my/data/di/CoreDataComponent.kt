package io.my.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.ktor.client.*
import io.my.core.GlobalDependencies
import io.my.data.local.DataStoreManager
import io.my.data.remote.token.TokenManagerProxy
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

interface CoreDataDependencies: GlobalDependencies {
    fun tokenProxy(): TokenManagerProxy

    fun dataStoreManager(): DataStoreManager

    fun client(): HttpClient
}

@Component(
    modules = [
        SerializeModule::class,
        RemoteModule::class,
        LocalModule::class,
        TokenModule::class,
        TokenProxyModule::class
    ]
)
@Singleton
interface CoreDataComponent: CoreDataDependencies {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        @BindsInstance
        fun globalCoroutineScope(scope: CoroutineScope): Builder

        fun build(): CoreDataComponent
    }
}