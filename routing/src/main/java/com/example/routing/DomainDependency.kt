package com.example.routing

import io.my.auth.data.di.DaggerAuthDataComponent
import io.my.auth.domain.di.DaggerAuthDomainComponent
import io.my.core.DomainDependencies
import io.my.core.GlobalDependencies
import io.my.data.di.CoreDataDependencies

class DomainDependency(
    private val globalDependencies: GlobalDependencies
) {

    fun getByPath(path: Path): DomainDependencies{
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