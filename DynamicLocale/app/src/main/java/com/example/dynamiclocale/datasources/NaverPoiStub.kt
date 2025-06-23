package com.example.dynamiclocale.datasources

import kotlinx.coroutines.delay
import kotlin.random.Random

/**
 * 네이버 POI Stub API
 * 실제 API 대신 사용하는 더미 데이터 제공 클래스
 */
object NaverPoiStub {
    
    /**
     * POI 데이터 클래스
     * 
     * @param id 고유 ID
     * @param name 장소 이름
     * @param address 주소
     * @param distance 거리 (미터 단위)
     */
    data class Poi(
        val id: String,
        val name: String,
        val address: String,
        val distance: Int
    )
    
    // 한국어 POI 데이터
    private val koPoiList = listOf(
        Poi("1", "강남역", "서울특별시 강남구 강남대로 396", 120),
        Poi("2", "강남 CGV", "서울특별시 강남구 강남대로 438", 350),
        Poi("3", "스타벅스 강남점", "서울특별시 강남구 테헤란로 101", 480),
        Poi("4", "강남 파이낸스센터", "서울특별시 강남구 테헤란로 152", 650),
        Poi("5", "신논현역", "서울특별시 강남구 강남대로 246", 780),
        Poi("6", "교보문고 강남점", "서울특별시 강남구 강남대로 465", 820),
        Poi("7", "강남 세브란스병원", "서울특별시 강남구 언주로 211", 950),
        Poi("8", "메가박스 강남", "서울특별시 강남구 역삼로 180", 1100),
        Poi("9", "강남 우체국", "서울특별시 강남구 강남대로 320", 1250),
        Poi("10", "강남구청", "서울특별시 강남구 학동로 426", 1400)
    )
    
    // 영어 POI 데이터
    private val enPoiList = listOf(
        Poi("1", "Gangnam Station", "396 Gangnam-daero, Gangnam-gu, Seoul", 120),
        Poi("2", "CGV Gangnam", "438 Gangnam-daero, Gangnam-gu, Seoul", 350),
        Poi("3", "Starbucks Gangnam", "101 Teheran-ro, Gangnam-gu, Seoul", 480),
        Poi("4", "Gangnam Finance Center", "152 Teheran-ro, Gangnam-gu, Seoul", 650),
        Poi("5", "Sinnonhyeon Station", "246 Gangnam-daero, Gangnam-gu, Seoul", 780),
        Poi("6", "Kyobo Book Centre Gangnam", "465 Gangnam-daero, Gangnam-gu, Seoul", 820),
        Poi("7", "Gangnam Severance Hospital", "211 Eonju-ro, Gangnam-gu, Seoul", 950),
        Poi("8", "Megabox Gangnam", "180 Yeoksam-ro, Gangnam-gu, Seoul", 1100),
        Poi("9", "Gangnam Post Office", "320 Gangnam-daero, Gangnam-gu, Seoul", 1250),
        Poi("10", "Gangnam-gu Office", "426 Hakdong-ro, Gangnam-gu, Seoul", 1400)
    )
    
    // 일본어 POI 데이터
    private val jaPoiList = listOf(
        Poi("1", "江南駅", "ソウル特別市江南区江南大路396", 120),
        Poi("2", "江南CGV", "ソウル特別市江南区江南大路438", 350),
        Poi("3", "スターバックス江南店", "ソウル特別市江南区テヘラン路101", 480),
        Poi("4", "江南ファイナンスセンター", "ソウル特別市江南区テヘラン路152", 650),
        Poi("5", "新論峴駅", "ソウル特別市江南区江南大路246", 780),
        Poi("6", "教保文庫江南店", "ソウル特別市江南区江南大路465", 820),
        Poi("7", "江南セブランス病院", "ソウル特別市江南区言住路211", 950),
        Poi("8", "メガボックス江南", "ソウル特別市江南区駅三路180", 1100),
        Poi("9", "江南郵便局", "ソウル特別市江南区江南大路320", 1250),
        Poi("10", "江南区庁", "ソウル特別市江南区学洞路426", 1400)
    )
    
    /**
     * 키워드로 POI를 검색합니다.
     * 
     * @param keyword 검색 키워드
     * @param locale 현재 로케일 (ko, en, ja)
     * @return POI 목록
     */
    suspend fun search(keyword: String, locale: String = "ko"): List<Poi> {
        // API 호출 시뮬레이션을 위한 딜레이 (0.5~2초)
        delay(Random.nextLong(500, 2000))
        
        // 로케일에 따라 다른 POI 목록 반환
        return when (locale) {
            "en" -> enPoiList.filter { it.toString().contains(keyword, ignoreCase = true) }
            "ja" -> jaPoiList.filter { it.toString().contains(keyword, ignoreCase = true) }
            else -> koPoiList.filter { it.toString().contains(keyword, ignoreCase = true) }
        }
    }
}
