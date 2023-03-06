package io.my.baseproject

import android.os.Bundle
import androidx.core.view.WindowCompat
import com.bumble.appyx.core.integrationpoint.NodeActivity
import com.example.routing.Path
import com.example.routing.ScreenInfo
import com.example.routing.setContentPerJetpack

@Suppress("UNCHECKED_CAST")
class MainActivity: NodeActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureEdgeToEdge()

        setContentPerJetpack(
            startPath = Path.FirstScreen,
            builder = {
                screens(getScreens().map { it as ScreenInfo<Path, Any> }.toSet())
            },
            coreDataDependencies = getCoreDataDependencies()
        )
    }

    private fun configureEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}