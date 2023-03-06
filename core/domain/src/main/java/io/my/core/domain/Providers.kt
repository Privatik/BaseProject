package io.my.core.domain

interface DataProvider<T: Any> {
    fun get(): T
}

interface DomainProvider<T: Any> {
    fun get(): T
}