package com.example.websocket_map.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(
    onClickDrivers: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        ElevatedButton(
            modifier = Modifier.align(Alignment.Center),
            onClick = {
                onClickDrivers()
            },
        ) {
            Text("Drivers")
        }
    }
}