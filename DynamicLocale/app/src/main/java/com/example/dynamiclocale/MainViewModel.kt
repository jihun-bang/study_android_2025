package com.example.dynamiclocale

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.dynamiclocale.datasources.NaverPoiStub
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Locale

/**
 * 메인 화면의 ViewModel
 * 
 * @param application 애플리케이션 컨텍스트
 */
class MainViewModel(application: Application) : AndroidViewModel(application) {
    
    // 현재 로케일 상태
    private val _currentLocale = MutableStateFlow(Locale.getDefault().language)
    val currentLocale: StateFlow<String> = _currentLocale.asStateFlow()
    
    // 다크 모드 상태
    private val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()
    
    // 검색어 상태
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    
    // 검색 결과 상태
    private val _searchResults = MutableStateFlow<List<NaverPoiStub.Poi>>(emptyList())
    val searchResults: StateFlow<List<NaverPoiStub.Poi>> = _searchResults.asStateFlow()
    
    // 로딩 상태
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    /**
     * 로케일을 변경합니다.
     * 
     * @param locale 변경할 로케일 (ko, en, ja)
     */
    fun setLocale(locale: String) {
        _currentLocale.value = locale
        LocaleHelper.setOverrideLocale(getApplication(), Locale(locale))
    }
    
    /**
     * 다크 모드를 토글합니다.
     */
    fun toggleDarkMode() {
        _isDarkMode.value = !_isDarkMode.value
    }
    
    /**
     * 검색어를 업데이트합니다.
     * 
     * @param query 검색어
     */
    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }
    
    /**
     * 키워드로 POI를 검색합니다.
     * 검색이 완료된 후에 결과가 있는지 여부를 반환합니다.
     * 
     * @return 검색 결과가 있으면 true, 없으면 false
     */
    suspend fun searchPoi(): Boolean {
        val query = _searchQuery.value
        if (query.isNotEmpty() && !_isLoading.value) {
            _isLoading.value = true
            try {
                // async를 사용하여 IO 스레드에서 검색 수행
                val deferredResults = viewModelScope.async(Dispatchers.IO) {
                    NaverPoiStub.search(query, _currentLocale.value)
                }
                
                // await를 사용하여 결과 기다리기
                val results = deferredResults.await()
                
                // 최대 10개 결과만 표시
                val limitedResults = results.take(10)
                _searchResults.value = limitedResults
                
                // 결과가 있는지 여부 반환
                return limitedResults.isNotEmpty()
            } finally {
                _isLoading.value = false
            }
        }
        return false
    }
}
