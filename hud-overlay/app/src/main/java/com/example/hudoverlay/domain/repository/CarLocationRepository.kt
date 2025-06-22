package com.example.hudoverlay.domain.repository

import com.example.hudoverlay.data.CarLocation
import kotlinx.coroutines.flow.Flow

/**
 * 차량 위치 데이터 접근을 위한 리포지토리 인터페이스
 * 도메인 계층과 데이터 계층을 분리하는 역할
 */
interface CarLocationRepository {
    
    /**
     * 차량 위치 정보 스트림을 제공하는 Flow
     * @return 차량 위치 정보 Flow
     */
    fun getCarLocationStream(): Flow<CarLocation>
}
