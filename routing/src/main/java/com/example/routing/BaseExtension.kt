package com.example.routing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier

internal abstract class BaseExtension<T> {

    @Composable
    open fun provider(): ProvidedValue<T>? = null

    @Composable
    open fun Content(modifier: Modifier) { }

}
