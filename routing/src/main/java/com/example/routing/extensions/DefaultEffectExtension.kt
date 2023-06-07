package com.example.routing.extensions

import android.content.Context
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.routing.BaseExtension
import com.example.routing.DefaultEffectHandler
import com.example.routing.route.RouteActionHandler
import io.my.ui.effect.EffectHandler
import io.my.ui.effect.LocalEffectHandler
import io.my.ui.theme.AppColors
import io.my.ui.theme.ProjectTheme
import io.my.ui.util.StringUI
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.*
import java.util.concurrent.ConcurrentHashMap

internal class DefaultEffectExtension(
    private val routeActionHandler: RouteActionHandler,
    private val snackbarHostState: SnackbarHostState
): BaseExtension<EffectHandler>() {

    private val snackBarBodyMap = ConcurrentHashMap<String, SnackBarExtensionBody>()

    @Composable
    override fun provider(): ProvidedValue<EffectHandler> {
        val effectHandler = remember { DefaultEffectHandler() }
        val context = LocalContext.current
        val colors = ProjectTheme.colors

        LaunchedEffect(Unit){
            effectHandler.effectFlow
                .onEach{ effect ->
                    handlerEffect(
                        context = context,
                        colors = colors,
                        defEffect = effect as DefaultEffectHandler.DefaultEffect,
                    )
                }
                .launchIn(this)
        }

        return LocalEffectHandler provides effectHandler
    }

    @Composable
    override fun Content(
        modifier: Modifier,
    ){
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = modifier,
            snackbar = { data ->
                val snackBarBody = snackBarBodyMap.remove(data.message) ?: return@SnackbarHost

                Snackbar(
                    modifier = modifier.padding(12.dp),
                    action = { snackBarBody.action?.invoke(data) },
                    backgroundColor = snackBarBody.backgroundColor,
                    contentColor = snackBarBody.contentColor,
                    content = { snackBarBody.content(snackBarBody.messageUI.getText()) }
                )
            }
        )
    }

    private suspend fun handlerEffect(
        context: Context,
        colors: AppColors,
        defEffect: DefaultEffectHandler.DefaultEffect
    ){
        when(defEffect){
            is DefaultEffectHandler.DefaultEffect.Navigate -> {
                routeActionHandler.navigate(defEffect.route)
            }
            is DefaultEffectHandler.DefaultEffect.SnackBar -> {
                val personalKey = UUID.randomUUID().toString()
                val snackBarExtensionBody = getSnackBarBody(
                    messageUI = defEffect.message,
                    colors = colors,
                    type = defEffect.type,
                )
                snackBarBodyMap[personalKey] = snackBarExtensionBody
                snackbarHostState.showSnackbar(message = personalKey)
            }
            is DefaultEffectHandler.DefaultEffect.MultiEffect -> {
                defEffect.effects.forEach { handlerEffect(context, colors, it) }
            }
        }
    }

    private fun getSnackBarBody(
        messageUI: StringUI,
        colors: AppColors,
        type: DefaultEffectHandler.DefaultEffect.SnackBar.Type
    ): SnackBarExtensionBody{
        return when(type){
            DefaultEffectHandler.DefaultEffect.SnackBar.Type.Error -> {
                SnackBarExtensionBody(
                    messageUI = messageUI,
                    backgroundColor = colors.contendPrimary,
                    contentColor = colors.textPrimary,
                    action = null,
                    content = { message -> Text(message) }
                )
            }
            DefaultEffectHandler.DefaultEffect.SnackBar.Type.Success -> {
                SnackBarExtensionBody(
                    messageUI = messageUI,
                    backgroundColor = colors.contendPrimary,
                    contentColor = colors.textPrimary,
                    action = null,
                    content = { message -> Text(message) }
                )
            }
        }
    }
}

private data class SnackBarExtensionBody(
    val messageUI: StringUI,
    val backgroundColor: Color,
    val contentColor: Color,
    val action: (@Composable (SnackbarData) -> Unit)? = null,
    val content: @Composable (String) -> Unit
)
