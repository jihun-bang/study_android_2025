package com.example.hudoverlay.data.repository

import com.example.hudoverlay.data.CarLocation
import com.example.hudoverlay.data.MockCarLocationSource
import com.example.hudoverlay.domain.repository.CarLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * CarLocationRepository 인터페이스의 구현체
 * MockCarLocationSource로부터 차량 위치 데이터를 제공받아 중계
 */
@Singleton
class CarLocationRepositoryImpl @Inject constructor(
    private val mockCarLocationSource: MockCarLocationSource
) : CarLocationRepository {
    
    /**
     * MockCarLocationSource의 Flow를 그대로 반환
     * 필요시 여기서 데이터 변환 또는 가공 가능
     */
    override fun getCarLocationStream(): Flow<CarLocation> {
        return mockCarLocationSource.flow
    }
}
