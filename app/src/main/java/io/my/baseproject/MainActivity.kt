package io.my.baseproject

import android.os.Bundle
import androidx.core.view.WindowCompat
import com.example.auth.ui.auth.AuthScreenInfo
import com.example.auth.ui.profile.ProfileScreenInfo
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
            Path.FIRST_SCREEN to AuthScreenInfo,
            Path.SECOND_SCREEN to ProfileScreenInfo
        )

        setContentPerJetpack { controller ->
            ProjectTheme {
                Routing(
                    controller = controller,
                    startScreenRoute = AuthScreenInfo.route,
                    screens = screens
                )
            }
        }
    }

    private fun configureEdgeToEdge() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}