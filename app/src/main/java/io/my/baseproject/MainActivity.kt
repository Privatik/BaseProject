package io.my.baseproject

import android.os.Bundle
import androidx.core.view.WindowCompat
import com.example.auth.ui.first_step.FirstStepScreenInfo
import com.example.auth.ui.second_step.SecondStepScreenInfo
import com.example.routing.Path
import com.example.routing.Routing
import com.example.routing.ScreenInfo
import com.example.routing.setContentPerJetpack
import com.io.navigation.PresenterComponentActivity
import io.my.ui.ProjectTheme

class MainActivity : PresenterComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureEdgeToEdge()

        val screens = mapOf<Path, ScreenInfo>(
            Path.FIRST_SCREEN to FirstStepScreenInfo,
            Path.SECOND_SCREEN to SecondStepScreenInfo
        )

        setContentPerJetpack { controller ->
            ProjectTheme {
                Routing(
                    controller = controller,
                    startScreenRoute = FirstStepScreenInfo.route,
                    screens = screens
                )
            }
        }
    }

    private fun configureEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}