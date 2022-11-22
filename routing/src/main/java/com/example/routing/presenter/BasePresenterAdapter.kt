package com.example.routing.presenter

import com.bumble.appyx.core.navigation.NavKey
import com.bumble.appyx.navmodel.backstack.BackStack
import com.bumble.appyx.navmodel.backstack.active
import com.example.routing.Path
import com.io.navigation_common.PresenterStoreOwner

class BasePresenterAdapter(
    private val backStack: BackStack<Path>
): PresenterStoreOwner.Adapter<NavKey<Path>>(){

    override fun getGuide(): NavKey<Path> = backStack.active!!.key

    override fun getCacheKey(currentGuide: NavKey<Path>): String = currentGuide.id


    override fun isOptionallyVerifyValidGuide(guide: NavKey<Path>?): Boolean {
        if (guide == null) return false
        val element = backStack.elements.value.lastOrNull { it.key == guide } ?: return false
        return element.targetState != BackStack.State.DESTROYED
    }

}