package com.example.websocket_map.drivers.data.datasource

import android.util.Log
import com.example.websocket_map.drivers.data.model.DriverModel
import com.example.websocket_map.network.websocket.OkHttpWebSocketService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

/**
 * 드라이버 위치 정보를 웹소켓을 통해 처리하는 데이터소스
 * OkHttpWebSocketService를 활용하여 드라이버 관련 실시간 데이터를 주고받습니다.
 */
@Singleton
class DriversWebSocketDataSource @Inject constructor(
    private val webSocketService: OkHttpWebSocketService
) {
    // 드라이버 위치 데이터를 위한 SharedFlow
    private val _driverLocationFlow = MutableSharedFlow<DriverModel>(replay = 0, extraBufferCapacity = 64)
    val driverLocationFlow: SharedFlow<DriverModel> = _driverLocationFlow.asSharedFlow()

    // 연결 상태를 위한 StateFlow
    private val _connectionState = MutableStateFlow(false)
    val connectionState: StateFlow<Boolean> = _connectionState.asStateFlow()

    // 에러 이벤트를 위한 SharedFlow
    private val _errorFlow = MutableSharedFlow<WebSocketError>()
    val errorFlow: SharedFlow<WebSocketError> = _errorFlow.asSharedFlow()

    // JSON 파서 설정
    private val jsonParser = Json { ignoreUnknownKeys = true }

    // 코루틴 스코프 생성
    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    // 웹소켓 이벤트 리스너 인스턴스 생성
    private val eventListener = DriverWebSocketEventListener()

    /**
     * 드라이버 위치 정보 웹소켓 서버에 연결합니다.
     * @param serverUrl 웹소켓 서버 URL
     */
    fun connectToDriversServer(serverUrl: String) {
        Log.d("WebSocket", "서버에 연결 시도: $serverUrl")
        webSocketService.connect(serverUrl, eventListener)
    }

    /**
     * 드라이버 위치 업데이트 메시지를 전송합니다.
     * @param locationData 위치 데이터 JSON 문자열
     * @return 전송 성공 여부
     */
    fun sendDriverLocationUpdate(locationData: String): Boolean {
        return webSocketService.sendMessage(locationData)
    }

    /**
     * 웹소켓 연결을 종료합니다.
     * @return 연결 종료 성공 여부
     */
    fun disconnect(): Boolean {
        return webSocketService.disconnect()
    }

    /**
     * 현재 웹소켓 연결 상태를 확인합니다.
     * @return 연결 상태
     */
    fun isConnected(): Boolean {
        return webSocketService.isConnected()
    }

    /**
     * 웹소켓 이벤트를 처리하는 내부 리스너입니다.
     */
    private inner class DriverWebSocketEventListener : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d("WebSocket", "연결 성공")
            _connectionState.tryEmit(true)
            
            // 코루틴을 사용하여 백그라운드에서 위치 데이터 전송
            coroutineScope.launch {
                Log.d("WebSocket", "위치 데이터 전송 시작")
                val route = listOf(
                    DriverModel("0", 37.5109, 127.0578),
                    DriverModel("0", 37.5106, 127.0600),
                    DriverModel("0", 37.5102, 127.0635),
                    DriverModel("0", 37.5097, 127.0665),
                    DriverModel("1", 37.5090, 127.0695),
                    DriverModel("1", 37.5080, 127.0715),
                    DriverModel("1", 37.5065, 127.0735),
                    DriverModel("1", 37.5045, 127.0730),
                    DriverModel("1", 37.5025, 127.0725),
                    DriverModel("1", 37.5005, 127.0720),
                    DriverModel("2", 37.4985, 127.0715),
                    DriverModel("2", 37.4965, 127.0710),
                    DriverModel("2", 37.4945, 127.0705),
                    DriverModel("2", 37.4925, 127.0700),
                    DriverModel("2", 37.4905, 127.0695),
                    DriverModel("2", 37.4885, 127.0690),
                    DriverModel("2", 37.4865, 127.0685),
                    DriverModel("3", 37.4845, 127.0680),
                    DriverModel("3", 37.4825, 127.0675),
                    DriverModel("3", 37.4815, 127.0655),
                    DriverModel("3", 37.4825, 127.0635),
                    DriverModel("3", 37.4835, 127.0615),
                    DriverModel("3", 37.4845, 127.0595),
                    DriverModel("3", 37.4855, 127.0575),
                    DriverModel("4", 37.4865, 127.0555),
                    DriverModel("4", 37.4875, 127.0535),
                    DriverModel("4", 37.4885, 127.0515),
                    DriverModel("4", 37.4905, 127.0510),
                    DriverModel("4", 37.4925, 127.0515),
                    DriverModel("4", 37.4945, 127.0520),
                    DriverModel("5", 37.4965, 127.0525),
                    DriverModel("5", 37.4985, 127.0530),
                    DriverModel("5", 37.5005, 127.0535),
                    DriverModel("5", 37.5025, 127.0540),
                    DriverModel("5", 37.5045, 127.0545),
                    DriverModel("5", 37.5065, 127.0550),
                    DriverModel("5", 37.5085, 127.0555),
                    DriverModel("5", 37.5090, 127.0570),
                    DriverModel("5", 37.5109, 127.0578),
                )

                while (isConnected()) {
                    for (location in route) {
                        _driverLocationFlow.tryEmit(location)
                        delay(50)
                    }
                }
            }
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d("WebSocket", "메시지 수신: $text")
            try {
                // 오류 메시지인지 확인
                val jsonElement = jsonParser.parseToJsonElement(text)
                if (jsonElement is JsonObject && jsonElement.containsKey("error")) {
                    val errorMessage = jsonElement["error"]?.jsonPrimitive?.content
                    Log.e("WebSocket", "서버 오류: $errorMessage")
                    _errorFlow.tryEmit(WebSocketError("서버 오류: $errorMessage"))
                    return
                }
                
                // 정상 메시지인 경우 드라이버 모델로 변환
                val driverModel = DriverModel.fromJson(text)
                _driverLocationFlow.tryEmit(driverModel)
            } catch (e: Exception) {
                Log.e("WebSocket", "메시지 파싱 오류: ${e.message}", e)
                _errorFlow.tryEmit(WebSocketError("메시지 파싱 오류: ${e.message}", e))
            }
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            Log.d("WebSocket", "연결 종료: code=$code, reason=$reason")
            _connectionState.tryEmit(false)
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e("WebSocket", "Error: ${t.message}")
            _connectionState.tryEmit(false)
            _errorFlow.tryEmit(WebSocketError(t.message ?: "알 수 없는 오류", t))
        }
    }
}

/**
 * 웹소켓 에러 정보를 담는 클래스
 */
data class WebSocketError(
    val message: String,
    val throwable: Throwable? = null
)