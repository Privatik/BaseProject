package io.my.baseproject

import android.app.Application
import android.content.Context
import com.example.routing.Path
import com.example.routing.Screen
import com.example.routing.ScreenInfo
import io.my.baseproject.di.AppComponent
import io.my.baseproject.di.DaggerAppComponent
import io.my.core.GlobalDependencies
import io.my.data.di.CoreDataDependencies
import io.my.data.di.DaggerCoreDataComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
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

    fun getScreens(): Set<ScreenInfo> = _component!!.screens()
    fun getGlobalDependencies(): GlobalDependencies {
        if (_coreDataComponent == null){
            _coreDataComponent = DaggerCoreDataComponent
                .builder()
                .context(this)
                .globalCoroutineScope(CoroutineScope(Dispatchers.IO + SupervisorJob()))
                .build()
        }
        return _coreDataComponent!!
    }

}

fun Context.getScreens(): Set<ScreenInfo>{
    return if (this is MainApplication){
        getScreens()
    } else {
        (applicationContext as MainApplication).getScreens()
    }
}

fun Context.getGlobalDependencies(): GlobalDependencies{
    return if (this is MainApplication){
        getGlobalDependencies()
    } else {
        (applicationContext as MainApplication).getGlobalDependencies()
    }
}

