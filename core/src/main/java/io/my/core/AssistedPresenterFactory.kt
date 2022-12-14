package io.my.core

import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.UIPresenter

class AssistedPresenterFactory (
    private val presenter: () -> UIPresenter
): PresenterFactory {

    override fun <P : UIPresenter> create(model: Class<out UIPresenter>): P {
        @Suppress("UNCHECKED_CAST")
        return presenter() as P
    }
}