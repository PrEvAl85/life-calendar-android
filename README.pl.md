# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

Aplikacja **Life Calendar** na Androida pokazuje całe Twoje życie jako siatkę tygodni — od daty urodzenia aż do końca przewidywanej długości życia. Wygodny przegląd po dekadach i latach, dziennik wpisów, wydarzenia i statystyki. Wszystko jest przechowywane lokalnie na Twoim urządzeniu.

Opiera się na pomyśle [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) z WaitButWhy.

## Funkcje

### Zakładki

Aplikacja składa się z 5 zakładek (dolna nawigacja):

- **Dziś** — ekran bieżącego tygodnia: wpisy i wydarzenia, nawigacja między sąsiednimi tygodniami (← →), przycisk „Dziś".
- **Mapa** — przegląd całego Twojego życia: przełącznik **„10 lat | Rok"**.
- **Dziennik** — wszystkie wpisy jako jedna lista: od najnowszych, wyszukiwanie po tekście, tworzenie/edycja/usuwanie.
- **Wydarzenia** — lista wydarzeń z kolorami; **„Urodziny" są przypięte na górze** (automatycznie, z daty urodzenia w ustawieniach).
- **Profil** — statystyki życia + wszystkie ustawienia + kopia zapasowa na jednym ekranie.

### Mapa: tryb „10 lat"

- Karty dekad zaczynające się od roku urodzenia: „1985–1994", „1995–2004", … (ostatnia jest niepełna).
- Każda karta ma **4 wiersze ogłoszeń**: najpierw wydarzenia (kolorowa kropka + data + nazwa), potem wpisy (data + tekst). Jeśli ogłoszeń jest więcej — „+N więcej…".
- **Twoje urodziny pojawiają się automatycznie w wydarzeniach** roku urodzenia (na zielono, bez wpisu w bazie danych).
- Dotknij dekady → karty lat wewnątrz niej. Każda karta: **„Rok — wiek"** z poprawnymi formami wyrazów („1991 — 6 lat", „2026 — 41 lat", rok urodzenia — „1985 — narodziny", przyszłość — „2031 — 46 lat · przyszłość").
- Dotknij roku → sekcja „Rok" dla tego roku.

### Mapa: tryb „Rok"

- Karty wszystkich tygodni wybranego roku, pogrupowane po miesiącach z **przyklejonymi nagłówkami „Styczeń"…„Grudzień"**.
- Każda karta pokazuje: daty tygodnia, wydarzenia jako kolorowe kropki, podglądy wpisów, podświetlenie „dziś".
- Dotknij tygodnia → panel szczegółów.
- Dotknij „Rok 2026" → szybki wybór dowolnego roku od urodzenia do bieżącego.

### Panel tygodnia (BottomSheet)

Dotknięcie przeżytego tygodnia na mapie/w roku otwiera panel: daty tygodnia, przycisk **„Otwórz tydzień"** (pełny ekran), **„Dodaj wpis"** oraz sekcje „Wydarzenia" i „Wpisy" (dotknij wpis, aby go edytować, ikona kosza, aby usunąć).

### Przyszłość jest niedostępna

- Dotknięcia przyszłych tygodni są ignorowane; w trybie „Rok" są przyciemnione i oznaczone jako „Przyszłość".
- Przycisk dodawania wpisu jest ukryty na przyszłych tygodniach; wybieranie przyszłych dat w kalendarzu wpisów jest zabronione.

### Różne

- **Onboarding przy pierwszym uruchomieniu**: prosi o datę urodzenia, zanim będziesz mógł zacząć.
- **Kopia zapasowa**: eksport wszystkich danych do JSON (i ich import z powrotem) przez systemowe okno zapisu/otwierania plików.
- **Ciemny i jasny motyw** — podążają za systemem.
- **Wielojęzyczność**: język aplikacji podąża za regionem urządzenia (17 tłumaczeń plus angielski) i można go zmienić ręcznie w Profil → Język.
- Wszystkie dane są tylko lokalne (Room + DataStore), nie jest używany internet.

## Pierwsze kroki

### Pierwsze uruchomienie

