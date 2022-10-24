package io.my.baseproject

import android.app.Application
import android.content.Context
import com.example.routing.Path
import com.example.routing.Screen
import io.my.baseproject.di.AppComponent
import io.my.baseproject.di.DaggerAppComponent
import kotlinx.coroutines.GlobalScope

class MainApplication: Application() {

    private var _component: AppComponent? = null

    override fun onCreate() {
        super.onCreate()

        _component = DaggerAppComponent
            .builder()
            .build()
    }

    fun getScreens(): Map<Path, Screen.Factory> = _component!!.screens()

}

fun Context.getScreens(): Map<Path, Screen.Factory>{
    return if (this is MainApplication){
        getScreens()
    } else {
        (applicationContext as MainApplication).getScreens()
    }
}

