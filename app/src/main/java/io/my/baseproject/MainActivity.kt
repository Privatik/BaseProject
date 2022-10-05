package io.my.baseproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.my.ui.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = ProjectTheme.colors.backgroundPrimary
                ) {
                    val colors = ProjectTheme.colors
                    Log.d("ReCompose","MainActivity")
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Log.d("ReCompose","MainActivity Box")
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                contentColor = ProjectTheme.colors.backgroundPrimary
                            ),
                            onClick = {
                                val isDark = colors.isDark
                                Log.d("ReCompose","MainActivity is theme is $isDark")
                                if (isDark){
                                    colors.updateColors(colorTheme = ColorForTheme.Light)
                                } else {
                                    colors.updateColors(colorTheme = ColorForTheme.Dark)
                                }
                        }) {
                            Text(text = "Change color")
                        }
                    }
                }
            }
        }
    }
}