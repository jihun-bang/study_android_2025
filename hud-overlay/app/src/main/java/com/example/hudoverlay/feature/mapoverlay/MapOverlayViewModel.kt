package com.example.hudoverlay.feature.mapoverlay

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hudoverlay.data.CarLocation
import com.example.hudoverlay.domain.usecase.GetCarLocationStreamUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 지도 오버레이 화면의 ViewModel
 * 차량 위치 데이터를 수집하고 UI 상태를 관리
 */
@HiltViewModel
class MapOverlayViewModel @Inject constructor(
    private val getCarLocationStreamUseCase: GetCarLocationStreamUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapOverlayUiState())
    
    /**
     * UI에 노출되는 상태 Flow
     */
    val uiState: StateFlow<MapOverlayUiState> = _uiState.asStateFlow()

    /**
     * 뷰모델 초기화 시 차량 위치 데이터 수집 시작
     */
    init {
        collectCarLocation()
    }

    /**
     * 차량 위치 데이터 Flow를 수집하여 UI 상태 업데이트
     */
    private fun collectCarLocation() {
        viewModelScope.launch {
            getCarLocationStreamUseCase().collectLatest { location ->
                updateLocationState(location)
            }
        }
    }

    /**
     * 새 위치 정보로 UI 상태 업데이트
     * 
     * @param location 새로운 차량 위치 정보
     */
    private fun updateLocationState(location: CarLocation) {
        val currentPoints = _uiState.value.pathPoints
        val newPoint = location.toLatLng()
        
        // 현재 상태를 복사하면서 필요한 값들만 업데이트
        _uiState.value = _uiState.value.copy(
            currentLocation = location,
            currentSpeed = location.speedKph,
            pathPoints = currentPoints + newPoint,
            isMapLoading = false
        )
    }
    
    /**
     * 지도가 초기화되었음을 알림
     */
    fun onMapInitialized() {
        _uiState.value = _uiState.value.copy(isMapLoading = false)
    }
}
