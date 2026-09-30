package com.danielhc.showcase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.danielhc.showcase.core.designsystem.theme.DanielShowcaseTheme
import com.danielhc.showcase.navigation.AppNavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DanielShowcaseTheme {
                AppNavHost()
            }
        }
    }
}
