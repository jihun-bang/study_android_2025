# WebSocket 실시간 마커
> > 목표: Mock WebSocket 서버에서 주기적으로 송신되는 여러 드라이버의 GPS 좌표를 받아 지도에 실시간으로 표시하고, MVVM + Clean Architecture 패턴으로 구조화된 Android 앱을 1 시간 이내에 완성하십시오.
>

---

### 1. 제공 환경 및 리소스

| 항목 | 설명 |
| --- | --- |
| **Starter Project** | `app` 모듈과 `core`, `data`, `domain`, `presentation` 서브모듈이 분리된 Clean Architecture 템플릿. |
| **Mock WebSocket Server** | `ws://10.0.2.2:8080/driver`JSON Payload 예시: `{"id":"taxi-42","lat":37.5665,"lng":126.9780}` (초당 1 회, 최대 5 대). |
| **Map SDK** | Google Maps Compose(1.3.+) 의존성 포함. `MapView` 초기화 예제 제공. |
| **Unit-Test Skeleton** | `DriverRepositoryTest.kt` 파일, Fake WebSocket Client 및 예상 결과 Stub 포함. |

---

### 2. 필수 구현 과제

| 단계 | 작업 내용 |
| --- | --- |
| **2-1. Data Layer** | `DriverRepository` 에 WebSocket 스트림 접속 로직을 구현하고, **Cold Flow<List<DriverPosition>>** 를 반환하십시오. |
| **2-2. Domain Layer** | `ObserveDriversUseCase` 를 생성해 Repository Flow 를 그대로 노출하되, 동일 ID 의 중복 좌표가 연속 수신될 경우 필터링합니다. |
| **2-3. Presentation Layer** | `DriverViewModel`—UseCase 를 구독해 **UIState**(최대 5개 마커 목록) 를 1 초 주기로 갱신`MainScreen`—`GoogleMap` 위에 마커를 Compose `Marker` 로 그립니다. |
| **2-4. 리소스 제어** | 첫 화면 진입 시 **앱 전체 메모리 사용량이 2 MB 이하**(Android Profiler Java Heap 기준)가 되도록 Bitmap 재사용·가비지 최소화를 적용하십시오. |

---

### 3. 동작 / 성능 요구 사항

| 항목 | 기준 |
| --- | --- |
| **실시간성** | 지도상의 마커 좌표가 서버 좌표에 대해 **1 초 이내** 오차 없이 업데이트될 것 |
| **UI 프레임 레이트** | 지도 확대/축소 중에도 **FPS ≥ 55** 유지 |
| **지연 복구** | 네트워크 Reconnect 발생 시 **3 초** 안에 스트림 자동 재연결 |
| **단위 테스트** | 제공된 3개 테스트(FakeRepository 주입) **모두 통과** |

---

### 4. 평가 포인트

1. **아키텍처 적합성** – Domain·Data·Presentation 레이어 분리와 의존성 방향성
2. **테스트 설계** – WebSocket 의존성을 Fake 로 대체한 검증 로직
3. **메모리 튜닝** – 메모리 릴리스·Bitmap Pool·Immutable State 관리
4. **코루틴 스케줄링** – Flow 연산 및 Dispatchers 선택 이유
5. **코드 가독성·네이밍** – 협업 관점의 읽기 쉬운 코드

---

### 5. 참고 가이드

- **WebSocket** : OkHttp 5.x `okhttp-ws` 모듈, Ping-Pong 자동 Keep-Alive 지원.
- **지도 마커 최적화** : `MarkerState(position)` 대신 `remember { mutableStateOf(...) }` 활용하여 불필요한 Recompose 방지.
- **Profiler Tip** : Allocation Tracker → Live Allocation 탭에서 Bitmap 객체 수를 확인해 10개 이하 유지.

> 주의: 제출 규칙(브랜치, README 등)은 평가 범위에 포함되지 않습니다.
>