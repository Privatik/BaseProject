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

    private var _component: AppComponent? = null
    private var _coreDataComponent: CoreDataDependencies? = null

    override fun onCreate() {
        super.onCreate()

        _component = DaggerAppComponent
            .builder()
            .build()
    }

    fun getScreens(): Set<ScreenInfo<out Path, *>> = _component!!.screens()
    fun getCoreDataDependencies(): CoreDataDependencies {
        if (_coreDataComponent == null){
            _coreDataComponent = DaggerCoreDataComponent
                .builder()
                .applicationContext(this)
                .backgroundCoroutineScope(CoroutineScope(Dispatchers.IO + SupervisorJob()))
                .build()
        }
        return _coreDataComponent!!
    }

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