1. Zainstaluj APK (patrz „Instalacja" poniżej).
2. Aplikacja poprosi Cię o **datę urodzenia** — ten krok jest obowiązkowy. Wszystkie obliczenia są na niej oparte: dekady, wiek na kartach lat, statystyki.
3. Możesz ją zmienić w dowolnym momencie w **Profil → Data urodzenia**.

### Codzienne użytkowanie

- **Zrób wpis na dziś** — trzy sposoby:
  - zakładka **Dziś** → przycisk „+" (FAB);
  - zakładka **Dziennik** → przycisk „+" (FAB);
  - **Mapa** → dotknij potrzebnego tygodnia → „Dodaj wpis".
- **Znajdź stary wpis** — zakładka **Dziennik**, wyszukiwanie po tekście.
- **Dodaj wydarzenie** (urodziny znajomego, rocznica itp.) — zakładka **Wydarzenia** → przycisk „+" → nazwa, kolor, data.
- **Zobacz, co było 10 lat temu** — **Mapa** → tryb „10 lat" → dotknij dekady „1995–2004" → dotknij potrzebnego roku → sekcja „Rok" dla tego roku, albo od razu spójrz na ogłoszenia na karcie roku.
- **Oszacuj swoje postępy życiowe** — zakładka **Profil**: wiek (lata/tygodnie), procent przeżytego życia, pozostałe tygodnie, liczba wpisów i wydarzeń.
- **Przenieś dane na inne urządzenie** — **Profil → Kopia zapasowa**: eksport JSON → wyślij plik → import JSON na nowym urządzeniu.

### Co oznaczają kolory i etykiety

- Zielona kropka — „Urodziny" (zawsze wirtualne, nie można ich usunąć).
- Kropki w innych kolorach — Twoje wydarzenia; kolor wybierasz przy tworzeniu.
- Wiersze bez kropki w kartach „10 lat" — wpisy dziennika.
- „+N więcej…" — nie wszystko mieści się na karcie, otwórz kartę roku/dekady.
- „· przyszłość" na karcie roku — rok jeszcze nie nadszedł; jego ogłoszenia są puste do tego czasu.

## Instalacja

1. Pobierz plik `life-calendar-vX.Y.Z.apk` z [Releases](https://github.com/PrEvAl85/life-calendar-android/releases).
2. Otwórz plik na urządzeniu i potwierdź instalację z nieznanych źródeł (system Cię o to poprosi).

### Play Protect i niepodpisany APK

APK nie jest podpisany przez Google Play i nie przeszedł weryfikacji Google Play Protect — przy pierwszej instalacji Android może pokazać ostrzeżenie „Play Protect zablokował aplikację" lub poprosić o potwierdzenie instalacji. Plik jest bezpieczny: to kompilacja kodu open-source tego repozytorium.

- Jak zainstalować: w oknie Play Protect wybierz **„Więcej szczegółów" → „Zainstaluj mimo to"** (jednorazowo), albo w ustawieniach Androida: **Zabezpieczenia → Instalacja nieznanych aplikacji → zezwól** dla Twojego menedżera plików/przeglądarki.
- Pobieraj APK tylko z sekcji **Releases** tego repozytorium.

## Budowanie ze źródeł

Wymagania: JDK 17, Android SDK (compileSdk 35), Gradle 8.10.2 (repozytorium zawiera wrapper).

```
gradlew assembleRelease
```

Podpisany APK pojawi się w `app/build/outputs/apk/release/app-release.apk`.

Uwaga: jeśli ścieżka projektu zawiera znaki spoza ASCII, Gradle może odmówić budowania (`StopExecutionException: ... non-ASCII characters`). Flaga `android.overridePathCheck=true` jest już dodana w `gradle.properties`, co rozwiązuje ten problem.

## Struktura projektu

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — punkt wejścia, obsługa nieprzechwyconych wyjątków (log w `filesDir/crash.log`).
  - `ui/AppNav.kt` — nawigacja i dolny pasek z 5 zakładkami (Dziś · Mapa · Dziennik · Wydarzenia · Profil).
  - `ui/grid/` — zakładka „Mapa":
    - `DecadeOverviewScreen.kt` — karty dekad i lat z ogłoszeniami i wiekiem;
    - `YearOverviewScreen.kt` — przegląd roczny po miesiącach z `stickyHeader`;
    - `YearPickerSheet.kt` — szybki wybór roku;
    - `WeekDetailSheet.kt` — panel tygodnia (wydarzenia/wpisy/otwórz tydzień);
    - `WeekGridViewModel.kt` — stan: tygodnie, wydarzenia, wpisy, ustawienia.
  - `ui/entries/` — zakładka „Dziennik": wszystkie wpisy, wyszukiwanie, CRUD.
  - `ui/events/` — zakładka „Wydarzenia": lista z kolorami, przypięte „Urodziny".
  - `ui/profile/` — zakładka „Profil": statystyki + ustawienia + kopia zapasowa.
  - `ui/onboarding/` — pierwsze uruchomienie: pytanie o datę urodzenia.
  - `ui/week/` — ekran tygodnia (zakładka „Dziś" i nawigacja z mapy).
  - `ui/common/` — wspólne okno wpisu, okno wyboru daty, wspólne kolory (np. zielony dla „Urodzin").
  - `util/LanguageManager.kt` — obsługa języka aplikacji (region urządzenia + ręczny wybór w Profilu).
  - `data/` — Room (encje, DAO, baza danych), DataStore (ustawienia), `BackupManager.kt` (kopia zapasowa JSON).
  - `util/Dates.kt` — obliczenia dat i tygodni.

## Wsparcie projektu

Life Calendar jest tworzony i utrzymywany w wolnym czasie; aplikacja jest darmowa i bez reklam. Jeśli jest dla Ciebie przydatna — pomóż w jej rozwoju:

- ⭐ **Daj gwiazdkę na GitHub** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **Zgłoszenia błędów i pomysły** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **Powiedz innym** — podziel się z tymi, którym może się przydać

**Wsparcie finansowe:**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**Kryptowaluty:**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

Dziękujemy za korzystanie z Life Calendar!

## Licencja

MIT. Szczegóły w `LICENSE`.
