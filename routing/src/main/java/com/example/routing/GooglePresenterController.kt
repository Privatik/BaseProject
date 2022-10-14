package com.example.routing

import androidx.navigation.NavController
import com.io.navigation.AndroidPresenterStoreOwner
import com.io.navigation_common.PresenterController
import com.io.navigation_common.PresenterStoreOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GooglePresenterController(
    owner: AndroidPresenterStoreOwner,
    controller: NavController
): PresenterController<String>(owner) {

    override val screenFlow: Flow<String> = controller.currentBackStackEntryFlow.map { it.id }
}