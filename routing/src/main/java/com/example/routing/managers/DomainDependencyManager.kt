package com.example.routing.managers

import com.example.routing.Path
import io.my.auth.data.di.DaggerAuthDataComponent
import io.my.auth.domain.di.DaggerAuthDomainComponent
import io.my.core.DomainDependencies
import io.my.data.di.CoreDataDependencies

internal interface DomainDependencyManager{
    fun getByPath(path: Path): DomainDependencies
}

internal class DomainDependencyManagerImpl(
    private val coreDependencies: CoreDataDependencies
): DomainDependencyManager {

     override fun getByPath(path: Path): DomainDependencies{
        return when(path){
            Path.FirstScreen,
            is Path.SecondScreen -> {
                DaggerAuthDomainComponent
                    .builder()
                    .dto(
                        DaggerAuthDataComponent
                            .builder()
                            .core(coreDependencies)
                            .build()
                    )
                    .build()
            }
        }
    }
}