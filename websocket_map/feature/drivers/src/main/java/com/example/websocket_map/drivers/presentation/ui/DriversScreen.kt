package com.example.websocket_map.drivers.presentation.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.websocket_map.drivers.presentation.viemodel.DriversViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun DriversScreen(
    viewModel: DriversViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val drivers = uiState.driverList

    // 기본 카메라 위치 (서울 중심)
    val defaultLocation = LatLng(37.5665, 126.9780)
    
    // 카메라 상태 설정
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(defaultLocation, 15f)
    }

    LaunchedEffect(key1 = Unit) {
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(defaultLocation, 15f),
            durationMs = 500
        )
    }

    BackHandler {
        viewModel.disconnect()
        onBackPressed()
    }
    
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            drivers.forEachIndexed { index, driver ->
                val driverPosition = LatLng(driver.latitude, driver.longitude)
                Marker(
                    state = MarkerState(position = driverPosition),
                    title = "드라이버 ID: ${driver.id}",
                    snippet = "위도: ${driver.latitude}, 경도: ${driver.longitude}",
                )
            }
        }
    }
}