package com.example.dynamiclocale.ui

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.example.dynamiclocale.MainViewModel
import com.example.dynamiclocale.ui.theme.DynamicLocaleTheme

/**
 * 로케일 프리뷰 파라미터 제공자
 */
class LocalePreviewParameterProvider : PreviewParameterProvider<String> {
    override val values = sequenceOf("ko", "en", "ja")
}

/**
 * 다크 모드 프리뷰 파라미터 제공자
 */
class DarkModePreviewParameterProvider : PreviewParameterProvider<Boolean> {
    override val values = sequenceOf(false, true)
}

/**
 * 미리보기용 ViewModel 생성
 */
private fun createPreviewViewModel(locale: String, isDarkMode: Boolean): MainViewModel {
    val viewModel = MainViewModel(Application())
    
    // 로케일 설정
    viewModel.setLocale(locale)
    
    // 다크 모드 설정
    if (isDarkMode) {
        viewModel.toggleDarkMode()
    }
    
    return viewModel
}

/**
 * 메인 화면 미리보기 (한국어, 라이트 모드)
 */
@Preview(name = "한국어 라이트 모드", showBackground = true)
@Composable
fun MainScreenPreviewKoLight() {
    val viewModel = createPreviewViewModel("ko", false)
    
    DynamicLocaleTheme(darkTheme = false) {
        MainScreen(viewModel = viewModel)
    }
}

/**
 * 메인 화면 미리보기 (한국어, 다크 모드)
 */
@Preview(name = "한국어 다크 모드", showBackground = true)
@Composable
fun MainScreenPreviewKoDark() {
    val viewModel = createPreviewViewModel("ko", true)
    
    DynamicLocaleTheme(darkTheme = true) {
        MainScreen(viewModel = viewModel)
    }
}

/**
 * 메인 화면 미리보기 (영어, 라이트 모드)
 */
@Preview(name = "영어 라이트 모드", showBackground = true, locale = "en")
@Composable
fun MainScreenPreviewEnLight() {
    val viewModel = createPreviewViewModel("en", false)
    
    DynamicLocaleTheme(darkTheme = false) {
        MainScreen(viewModel = viewModel)
    }
}

/**
 * 메인 화면 미리보기 (영어, 다크 모드)
 */
@Preview(name = "영어 다크 모드", showBackground = true, locale = "en")
@Composable
fun MainScreenPreviewEnDark() {
    val viewModel = createPreviewViewModel("en", true)
    
    DynamicLocaleTheme(darkTheme = true) {
        MainScreen(viewModel = viewModel)
    }
}

/**
 * 메인 화면 미리보기 (일본어, 라이트 모드)
 */
@Preview(name = "일본어 라이트 모드", showBackground = true, locale = "ja")
@Composable
fun MainScreenPreviewJaLight() {
    val viewModel = createPreviewViewModel("ja", false)
    
    DynamicLocaleTheme(darkTheme = false) {
        MainScreen(viewModel = viewModel)
    }
}

/**
 * 메인 화면 미리보기 (일본어, 다크 모드)
 */
@Preview(name = "일본어 다크 모드", showBackground = true, locale = "ja")
@Composable
fun MainScreenPreviewJaDark() {
    val viewModel = createPreviewViewModel("ja", true)
    
    DynamicLocaleTheme(darkTheme = true) {
        MainScreen(viewModel = viewModel)
    }
}
