package com.example.hudoverlay.data.repository

import com.example.hudoverlay.data.CarLocation
import com.example.hudoverlay.data.MockCarLocationSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * CarLocationRepositoryImpl 단위 테스트
 */
@ExperimentalCoroutinesApi
class CarLocationRepositoryImplTest {

    // 테스트할 대상
    private lateinit var repository: CarLocationRepositoryImpl
    
    // 의존성 목 객체
    private val mockSource: MockCarLocationSource = mock()
    
    // 테스트용 더미 데이터
    private val testLocation = CarLocation(37.5109, 127.0578, 50f)
    private val testFlow: Flow<CarLocation> = flowOf(testLocation)
    
    @Before
    fun setUp() {
        // MockCarLocationSource의 flow가 테스트용 flow를 반환하도록 설정
        whenever(mockSource.flow).thenReturn(testFlow)
        
        // 테스트할 리포지토리 인스턴스 생성
        repository = CarLocationRepositoryImpl(mockSource)
    }
    
    @Test
    fun `위치 스트림이 올바르게 반환되는지 확인`() = runTest {
        // 테스트 실행
        val result = repository.getCarLocationStream().first()
        
        // 결과 검증
        assertEquals(testLocation, result)
        assertEquals(37.5109, result.lat, 0.0001)
        assertEquals(127.0578, result.lng, 0.0001)
        assertEquals(50f, result.speedKph, 0.1f)
    }
}
