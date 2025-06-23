package com.example.dynamiclocale

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.dynamiclocale.ui.MainScreen
import com.example.dynamiclocale.ui.theme.DynamicLocaleTheme

class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState()

            DynamicLocaleTheme(darkTheme = isDarkMode) {
                MainScreen(viewModel = viewModel)
            }
        }
    }
}