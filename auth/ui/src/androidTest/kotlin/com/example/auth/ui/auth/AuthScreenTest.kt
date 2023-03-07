@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.auth.ui.auth

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.my.testing.android.AndroidTestCaseWithSupportScreenshot
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class AuthScreenTest: AndroidTestCaseWithSupportScreenshot() {

    lateinit var authScreen: AuthScreen

    @Test
    fun addItemToList() = before {
    }.after {

    }.run {
        val state = MutableStateFlow(
            AuthState(
                login = "test",
                password = "test".toList()
            )
        )
        val intent = AuthIntent(CoroutineScope(UnconfinedTestDispatcher()))

        setContent { buildContext ->
            authScreen = remember { AuthScreen.AuthScreenFactory().create(buildContext) as AuthScreen }
            val wrap = state.collectAsState()
            authScreen.Content(
                wrap = wrap,
                intent = intent
            )
        }

        step("do screenshot and check displayed"){
            ComposeScreen.onComposeScreen<AuthComposeScreen>(composeTestRule) {
                loginField {
                    assertIsDisplayed()
                }
                passwordField {
                    assertIsDisplayed()
                }

                captureScreenshot("auth_screen_1")
            }
        }
    }
}