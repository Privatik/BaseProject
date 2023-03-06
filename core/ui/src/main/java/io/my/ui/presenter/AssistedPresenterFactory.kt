package io.my.ui.presenter

class AssistedPresenterFactory (
    private val presenter: () -> MyPresenter
): MyPresenterFactory() {

    override fun <P : MyPresenter> create(model: Class<out MyPresenter>): P {
        @Suppress("UNCHECKED_CAST")
        return presenter() as P
    }
}