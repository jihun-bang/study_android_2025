package com.example.hudoverlay.data

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.seconds

/**
 * 더미 위치 데이터를 1초에 한 번씩 제공하는 소스 클래스
 * 실제 경로를 반복하는 시뮬레이션 데이터 생성
 */
@Singleton
class MockCarLocationSource @Inject constructor() {

    /**
     * 차량 위치 정보를 1초마다 emit하는 Flow
     */
    val flow: Flow<CarLocation> = flow {
        // 서울 도심 일부 경로 (강남 일대)
        val route = listOf(
            CarLocation(37.5109, 127.0578, 0f),
            CarLocation(37.5106, 127.0600, 25f),
            CarLocation(37.5102, 127.0635, 35f),
            CarLocation(37.5097, 127.0665, 45f),
            CarLocation(37.5090, 127.0695, 55f),
            CarLocation(37.5080, 127.0715, 65f),
            CarLocation(37.5065, 127.0735, 70f),
            CarLocation(37.5045, 127.0730, 55f),
            CarLocation(37.5025, 127.0725, 40f),
            CarLocation(37.5005, 127.0720, 35f),
            CarLocation(37.4985, 127.0715, 0f),
            CarLocation(37.4965, 127.0710, 15f),
            CarLocation(37.4945, 127.0705, 35f),
            CarLocation(37.4925, 127.0700, 55f),
            CarLocation(37.4905, 127.0695, 50f),
            CarLocation(37.4885, 127.0690, 45f),
            CarLocation(37.4865, 127.0685, 40f),
            CarLocation(37.4845, 127.0680, 25f),
            CarLocation(37.4825, 127.0675, 30f),
            CarLocation(37.4815, 127.0655, 35f),
            CarLocation(37.4825, 127.0635, 40f),
            CarLocation(37.4835, 127.0615, 45f),
            CarLocation(37.4845, 127.0595, 50f),
            CarLocation(37.4855, 127.0575, 55f),
            CarLocation(37.4865, 127.0555, 50f),
            CarLocation(37.4875, 127.0535, 45f),
            CarLocation(37.4885, 127.0515, 35f),
            CarLocation(37.4905, 127.0510, 25f),
            CarLocation(37.4925, 127.0515, 20f),
            CarLocation(37.4945, 127.0520, 35f),
            CarLocation(37.4965, 127.0525, 55f),
            CarLocation(37.4985, 127.0530, 65f),
            CarLocation(37.5005, 127.0535, 60f),
            CarLocation(37.5025, 127.0540, 55f),
            CarLocation(37.5045, 127.0545, 45f),
            CarLocation(37.5065, 127.0550, 35f),
            CarLocation(37.5085, 127.0555, 25f),
            CarLocation(37.5090, 127.0570, 15f),
            CarLocation(37.5109, 127.0578, 0f),
        )

        // 위치 정보 무한 반복
        while (true) {
            for (location in route) {
                emit(location)
                delay(1.seconds) // 1초마다 발행
            }
        }
    }
}
