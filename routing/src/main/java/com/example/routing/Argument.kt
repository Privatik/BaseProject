package com.example.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.core.os.bundleOf
import java.util.concurrent.ConcurrentHashMap
import javax.inject.Inject

internal interface Argument<T> {
    fun set(key: T, arg: Any)
    fun get(key: T): Any?
}

internal class GoogleArgumentImpl @Inject constructor(): Argument<String> {
    private val map = ConcurrentHashMap<String, Any>()

    override fun set(key: String, arg: Any) {
        if (arg != Unit){
            map[key] = arg
        }
    }

    override fun get(key: String): Any? {
        return map.remove(key)
    }

}

private fun argumentSaver(key: String): Saver<Any, *> =
    Saver(
        save = { if (it != Unit) bundleOf(key to it) else null },
        restore = { bundle ->
            @Suppress("DEPRECATION")
            bundle.get(key) ?: Unit
        }
    )

@Composable
internal fun rememberArgument(key: String, value: Any): Any =
    rememberSaveable(saver = argumentSaver(key)) { value }
