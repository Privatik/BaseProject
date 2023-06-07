package com.example.routing.initialize_helper

import com.example.routing.Path
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal class InitialPathHelper(
    private val scope: CoroutineScope
) {


    fun getInitialPath(): Path = Path.FirstScreen

    fun calculateInitialPath(actionAfterCalculate: () -> Unit){
        scope.launch { actionAfterCalculate() }
    }

}