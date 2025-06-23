package com.example.dynamiclocale.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 인디고 색상을 기본 시드 색상으로 사용
private val DarkColorScheme = darkColorScheme(
    primary = IndigoDark,
    secondary = IndigoSecondaryDark,
    tertiary = IndigoTertiaryDark
)

private val LightColorScheme = lightColorScheme(
    primary = Indigo,
    secondary = IndigoSecondary,
    tertiary = IndigoTertiary
)

/**
 * 다이나믹 로케일 테마
 * Material You Dynamic Color를 지원하는 테마 컴포저블
 * 
 * @param darkTheme 다크 테마 사용 여부
 * @param dynamicColor 다이나믹 컬러 사용 여부 (기본값: true)
 * @param content 컨텐츠 컴포저블
 */
@Composable
fun DynamicLocaleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    // 컬러 스킴 결정
    val colorScheme = when {
        // Android 12 이상에서 다이나믹 컬러 사용
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        // 다크 테마 사용
        darkTheme -> DarkColorScheme
        // 라이트 테마 사용
        else -> LightColorScheme
    }
    
    // 시스템 UI 색상 설정
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            window.navigationBarColor = Color.Transparent.toArgb()
            
            // 다크 테마에 따라 시스템 바 아이콘 색상 설정
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}