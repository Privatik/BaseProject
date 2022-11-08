package com.example.routing.managers

import com.example.routing.route.Path
import io.my.auth.data.di.DaggerAuthDataComponent
import io.my.auth.domain.di.DaggerAuthDomainComponent
import io.my.core.DomainDependencies
import io.my.core.GlobalDependencies
import io.my.data.di.CoreDataDependencies

internal interface DomainDependencyManager{
    fun getByPath(path: Path): DomainDependencies
}

internal class DomainDependencyManagerImpl(
    private val globalDependencies: GlobalDependencies
): DomainDependencyManager {

     override fun getByPath(path: Path): DomainDependencies{
        return when(path){
            Path.FIRST_SCREEN,
            Path.SECOND_SCREEN -> {
                DaggerAuthDomainComponent
                    .builder()
                    .dto(
                        DaggerAuthDataComponent
                            .builder()
                            .core(globalDependencies as CoreDataDependencies)
                            .build()
                    )
                    .build()
            }
        }
    }
}