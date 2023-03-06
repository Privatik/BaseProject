package io.my.ui.presenter

import androidx.compose.runtime.Composable
import com.io.navigation.AndroidPresenter
import com.io.navigation.presenter
import com.io.navigation_common.PresenterFactory
import com.io.navigation_common.UIPresenter
import com.io.navigation_common.emptyPresenter

abstract class MyPresenter: AndroidPresenter()

abstract class MyPresenterFactory: PresenterFactory {

    override fun <P : UIPresenter> create(model: Class<out UIPresenter>): P {
        @Suppress("UNCHECKED_CAST")
        return create(model as Class<out MyPresenter>)
    }

    abstract fun <P : MyPresenter> create(model: Class<out MyPresenter>): P
}

@Composable
public inline fun <reified P: UIPresenter> myPresenter(
    factory: MyPresenterFactory? = null,
): P {
    return presenter(factory ?: emptyPresenter(), P::class.java)
}

@Composable
public inline fun <reified P: UIPresenter> mySharedPresenter(
    factory: MyPresenterFactory? = null
): P {
    return presenter(factory ?: emptyPresenter(), P::class.java, true)
}