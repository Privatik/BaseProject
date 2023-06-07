package io.my.baseproject

import androidx.core.splashscreen.SplashScreen
import com.example.routing.initialize_helper.InitialController

class InitialControllerImpl(
    private val splashScreen: SplashScreen,
): InitialController {

    @Volatile
    private var isCanContinue = false

    override fun stopInitialize() {
        isCanContinue = false
        splashScreen.setKeepOnScreenCondition { !isCanContinue }
    }

    override fun continueInitialize() {
        isCanContinue = true
    }

}