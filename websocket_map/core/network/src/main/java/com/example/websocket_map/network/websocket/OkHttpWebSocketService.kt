package com.example.websocket_map.network.websocket

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject
import javax.inject.Singleton

/**
 * OkHttp를 사용한 WebSocket 서비스
 * @param okHttpClient OkHttp 클라이언트 인스턴스
 */
@Singleton
class OkHttpWebSocketService @Inject constructor(
    private val okHttpClient: OkHttpClient
) {
    private var webSocket: WebSocket? = null
    private var isConnected = false

    /**
     * 웹소켓 연결을 시작합니다.
     * @param url 연결할 웹소켓 서버 URL
     */
    fun connect(
        url: String,
        eventListener: WebSocketListener?
    ) {
        // 이미 연결되어 있다면 기존 연결 종료
        if (isConnected) {
            disconnect()
        }

        val request = Request.Builder()
            .url(url)
            .build()

        webSocket = okHttpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                isConnected = true
                eventListener?.onOpen(webSocket, response) ?: super.onOpen(webSocket, response)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                eventListener?.onMessage(webSocket, text) ?: super.onMessage(webSocket, text)
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                isConnected = false
                eventListener?.onClosed(webSocket, code, reason) ?: super.onClosed(webSocket, code, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                isConnected = false
                eventListener?.onFailure(webSocket, t, response) ?: super.onFailure(webSocket, t, response)
            }
        })
    }

    /**
     * 텍스트 메시지를 전송합니다.
     * @param message 전송할 텍스트 메시지
     * @return 메시지 전송 성공 여부
     */
    fun sendMessage(message: String): Boolean {
        return webSocket?.send(message) == true
    }

    /**
     * 바이너리 메시지를 전송합니다.
     * @param bytes 전송할 바이너리 데이터
     * @return 메시지 전송 성공 여부
     */
    fun sendMessage(bytes: ByteString): Boolean {
        return webSocket?.send(bytes) == true
    }

    /**
     * 웹소켓 연결을 종료합니다.
     * @param code 종료 코드
     * @param reason 종료 이유
     * @return 연결 종료 성공 여부
     */
    fun disconnect(code: Int = 1000, reason: String = "정상 종료"): Boolean {
        if (webSocket == null) return false

        val result = webSocket?.close(code, reason) == true
        if (result) {
            isConnected = false
            webSocket = null
        }
        return result
    }

    /**
     * 현재 웹소켓 연결 상태를 반환합니다.
     * @return 연결 상태
     */
    fun isConnected(): Boolean = isConnected
}
