package io.my.baseproject

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.example.routing.Path
import com.example.routing.ScreenInfo
import com.example.routing.setNavigationContent

@Suppress("UNCHECKED_CAST")
class MainActivity: NodeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        configureEdgeToEdge()

        setNavigationContent(
            builder = {
                screens(getScreens().map { it as ScreenInfo<Path, Any> }.toSet())
            },
            coreDataDependencies = getCoreDataDependencies(),
            initialController = InitialControllerImpl(splashScreen)
        )
    }

    private fun configureEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}