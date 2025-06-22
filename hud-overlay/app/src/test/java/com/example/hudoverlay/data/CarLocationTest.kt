package com.example.hudoverlay.data

import com.google.android.gms.maps.model.LatLng
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * CarLocation 데이터 클래스 단위 테스트
 */
class CarLocationTest {

    @Test
    fun `위도, 경도, 속도값이 올바르게 저장되는지 확인`() {
        // 테스트 데이터 생성
        val carLocation = CarLocation(
            lat = 37.5665,
            lng = 126.9780,
            speedKph = 60.5f
        )
        
        // 데이터 검증
        assertEquals(37.5665, carLocation.lat, 0.0001)
        assertEquals(126.9780, carLocation.lng, 0.0001)
        assertEquals(60.5f, carLocation.speedKph, 0.01f)
    }
    
    @Test
    fun `toLatLng 함수가 올바르게 동작하는지 확인`() {
        // 테스트 데이터 생성
        val carLocation = CarLocation(
            lat = 37.5665,
            lng = 126.9780,
            speedKph = 60.5f
        )
        
        // LatLng 변환 함수 테스트
        val latLng: LatLng = carLocation.toLatLng()
        
        // 결과 검증
        assertEquals(37.5665, latLng.latitude, 0.0001)
        assertEquals(126.9780, latLng.longitude, 0.0001)
    }
}
