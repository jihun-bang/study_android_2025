package com.example.hudoverlay

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.hudoverlay.feature.mapoverlay.composable.MapOverlayScreen
import com.example.hudoverlay.ui.theme.HudOverlayTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * 앱의 메인 액티비티
 * 지도 오버레이와 속도 HUD를 표시하는 진입점
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // 엣지 투 엣지 UI를 활성화하여 전체 화면 사용
        
        setContent {
            HudOverlayTheme {
                // 테마가 적용된 메인 컨테이너 Surface
                MapOverlayScreen()
            }
        }
    }
}