package com.example.auth.ui.auth

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import com.example.auth.ui.AuthTestTags
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class AuthComposeScreen(semanticProvider: SemanticsNodeInteractionsProvider): ComposeScreen<AuthComposeScreen>(
    semanticsProvider = semanticProvider,
    viewBuilderAction = { hasTestTag(AuthTestTags.authContent) }
) {

    val loginField: KNode = child {
        hasTestTag(AuthTestTags.loginField)
    }

    val passwordField: KNode = child {
        hasTestTag(AuthTestTags.passwordField)
    }
}