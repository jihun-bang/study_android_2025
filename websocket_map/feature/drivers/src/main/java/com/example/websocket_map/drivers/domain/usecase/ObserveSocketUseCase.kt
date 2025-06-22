package com.example.websocket_map.drivers.domain.usecase

import com.example.websocket_map.drivers.data.model.DriverModel
import com.example.websocket_map.drivers.domain.repository.DriversRepository
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class ObserveSocketUseCase @Inject constructor(
    private val repository: DriversRepository
) {
    operator fun invoke(): SharedFlow<DriverModel> = repository.driverLocationFlow
}