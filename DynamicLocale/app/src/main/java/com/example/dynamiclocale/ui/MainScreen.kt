package com.example.dynamiclocale.ui

import android.provider.CalendarContract
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsProperties.ImeAction
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.example.dynamiclocale.MainViewModel
import com.example.dynamiclocale.R
import com.example.dynamiclocale.ui.sheet.PoiSearchBottomSheet
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * 메인 화면 컴포저블
 * 
 * @param viewModel 메인 화면의 ViewModel
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    // 현재 로케일
    val currentLocale by viewModel.currentLocale.collectAsState()
    // 다크 모드 상태
    val isDarkMode by viewModel.isDarkMode.collectAsState()
    // 검색어 상태
    val searchQuery by viewModel.searchQuery.collectAsState()
    // 로딩 상태
    val isLoading by viewModel.isLoading.collectAsState()
    
    // 로케일 선택 드롭다운 상태
    var showLocaleDropdown by remember { mutableStateOf(false) }
    
    // 코루틴 스코프
    val coroutineScope = rememberCoroutineScope()
    
    // 키보드 컨트롤러
    val keyboardController = LocalSoftwareKeyboardController.current
    
    // 바텀시트 상태
    val bottomSheetState = rememberStandardBottomSheetState(
        initialValue = SheetValue.Hidden,
        skipHiddenState = false
    )
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = bottomSheetState)

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            PoiSearchBottomSheet(
                viewModel = viewModel,
                sheetState = bottomSheetState,
                currentLocale = currentLocale
            )
        },
        sheetPeekHeight = 0.dp,
        sheetContainerColor = MaterialTheme.colorScheme.surface,
        // 시스템 내비게이션 바에 대한 WindowInsets 처리
        modifier = Modifier.safeDrawingPadding(),
        topBar = {
            TopAppBar(
                modifier = Modifier.background(Color(0xFFFFFFFF)),
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // 상단 컨트롤 영역
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 다크 모드 스위치
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (currentLocale) {
                            "en" -> "Dark Mode"
                            "ja" -> "ダークモード"
                            else -> "다크 모드"
                        }
                    )
                    Switch(
                        checked = isDarkMode,
                        onCheckedChange = { viewModel.toggleDarkMode() }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                // 언어 선택 드롭다운
                Box {
                    IconButton(onClick = { showLocaleDropdown = true }) {
                        Icon(
                            imageVector = Icons.Default.Build,
                            contentDescription = when (currentLocale) {
                                "en" -> "Language Settings"
                                "ja" -> "言語設定"
                                else -> "언어 설정"
                            }
                        )
                    }
                    
                    DropdownMenu(
                        expanded = showLocaleDropdown,
                        onDismissRequest = { showLocaleDropdown = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("한국어") },
                            onClick = {
                                viewModel.setLocale("ko")
                                showLocaleDropdown = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("English") },
                            onClick = {
                                viewModel.setLocale("en")
                                showLocaleDropdown = false
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("日本語") },
                            onClick = {
                                viewModel.setLocale("ja")
                                showLocaleDropdown = false
                            }
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(32.dp))
            
            // 검색 영역
            Text(
                text = when (currentLocale) {
                    "en" -> "Search for places"
                    "ja" -> "場所を検索"
                    else -> "장소 검색"
                },
                style = MaterialTheme.typography.titleLarge
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                modifier = Modifier
                    .fillMaxWidth(),
                label = { 
                    Text(
                        text = when (currentLocale) {
                            "en" -> "Search location"
                            "ja" -> "場所を検索"
                            else -> "장소 검색"
                        }
                    )
                },
                placeholder = {
                    Text(stringResource(id = R.string.search_hint))
                },
                trailingIcon = {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .size(16.dp)
                        )
                    }
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = androidx.compose.ui.text.input.ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        keyboardController?.hide()
                        coroutineScope.launch {
                            val hasResults = viewModel.searchPoi()
                            if (hasResults) {
                                bottomSheetState.expand()
                            }
                        }
                    }
                )
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // 검색 결과 설명
            Text(
                text = when (currentLocale) {
                    "en" -> "Search for places to see results"
                    "ja" -> "結果を表示するには場所を検索してください"
                    else -> "결과를 보려면 장소를 검색하세요"
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}