package com.example.auth.ui.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bumble.appyx.core.modality.BuildContext
import com.example.auth.ui.AuthPresenterScope
import com.example.routing.Path
import com.example.routing.Screen
import com.example.routing.ScreenInfo
import com.example.routing.route.RouteAction
import com.io.navigation.presenter
import com.io.navigation.sharedPresenter
import com.io.navigation_common.UIPresenter
import io.my.auth.domain.di.AuthDomainDependencies
import io.my.core.DomainDependencies
import io.my.ui.ProjectTheme
import kotlinx.coroutines.flow.launchIn
import javax.inject.Inject
import kotlin.reflect.KClass

class ProfileScreen private constructor(
    buildContext: BuildContext,
    private val email: String,
): Screen(buildContext) {

    @Composable
    override fun Content(modifier: Modifier) {
        val scope: AuthPresenterScope = sharedPresenter()
        val presenter: ProfilePresenter = presenter(scope.factory)
        LaunchedEffect(Unit){
            presenter.state.launchIn(this)
        }
        Content(intent = presenter.intent)
    }

    @Composable
    private fun Content(
        intent: ProfileIntent
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(ProjectTheme.colors.backgroundPrimary),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(email)
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = { intent.checkValid() }
            ){
                Text("Check valid", color = ProjectTheme.colors.backgroundPrimary)
            }
        }
    }

    class ProfileFactory(
        private val email: String
    ): Factory{

        override fun create(buildContext: BuildContext): Screen = ProfileScreen(
            buildContext = buildContext,
            email = email
        )
    }
}

class ProfileScreenInfo @Inject constructor(): ScreenInfo<Path.SecondScreen>{
    override val path: KClass<Path.SecondScreen> = Path.SecondScreen::class

    override val screenFactory: (Path.SecondScreen) -> Screen.Factory = {
        ProfileScreen.ProfileFactory(it.email)
    }

    override val scope: (
        RouteAction, DomainDependencies
    ) -> UIPresenter = { routingAction, domainDependencies ->
        AuthPresenterScope(routingAction, domainDependencies as AuthDomainDependencies)
    }
}
