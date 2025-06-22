package com.example.hudoverlay.feature.mapoverlay.composable

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import com.example.hudoverlay.feature.mapoverlay.MapOverlayViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.JointType
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.RoundCap
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapEffect
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.launch

/**
 * 지도와 HUD 오버레이를 표시하는 메인 화면
 */
@Composable
fun MapOverlayScreen(
    modifier: Modifier = Modifier,
    viewModel: MapOverlayViewModel = hiltViewModel()
) {
    // ViewModel로부터 UI 상태 수집
    val uiState by viewModel.uiState.collectAsState()
    
    // 현재 위치 추출
    val currentLocation = uiState.currentLocation
    val currentLatLng = currentLocation?.toLatLng() ?: DEFAULT_POSITION
    
    // 카메라 상태 생성
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLatLng, 15f)
    }
    
    // 마커 상태 생성
    val markerState = rememberMarkerState(position = currentLatLng)
    
    // 카메라 위치 업데이트
    LaunchedEffect(key1 = currentLatLng) {
        launch {
            markerState.position = currentLatLng
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f),
                durationMs = 500
            )
        }
    }
    
    // 지도 초기화 완료 알림
    LaunchedEffect(key1 = Unit) {
        viewModel.onMapInitialized()
    }

    Box(modifier = modifier.fillMaxSize()) {
        // 구글 지도 컴포넌트
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = false)
        ) {
            // 차량 마커
            if (currentLocation != null) {
                Marker(
                    state = markerState,
                    title = "내 차량",
                    snippet = "현재 속도: ${currentLocation.speedKph} km/h"
                )
            }
            
            // 경로 폴리라인
            if (uiState.pathPoints.size > 1) {
                Polyline(
                    points = uiState.pathPoints,
                    color = getColorForSpeed(uiState.currentSpeed),
                    width = 10f,
                    jointType = JointType.ROUND,
                    startCap = RoundCap(),
                    endCap = RoundCap()
                )
            }
            
            // MapEffect를 사용하여 지도에 직접적인 조작이 필요한 경우 처리
            MapEffect { map ->
                map.uiSettings.apply {
                    isZoomControlsEnabled = true
                    isMyLocationButtonEnabled = false
                }
            }
        }
        
        // 속도 HUD 오버레이
        SpeedHudOverlay(
            speedKph = uiState.currentSpeed,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 48.dp)
        )
    }
}

/**
 * 속도에 따른 색상 결정
 */
private fun getColorForSpeed(speedKph: Float) = when {
    speedKph >= 60f -> Color.Red
    speedKph >= 30f -> Color.Yellow
    else -> Color.Green
}

/**
 * 기본 시작 위치(서울 강남역)
 */
private val DEFAULT_POSITION = LatLng(37.5044, 127.0489)
