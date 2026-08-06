# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

**Life Calendar** Android 앱은 당신의 인생 전체를 주(週) 그리드로 보여줍니다——생년월일부터 기대 수명의 끝까지. 십 년과 연 단위의 편리한 개요, 일기 항목, 이벤트, 통계. 모든 데이터는 기기에 로컬로 저장됩니다.

WaitButWhy의 [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) 아이디어를 기반으로 합니다.

## 기능

### 탭

앱은 5개의 탭(하단 내비게이션)으로 구성되어 있습니다:

- **오늘(Today)** — 현재 주 화면: 항목과 이벤트, 인접한 주 사이의 이동(← →), "오늘" 버튼.
- **지도(Map)** — 인생 전체의 개요: **"10 years | Year"** 전환.
- **일지(Journal)** — 모든 항목이 하나의 목록으로: 최신순, 텍스트 검색, 생성/편집/삭제.
- **이벤트(Events)** — 색상이 있는 이벤트 목록; **"생일(Birthday)"이 맨 위에 고정**됩니다(설정의 생년월일에서 자동으로).
- **프로필(Profile)** — 인생 통계 + 모든 설정 + 백업이 한 화면에.

### 지도: "10 years" 모드

- 출생 연도부터 시작하는 십 년 카드: "1985–1994", "1995–2004", ……(마지막은 부분적).
- 각 카드에는 **4줄의 알림**이 있습니다: 먼저 이벤트(색 점 + 날짜 + 이름), 다음으로 항목(날짜 + 텍스트). 더 있으면 "+N more…".
- **생일은 출생 연도의 이벤트에 자동으로 나타납니다**(녹색, 데이터베이스 항목 없이).
- 십 년을 탭하면 → 그 안의 연도 카드. 각 카드: **"Year — age"** 올바른 단어 형태와 함께("1991 — 6 years", "2026 — 41 years", 출생 연도는 "1985 — birth", 미래는 "2031 — 46 years · future").
- 연도를 탭하면 → 해당 연도의 "Year" 섹션.

### 지도: "Year" 모드

- 선택한 연도의 모든 주의 카드, 월별로 그룹화되고 **고정된 "January"…"December" 헤더**가 있습니다.
- 각 카드 표시: 주 날짜, 이벤트의 색 점, 항목 미리보기, "오늘" 강조.
- 주를 탭하면 → 상세 패널.
- "Year 2026"을 탭하면 → 출생부터 현재까지의 임의 연도를 빠르게 선택.

### 주 패널(BottomSheet)

지도/연도에서 지나간 주를 탭하면 패널이 열립니다: 주 날짜, **"Open week"** 버튼(전체 화면), **"Add entry"**, 그리고 "Events"와 "Entries" 섹션(항목을 탭하면 편집, 휴지통 아이콘으로 삭제).

### 미래는 사용할 수 없습니다

- 미래 주를 탭하면 무시됩니다. "Year" 모드에서는 흐리게 표시되고 "Future"로 표시됩니다.
- 미래 주에는 항목 추가 버튼이 숨겨집니다. 항목 캘린더에서 미래 날짜를 선택하는 것은 금지됩니다.

### 기타

- **첫 실행 온보딩**: 시작하기 전에 생년월일을 묻습니다.
- **백업**: 시스템 파일 저장/열기 대화상자를 통해 모든 데이터를 JSON으로 내보냅니다(다시 가져오기도).
- **다크 및 라이트 테마** — 시스템을 따릅니다.
- **다국어**: 앱 언어는 기기 지역을 따릅니다(17개 번역 + 영어). 프로필 → 언어에서 수동으로 변경할 수 있습니다.
- 모든 데이터는 로컬 전용입니다(Room + DataStore), 인터넷은 사용하지 않습니다.

## 시작하기

### 첫 실행

1. APK를 설치합니다(아래 "설치" 참조).
2. 앱이 **생년월일**을 묻습니다——이 단계는 필수입니다. 모든 계산이 이를 기반으로 합니다: 십 년, 연도 카드의 나이, 통계.
3. 언제든지 **프로필 → 생년월일**에서 변경할 수 있습니다.

### 일상적인 사용

- **오늘 항목 만들기** — 세 가지 방법:
  - **오늘(Today)** 탭 → "+" 버튼(FAB);
  - **일지(Journal)** 탭 → "+" 버튼(FAB);
  - **지도(Map)** → 원하는 주를 탭 → "항목 추가".
- **오래된 항목 찾기** — **일지(Journal)** 탭, 텍스트 검색.
- **이벤트 추가**(친구 생일, 기념일 등) — **이벤트(Events)** 탭 → "+" 버튼 → 이름, 색상, 날짜.
- **10년 전에 무슨 일이 있었는지 보기** — **지도(Map)** → "10 years" 모드 → "1995–2004" 십 년을 탭 → 원하는 연도를 탭 → 해당 연도의 "Year" 섹션, 또는 연도 카드의 알림을 바로 확인.
- **인생 진행 상황 확인** — **프로필(Profile)** 탭: 나이(년/주), 살아온 인생의 비율, 남은 주 수, 항목과 이벤트의 개수.
- **데이터를 다른 기기로 옮기기** — **프로필 → 백업**: JSON 내보내기 → 파일 전송 → 새 기기에서 JSON 가져오기.

