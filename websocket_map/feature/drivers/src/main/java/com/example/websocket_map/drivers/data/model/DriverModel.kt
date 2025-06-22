package com.example.websocket_map.drivers.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class DriverModel(
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    companion object {
        fun fromJson(json: String): DriverModel {
            return Json.decodeFromString<DriverModel>(json)
        }
    }
}