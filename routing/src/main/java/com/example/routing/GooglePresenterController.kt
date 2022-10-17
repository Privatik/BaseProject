package com.example.routing

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.io.navigation.AndroidPresenterStoreOwner
import com.io.navigation_common.PresenterController
import com.io.navigation_common.PresenterStoreOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GooglePresenterController(
    owner: AndroidPresenterStoreOwner,
    controller: NavHostController
): PresenterController<String>(owner) {

    override val screenFlow: Flow<String> = controller.currentBackStackEntryFlow.map { it.id }
}