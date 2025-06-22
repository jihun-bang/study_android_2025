package com.example.websocket_map.drivers.domain.usecase

import com.example.websocket_map.drivers.domain.repository.DriversRepository
import jakarta.inject.Inject

class DisconnectUseCase @Inject constructor(
    private val repository: DriversRepository
) {
    operator fun invoke() = repository.disconnect()
}