package io.my.ui.util

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource


sealed interface StringUI{

    data class Message(val body: String): StringUI
    data class MessageId(@StringRes val id: Int): StringUI

    fun getText(context: Context): String {
        return when (this){
            is Message -> body
            is MessageId -> context.getString(id)
        }
    }

    @Composable
    fun getText(): String {
        return when (this){
            is Message -> body
            is MessageId -> stringResource(id = id)
        }
    }
}
