# 다국어 BottomSheet & Dynamic Color

> 제한 시간 : 60 min | Tech Stack : Kotlin 1.9 · Android Studio Hedgehog · Jetpack Compose 1.6 · Material 3
>

---

## 0. 목표

Android 13 `LocaleManager`와 Material 3 Dynamic Color를 활용해 **재시작 없이** 앱의 표시 언어와 테마를 전환하고, 네이버 POI Stub API 결과를 `BottomSheetScaffold`에 그려 UX 일관성과 접근성을 검증한다.

---

## 1. 제공 리소스 (Starter)

app/

├─ MainActivity.kt          // NavHost, 시스템 UI 컨트롤러 초기화

├─ LocaleHelper.kt         // setOverrideLocale( ) stub

├─ datasources/

│   └─ NaverPoiStub.kt     // suspend fun search(keyword): List<Poi>

├─ ui/

│   ├─ sheet/BottomSheet.kt // 비어 있는 Composable

│   └─ theme/              // Material3 테마 seedColor = Indigo

└─ res/

├─ values/strings.xml  // ko-KR, en-US, ja-JP 3개 locale

└─ xml/locale_config.xml

---

## 2. 구현 요구사항

| # | 필수 / 가산 | 세부 내용 | 품질 Gate |
| --- | --- | --- | --- |
| 1 | **필수** | **Locale 전환 버튼**<br>  – `MainScreen` 상단 우측에 `IconButton`(🌐) 배치<br>  – 클릭 시 `DropdownMenu` (한국어·English·日本語)<br>  – 선택 즉시 `LocaleManager.setOverrideLocale()` 호출 후 **화면만 재구성** | 언어 변경 → 250 ms 내 Text 전환 |
| 2 | **필수** | **BottomSheetScaffold** 구현<br>  – 검색창(`OutlinedTextField`)에 키워드 입력 후 *Enter* → `search()` 호출<br>  – 최대 10개 POI → `LazyColumn` 카드 형태 (이름, 주소, 거리)<br>  – `rememberBottomSheetState(skipPartiallyExpanded = true)` | 스크롤 FPS ≥ 55 |
| 3 | **필수** | **Material You Dynamic Color**<br>  – `DynamicColorTheme` 활용, 라이트/다크 모두 지원<br>  – 다크 모드 토글 (`Switch`) 의도적으로 추가 | 컬러셋이 시스템 seed 변경 따라야 함 |
| 4 | **가산점** | **Preview-환경 빌드**<br>  – `@Preview(locale="ja")` 에서 일본어·다크 모드 미리보기 작성 | 2점 |
| 5 | **가산점** | **State Save/Restore**<br>  – 언어 변경 뒤에도 검색어·결과 유지(`rememberSaveable`) | 2점 |

---

## 3. 완료 기준 (Acceptance Criteria)

1. 언어 버튼 → 드롭다운 → 선택 직후 **Activity 재생성 없이** 모든 UI 문구가 선택 언어로 갱신된다.
2. `OutlinedTextField`에 “강남역” 입력 → *Enter* → 3 초 이내 BottomSheet에 한국어 결과 10개가 리스트업된다.
3. 다크 모드 `Switch` 토글 시 **컬러 팔레트가 즉시 반전**되고, 컴포넌트 색상이 Material You 규칙을 따른다.
4. 에뮬레이터 메모리 스냅샷(`adb shell dumpsys meminfo`)에서 **최초 launch RSS ≤ 75 MB**.
5. `MainActivity` rotation 또는 프로세스 recreate 후에도 **현재 언어·모드·검색 상태가 복원**된다. (가산 항목)

---

## 4. 평가 포인트

| 항목 | 배점 | 체크리스트 |
| --- | --- | --- |
| 기능 완성도 | 40 | 요구사항 1-3 충족 여부 |
| Compose 상태 관리 | 20 | `remember`·`Flow` 사용, recomposition 최소화 |
| 아키텍처 적절성 | 15 | ViewModel 레이어 분리, `LocaleHelper` encapsulation |
| UX & 접근성 | 15 | TalkBack label, `contentDescription`, 큰 글꼴 대응 |
| 코드 품질 | 10 | KDoc, naming, Modular commit (Conventional Commits) |

> Tip : stringResource(id) 대신 pluralStringResource·formatArgs를 활용하면 언어별 어순 차이를 줄일 수 있습니다.
>

---

## 5. 제한 & 참고

- Android 12- 이하 디바이스에서는 `AppCompatDelegate.setApplicationLocales()`로 fallback 가능합니다.
- 네트워크 지연 환경을 고려해 `search()` 타임아웃을 2 s로 설정하세요.
- 테스트 중 **Stub API**는 100 req/min 제한이 있으니 Debounce 처리를 권장합니다.

---