package com.example.websocket_map.drivers.presentation.viemodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.websocket_map.drivers.domain.usecase.ConnectUseCase
import com.example.websocket_map.drivers.domain.usecase.DisconnectUseCase
import com.example.websocket_map.drivers.domain.usecase.ObserveSocketUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class DriversViewModel @Inject constructor(
    connectUseCase: ConnectUseCase,
    observeSocketUseCase: ObserveSocketUseCase,
    private val disconnectUseCase: DisconnectUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DriversUiState())
    val uiState: StateFlow<DriversUiState> = _uiState.asStateFlow()

    init {
        connectUseCase()
        viewModelScope.launch {
            observeSocketUseCase()
                .sample(100)
                .conflate()
                .collect { driver ->
                    Log.d("DriversViewModel", "드라이버 위치 업데이트: $driver")
                    // 리스트 업데이트 최적화: 기존 드라이버 제거 후 새 드라이버 추가
                    val updatedList = _uiState.value.driverList.filter { it.id != driver.id } + driver
                    _uiState.value = _uiState.value.copy(
                        driverList = updatedList
                    )
                }
        }
    }

    fun disconnect() {
        Log.d("DriversViewModel", "연결 종료")
        disconnectUseCase()
    }
}