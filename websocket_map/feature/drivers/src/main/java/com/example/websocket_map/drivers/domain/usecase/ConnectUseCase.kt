package com.example.websocket_map.drivers.domain.usecase

import com.example.websocket_map.drivers.domain.repository.DriversRepository
import jakarta.inject.Inject

class ConnectUseCase @Inject constructor(
    private val repository: DriversRepository
) {
    operator fun invoke() {
        val serverUrl = "wss://demo.piesocket.com/v3/channel_123?api_key=VCXCEuvhGcBDP7XhiJJUDvR1e1D3eiVjgZ9VRiaV&notify_self"
        repository.connectToDriversServer(serverUrl)
    }
}