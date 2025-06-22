package com.example.websocet_map

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.example.websocet_map.navigation.MainNavigation
import com.example.websocet_map.ui.theme.Websocet_mapTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Websocet_mapTheme {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        Color.Transparent.toArgb(),
                        Color.Transparent.toArgb(),
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        Color.Transparent.toArgb(),
                        Color.Transparent.toArgb(),
                    ),
                )
                MainNavigation()
            }
        }
    }
}