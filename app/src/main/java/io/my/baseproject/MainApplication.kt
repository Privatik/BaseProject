package io.my.baseproject

import android.app.Application
import android.content.Context
import com.example.routing.Path
import com.example.routing.ScreenInfo
import io.my.baseproject.di.AppComponent
import io.my.baseproject.di.DaggerAppComponent
import io.my.data.di.CoreDataDependencies
import io.my.data.di.DaggerCoreDataComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

class MainApplication: Application() {

    private val _component: AppComponent by lazy(LazyThreadSafetyMode.NONE) {
        DaggerAppComponent
            .builder()
            .build()
    }
    private val _coreDataComponent: CoreDataDependencies by lazy(LazyThreadSafetyMode.NONE) {
        DaggerCoreDataComponent
            .builder()
            .applicationContext(this)
            .build()
    }

    fun getScreens(): Set<ScreenInfo<out Path, *>> = _component.screens()
    fun getCoreDataDependencies(): CoreDataDependencies = _coreDataComponent

}

fun Context.getScreens(): Set<ScreenInfo<out Path , *>> {
    return if (this is MainApplication){
        getScreens()
    } else {
        applicationContext.getScreens()
    }
}

fun Context.getCoreDataDependencies(): CoreDataDependencies {
    return if (this is MainApplication){
        getCoreDataDependencies()
    } else {
        applicationContext.getCoreDataDependencies()
    }
}