### 색상과 라벨의 의미

- 녹색 점 — "생일"(항상 가상이며 삭제할 수 없음).
- 다른 색의 점 — 당신의 이벤트; 색상은 만들 때 선택합니다.
- "10 years" 카드에서 점이 없는 줄 — 일기 항목.
- "+N more…" — 카드에 모두 들어가지 않습니다. 연도/십 년 카드를 열어보세요.
- 연도 카드의 "· future" — 그 해는 아직 오지 않았습니다. 그때까지 알림은 비어 있습니다.

## 설치

1. [Releases](https://github.com/PrEvAl85/life-calendar-android/releases)에서 `life-calendar-vX.Y.Z.apk` 파일을 다운로드합니다.
2. 기기에서 파일을 열고 알 수 없는 소스에서의 설치를 확인합니다(시스템이 묻습니다).

### Play Protect와 서명되지 않은 APK

이 APK는 Google Play에서 서명되지 않았고 Google Play Protect 검증을 통과하지 못했습니다——첫 설치 시 Android가 "Play Protect가 앱을 차단했습니다" 경고를 표시하거나 설치 확인을 요청할 수 있습니다. 파일은 안전합니다: 이 저장소의 오픈소스 코드를 빌드한 것입니다.

- 설치 방법: Play Protect 대화상자에서 **"자세히" → "그래도 설치"**를 선택(한 번), 또는 Android 설정에서: **보안 → 알 수 없는 앱 설치 → 허용**을 파일 관리자/브라우저에 대해 선택.
- APK는 이 저장소의 **Releases** 섹션에서만 다운로드하세요.

## 소스에서 빌드

요구 사항: JDK 17, Android SDK(compileSdk 35), Gradle 8.10.2(저장소에는 wrapper가 포함되어 있습니다).

```
gradlew assembleRelease
```

서명된 APK는 `app/build/outputs/apk/release/app-release.apk`에 생성됩니다.

참고: 프로젝트 경로에 비 ASCII 문자가 포함되어 있으면 Gradle이 빌드를 거부할 수 있습니다(`StopExecutionException: ... non-ASCII characters`). `android.overridePathCheck=true` 플래그가 `gradle.properties`에 이미 추가되어 있어 이 문제를 해결합니다.

## 프로젝트 구조

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — 진입점, 처리되지 않은 예외 핸들러(로그는 `filesDir/crash.log`).
  - `ui/AppNav.kt` — 내비게이션과 5개 탭이 있는 하단 바(Today · Map · Journal · Events · Profile).
  - `ui/grid/` — "Map" 탭:
    - `DecadeOverviewScreen.kt` — 알림과 나이가 있는 십 년 및 연도 카드;
    - `YearOverviewScreen.kt` — `stickyHeader`가 있는 월별 연간 개요;
    - `YearPickerSheet.kt` — 빠른 연도 선택;
    - `WeekDetailSheet.kt` — 주 패널(이벤트/항목/주 열기);
    - `WeekGridViewModel.kt` — 상태: 주, 이벤트, 항목, 설정.
  - `ui/entries/` — "Journal" 탭: 모든 항목, 검색, CRUD.
  - `ui/events/` — "Events" 탭: 색상이 있는 목록, 고정된 "Birthday".
  - `ui/profile/` — "Profile" 탭: 통계 + 설정 + 백업.
  - `ui/onboarding/` — 첫 실행: 생년월일 묻기.
  - `ui/week/` — 주 화면("Today" 탭과 지도에서의 내비게이션).
  - `ui/common/` — 공유 항목 대화상자, 날짜 선택 대화상자, 공통 색상(예: "Birthday"의 녹색).
  - `util/LanguageManager.kt` — 앱 언어 처리(기기 지역 + 프로필에서 수동 선택).
  - `data/` — Room(엔티티, DAO, 데이터베이스), DataStore(설정), `BackupManager.kt`(JSON 백업).
  - `util/Dates.kt` — 날짜와 주 계산.

## 프로젝트 지원

Life Calendar은 여가 시간에 만들어지고 유지 관리됩니다. 앱은 무료이고 광고도 없습니다. 유용하다면——개발을 도와주세요:

- ⭐ **GitHub에서 스타** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **버그 보고와 아이디어** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **다른 사람에게 알리기** — 유용할 만한 사람들과 공유

**금전적 지원:**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**암호화폐:**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

Life Calendar을 사용해 주셔서 감사합니다!

## 라이선스

MIT. 자세한 내용은 `LICENSE`를 참조하세요.
