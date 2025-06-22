package com.example.hudoverlay.domain.usecase

import com.example.hudoverlay.data.CarLocation
import com.example.hudoverlay.domain.repository.CarLocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * 차량 위치 정보 스트림을 제공하는 유스케이스
 * 비즈니스 로직을 캡슐화하고 프레젠테이션 계층과 도메인 계층을 분리
 */
class GetCarLocationStreamUseCase @Inject constructor(
    private val carLocationRepository: CarLocationRepository
) {
    /**
     * 차량 위치 정보 스트림을 반환
     * @return 1초 간격으로 업데이트되는 차량 위치 정보 Flow
     */
    operator fun invoke(): Flow<CarLocation> {
        return carLocationRepository.getCarLocationStream()
    }
}
