package com.example.routing.di

import com.example.routing.route.RouteActionHandler
import io.my.core.domain.DomainProvider
import javax.inject.Provider

internal interface RouteDependencies {

    val domainDependenciesProvider: Map<Class<out DomainProvider<*>>, Provider<DomainProvider<*>>>

    fun action(): RouteActionHandler
}