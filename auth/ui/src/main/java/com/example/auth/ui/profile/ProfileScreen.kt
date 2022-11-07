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
import com.example.auth.ui.AuthPresenterScope
import com.example.routing.Path
import com.example.routing.RoutingAction
import com.example.routing.Screen
import com.example.routing.ScreenInfo
import com.example.routing.route.Route
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
    private val email: String,
): Screen() {

    @Composable
    override fun Content() {
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

    class ProfileFactory @Inject constructor(): Factory{

        override fun <A : Any> create(arg: A): Screen {
            return ProfileScreen(
                email = arg as String,
            )
        }
    }
}

class ProfileScreenInfo @Inject constructor(): ScreenInfo{
    override val path: Path = Path.SECOND_SCREEN
    override val routeForNavigation: String = "profile"
    override val scopeKClazz: KClass<out UIPresenter> = ProfilePresenter::class

    override val screenFactory: () -> Screen.Factory = {
        ProfileScreen.ProfileFactory()
    }
    override val scopeInPresenter: (
        routeingAction: RoutingAction,
        domainDependencies: DomainDependencies
    ) -> UIPresenter = { routingAction, domainDependencies ->
        AuthPresenterScope(routingAction, domainDependencies as AuthDomainDependencies)
    }
}
