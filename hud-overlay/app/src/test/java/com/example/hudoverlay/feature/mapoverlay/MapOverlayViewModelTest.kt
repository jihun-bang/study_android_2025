package com.example.hudoverlay.feature.mapoverlay

import com.example.hudoverlay.data.CarLocation
import com.example.hudoverlay.domain.usecase.GetCarLocationStreamUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever

/**
 * MapOverlayViewModel 단위 테스트
 */
@ExperimentalCoroutinesApi
class MapOverlayViewModelTest {

    // 테스트 디스패처
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    
    // 테스트할 대상
    private lateinit var viewModel: MapOverlayViewModel
    
    // 의존성 목 객체
    private val mockGetCarLocationStreamUseCase: GetCarLocationStreamUseCase = mock()
    
    // 테스트용 Flow
    private val carLocationFlow = MutableSharedFlow<CarLocation>()
    
    @Before
    fun setUp() {
        // 메인 디스패처를 테스트 디스패처로 교체
        Dispatchers.setMain(testDispatcher)
        
        // 목 객체 설정
        whenever(mockGetCarLocationStreamUseCase.invoke()).thenReturn(carLocationFlow)
        
        // 테스트할 뷰모델 인스턴스 생성
        viewModel = MapOverlayViewModel(mockGetCarLocationStreamUseCase)
    }
    
    @After
    fun tearDown() {
        // 메인 디스패처 리셋
        Dispatchers.resetMain()
    }
    
    @Test
    fun `초기 상태 확인`() = testScope.runTest {
        // 초기 UI 상태 확인
        val initialState = viewModel.uiState.value
        
        assertEquals(0f, initialState.currentSpeed, 0f)
        assertEquals(0, initialState.pathPoints.size)
        assertEquals(null, initialState.currentLocation)
        assertTrue(initialState.isMapLoading)
    }
    
    @Test
    fun `위치 업데이트 시 상태 변경 확인`() = testScope.runTest {
        // 상태 컬렉션 시작
        val collectJob = launch { viewModel.uiState.collect() }
        
        // 테스트 데이터 방출
        val testLocation = CarLocation(37.5109, 127.0578, 50f)
        carLocationFlow.emit(testLocation)
        
        // 코루틴 모든 작업 완료 대기
        advanceUntilIdle()
        
        // UI 상태 검증
        val state = viewModel.uiState.value
        assertEquals(testLocation, state.currentLocation)
        assertEquals(50f, state.currentSpeed, 0f)
        assertEquals(1, state.pathPoints.size)
        assertEquals(37.5109, state.pathPoints[0].latitude, 0.0001)
        assertEquals(127.0578, state.pathPoints[0].longitude, 0.0001)
        
        // 컬렉션 작업 취소
        collectJob.cancel()
    }
    
    @Test
    fun `지도 초기화 호출 시 로딩 상태 업데이트 확인`() = testScope.runTest {
        // 상태 컬렉션 시작
        val collectJob = launch { viewModel.uiState.collect() }
        
        // 초기 상태 확인
        assertTrue(viewModel.uiState.value.isMapLoading)
        
        // 지도 초기화 함수 호출
        viewModel.onMapInitialized()
        
        // 모든 작업이 처리될 때까지 대기
        advanceUntilIdle()
        
        // UI 상태 검증
        assertFalse(viewModel.uiState.value.isMapLoading)
        
        // 컬렉션 작업 취소
        collectJob.cancel()
    }
}
