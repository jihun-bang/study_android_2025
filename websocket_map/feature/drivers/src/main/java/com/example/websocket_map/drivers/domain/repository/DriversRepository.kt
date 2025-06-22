package com.example.websocket_map.drivers.domain.repository

import com.example.websocket_map.drivers.data.datasource.WebSocketError
import com.example.websocket_map.drivers.data.model.DriverModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 드라이버 관련 데이터를 처리하는 리포지토리 인터페이스
 * 웹소켓을 통한 드라이버 위치 정보 스트리밍 및 상태 관리를 담당합니다.
 */
interface DriversRepository {
    /**
     * 드라이버 위치 정보를 스트리밍하는 Flow
     */
    val driverLocationFlow: SharedFlow<DriverModel>
    
    /**
     * 웹소켓 연결 상태를 나타내는 Flow
     */
    val connectionState: StateFlow<Boolean>
    
    /**
     * 웹소켓 에러 이벤트를 전달하는 Flow
     */
    val errorFlow: SharedFlow<WebSocketError>
    
    /**
     * 드라이버 위치 정보 웹소켓 서버에 연결합니다.
     * @param serverUrl 웹소켓 서버 URL
     */
    fun connectToDriversServer(serverUrl: String)
    
    /**
     * 드라이버 위치 업데이트 메시지를 전송합니다.
     * @param locationData 위치 데이터 JSON 문자열
     * @return 전송 성공 여부
     */
    fun sendDriverLocationUpdate(locationData: String): Boolean
    
    /**
     * 웹소켓 연결을 종료합니다.
     * @return 연결 종료 성공 여부
     */
    fun disconnect(): Boolean
    
    /**
     * 현재 웹소켓 연결 상태를 확인합니다.
     * @return 연결 상태
     */
    fun isConnected(): Boolean
}