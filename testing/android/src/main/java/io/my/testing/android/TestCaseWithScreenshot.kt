package io.my.testing.android

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.rule.GrantPermissionRule
import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.state.SavedStateMap
import com.kaspersky.components.composesupport.config.withComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.DocLocScreenshotTestCase
import io.my.ui.theme.ProjectTheme
import org.junit.Rule

private const val LOCALES = "ru"

abstract class AndroidTestCaseWithSupportScreenshot(
    kaspressoBuilder: Kaspresso.Builder = Kaspresso.Builder.withComposeSupport()
): DocLocScreenshotTestCase(
    locales = LOCALES,
    kaspressoBuilder = kaspressoBuilder
) {

    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val runtimePermissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    protected fun setContent(
        savedStateMap: SavedStateMap? = null,
        content: @Composable (
            buildContext: BuildContext
        ) -> Unit
    ){
        composeTestRule.setContent {
            val buildContext = remember { BuildContext.root(savedStateMap = savedStateMap) }
            ProjectTheme {
                content(buildContext)
            }
        }
    }
}