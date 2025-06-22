package com.example.websocket_map.drivers.data.repository

import com.example.websocket_map.drivers.data.datasource.DriversWebSocketDataSource
import com.example.websocket_map.drivers.data.datasource.WebSocketError
import com.example.websocket_map.drivers.data.model.DriverModel
import com.example.websocket_map.drivers.domain.repository.DriversRepository
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * DriversRepository 인터페이스의 구현체
 * DriversWebSocketDataSource를 사용하여 웹소켓 통신을 처리합니다.
 */
@Singleton
class DriversRepositoryImpl @Inject constructor(
    private val driversWebSocketDataSource: DriversWebSocketDataSource
) : DriversRepository {
    
    /**
     * 드라이버 위치 정보를 스트리밍하는 Flow
     */
    override val driverLocationFlow: SharedFlow<DriverModel>
        get() = driversWebSocketDataSource.driverLocationFlow
    
    /**
     * 웹소켓 연결 상태를 나타내는 Flow
     */
    override val connectionState: StateFlow<Boolean>
        get() = driversWebSocketDataSource.connectionState
    
    /**
     * 웹소켓 에러 이벤트를 전달하는 Flow
     */
    override val errorFlow: SharedFlow<WebSocketError>
        get() = driversWebSocketDataSource.errorFlow
    
    /**
     * 드라이버 위치 정보 웹소켓 서버에 연결합니다.
     * @param serverUrl 웹소켓 서버 URL
     */
    override fun connectToDriversServer(serverUrl: String) {
        driversWebSocketDataSource.connectToDriversServer(serverUrl)
    }
    
    /**
     * 드라이버 위치 업데이트 메시지를 전송합니다.
     * @param locationData 위치 데이터 JSON 문자열
     * @return 전송 성공 여부
     */
    override fun sendDriverLocationUpdate(locationData: String): Boolean {
        return driversWebSocketDataSource.sendDriverLocationUpdate(locationData)
    }
    
    /**
     * 웹소켓 연결을 종료합니다.
     * @return 연결 종료 성공 여부
     */
    override fun disconnect(): Boolean {
        return driversWebSocketDataSource.disconnect()
    }
    
    /**
     * 현재 웹소켓 연결 상태를 확인합니다.
     * @return 연결 상태
     */
    override fun isConnected(): Boolean {
        return driversWebSocketDataSource.isConnected()
    }
}
