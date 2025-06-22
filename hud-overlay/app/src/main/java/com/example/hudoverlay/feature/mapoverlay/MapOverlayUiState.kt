package com.example.hudoverlay.feature.mapoverlay

import com.example.hudoverlay.data.CarLocation
import com.google.android.gms.maps.model.LatLng

/**
 * 지도 오버레이 화면의 UI 상태를 담는 클래스
 * 불변(immutable) 상태 객체로 관리하여 단방향 데이터 흐름 유지
 */
data class MapOverlayUiState(
    /**
     * 현재 차량 위치
     */
    val currentLocation: CarLocation? = null,
    
    /**
     * 이동 경로 좌표 목록
     */
    val pathPoints: List<LatLng> = emptyList(),
    
    /**
     * 현재 속도 (km/h)
     */
    val currentSpeed: Float = 0f,
    
    /**
     * 지도 로딩 상태
     */
    val isMapLoading: Boolean = true
)
