package com.example.hudoverlay.data

import com.google.android.gms.maps.model.LatLng

/**
 * 차량 위치 및 속도 정보를 담는 데이터 클래스
 *
 * @property lat 위도
 * @property lng 경도
 * @property speedKph 시속 (km/h)
 */
data class CarLocation(
    val lat: Double,
    val lng: Double,
    val speedKph: Float
) {
    /**
     * Google Maps용 LatLng 객체로 변환하는 확장 함수
     */
    fun toLatLng(): LatLng = LatLng(lat, lng)
}
