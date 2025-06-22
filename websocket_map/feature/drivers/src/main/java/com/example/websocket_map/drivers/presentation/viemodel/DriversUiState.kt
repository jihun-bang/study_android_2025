package com.example.websocket_map.drivers.presentation.viemodel

import com.example.websocket_map.drivers.data.model.DriverModel

data class DriversUiState(
    val driverList: List<DriverModel> = emptyList()
)