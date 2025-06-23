package com.example.dynamiclocale.ui.sheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.dynamiclocale.MainViewModel
import com.example.dynamiclocale.datasources.NaverPoiStub
import kotlinx.coroutines.launch

/**
 * POI 검색 결과를 표시하는 바텀시트 컴포넌트
 * 
 * @param sheetState 바텀시트 상태
 * @param currentLocale 현재 로케일
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PoiSearchBottomSheet(
    viewModel: MainViewModel,
    sheetState: SheetState,
    currentLocale: String
) {
    // 검색어 상태 저장
    val searchQuery by viewModel.searchQuery.collectAsState()
    // 검색 결과 상태 저장
    val searchResults by viewModel.searchResults.collectAsState()
    // 로딩 상태
    val isLoading by viewModel.isLoading.collectAsState()

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .safeDrawingPadding()
    ) {
        // 검색창
        OutlinedTextField(
            value = searchQuery,
            onValueChange = {
                viewModel.updateSearchQuery(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .semantics {
                    contentDescription = when (currentLocale) {
                        "en" -> "Search location"
                        "ja" -> "場所を検索"
                        else -> "장소 검색"
                    }
                },
            label = {
                Text(
                    text = when (currentLocale) {
                        "en" -> "Search location"
                        "ja" -> "場所を検索"
                        else -> "장소 검색"
                    }
                )
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
                    coroutineScope.launch {
                        val hasResults = viewModel.searchPoi()
                        if (hasResults) {
                            sheetState.expand()
                        }
                    }
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 검색 결과 목록
        LazyColumn {
            items(searchResults) { poi ->
                PoiCard(poi = poi, currentLocale = currentLocale)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

/**
 * POI 정보를 표시하는 카드 컴포넌트
 * 
 * @param poi POI 데이터
 * @param currentLocale 현재 로케일
 */
@Composable
fun PoiCard(
    poi: NaverPoiStub.Poi,
    currentLocale: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // POI 이름
            Text(
                text = poi.name,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            // POI 주소
            Text(
                text = poi.address,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Text(
                text = poi.distance.toString(),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
