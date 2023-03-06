package io.my.data.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import io.ktor.client.*
import io.my.data.local.DataStoreManager
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

interface CoreDataDependenciesConsumer {
    fun useProvider(provider: CoreDataDependencies){

    }
}

interface CoreDataDependencies {

    fun dataStoreManager(): DataStoreManager

    fun client(): HttpClient

}

@Component(
    modules = [
        SerializeModule::class,
        RemoteModule::class,
        CacheModule::class,
    ]
)
@Singleton
interface CoreDataComponent: CoreDataDependencies {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun applicationContext(context: Context): Builder

        @BindsInstance
        fun backgroundCoroutineScope(scope: CoroutineScope): Builder

        fun build(): CoreDataComponent
    }
}