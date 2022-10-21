package io.my.baseproject.di

import com.example.routing.Path
import dagger.MapKey

@MapKey
internal annotation class PathKey(val value: Path)