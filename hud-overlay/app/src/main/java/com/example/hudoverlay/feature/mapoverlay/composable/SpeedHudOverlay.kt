package com.example.hudoverlay.feature.mapoverlay.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * 현재 속도를 표시하는 HUD 오버레이 컴포넌트
 * 
 * @param speedKph 현재 속도 (km/h)
 * @param modifier Compose 수정자
 */
@Composable
fun SpeedHudOverlay(
    speedKph: Float,
    modifier: Modifier = Modifier
) {
    // 속도에 따른 색상 설정
    val backgroundColor = when {
        speedKph >= 60f -> Color(0xAAFF3B30) // 빨간색 (반투명)
        speedKph >= 30f -> Color(0xAAFF9500) // 주황색 (반투명)
        else -> Color(0xAA34C759) // 녹색 (반투명)
    }
    
    // 접근성용 콘텐츠 설명
    val speedDescription = "현재 속도 시속 ${speedKph.toInt()}킬로미터"
    
    Surface(
        modifier = modifier
            .semantics { contentDescription = speedDescription },
        color = Color.Transparent
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundColor)
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            Text(
                text = "${speedKph.toInt()} km/h",
                color = Color.White,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
