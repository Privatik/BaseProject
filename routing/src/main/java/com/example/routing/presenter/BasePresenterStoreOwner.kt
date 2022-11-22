package com.example.routing.presenter

import com.bumble.appyx.core.navigation.NavKey
import com.example.routing.Path
import com.io.navigation.AndroidPresenterStoreOwner

class BasePresenterStoreOwner(
    adapter: BasePresenterAdapter
): AndroidPresenterStoreOwner<NavKey<Path>>(adapter)