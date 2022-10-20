package io.my.baseproject

import android.app.Application
import com.example.routing.di.DaggerRoutingComponent
import com.example.routing.route.RouteManager
import io.my.baseproject.di.AppComponent
import io.my.baseproject.di.DaggerAppComponent

class MainApplication: Application() {

    private var _component: AppComponent? = null

    fun createComponent(routeManager: RouteManager){
        _component = DaggerAppComponent.builder()
            .routeDependencies(
                DaggerRoutingComponent.builder()
                    .instanceRoute(routeManager)
                    .build()
            )
            .build()
    }

}