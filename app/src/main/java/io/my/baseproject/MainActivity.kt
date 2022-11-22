package io.my.baseproject

import android.os.Bundle
import androidx.core.view.WindowCompat
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.example.routing.route.Path
import com.example.routing.setContentPerJetpack
import com.io.navigation.PresenterComponentActivity

class MainActivity: NodeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureEdgeToEdge()

        setContentPerJetpack(
            startPath = Path.FIRST_SCREEN,
            builder = {
                screens(getScreens())
            },
            globalDependencies = getGlobalDependencies()
        )
    }

    private fun configureEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}