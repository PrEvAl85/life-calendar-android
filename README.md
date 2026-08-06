# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

The **Life Calendar** Android app shows your whole life as a grid of weeks — from your date of birth to the end of your expected lifespan. Convenient overview by decades and years, a journal of entries, events, and statistics. Everything is stored locally on your device.

Based on the idea of [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) from WaitButWhy.

## Features

### Tabs

The app consists of 5 tabs (bottom navigation):

- **Today** — the current week screen: entries and events, navigation between neighboring weeks (← →), the "Today" button.
- **Map** — an overview of your whole life: the **"10 years | Year"** switcher.
- **Journal** — all entries as a single list: newest first, search by text, create/edit/delete.
- **Events** — a list of events with colors; **"Birthday" is pinned at the top** (automatically, from the birth date in settings).
- **Profile** — life statistics + all settings + backup in one screen.

### Map: "10 years" mode

- Decade cards starting from the birth year: "1985–1994", "1995–2004", … (the last one is partial).
- Each card has **4 announcement rows**: events first (colored dot + date + name), then entries (date + text). If there are more announcements — "+N more…".
- **Your birthday automatically appears in the events** of the birth year (in green, without a database entry).
- Tap a decade → year cards inside it. Each card: **"Year — age"** with correct word forms ("1991 — 6 years", "2026 — 41 years", the birth year — "1985 — birth", future — "2031 — 46 years · future").
- Tap a year → the "Year" section for that year.

### Map: "Year" mode

- Cards for all weeks of the selected year, grouped by months with **sticky "January"…"December" headers**.
- Each card shows: week dates, events as colored dots, entry previews, the "today" highlight.
- Tap a week → a detail panel.
- Tap "Year 2026" → quick pick any year from birth to the current one.

### Week panel (BottomSheet)

Tapping a lived week on the map/in the year opens a panel: week dates, the **"Open week"** button (full screen), **"Add entry"**, and the "Events" and "Entries" sections (tap an entry to edit, trash icon to delete).

### The future is unavailable

- Taps on future weeks are ignored; in "Year" mode they are dimmed and marked "Future".
- The add-entry button is hidden on future weeks; picking future dates in the entry calendar is forbidden.

### Misc

- **First-launch onboarding**: asks for the birth date before you can start.
- **Backup**: export all data to JSON (and import it back) through the system file save/open dialog.
- **Dark and light themes** — follow the system.
- **Multilingual**: the app language follows the device region (17 translations plus English) and can be changed manually in Profile → Language.
- All data is local only (Room + DataStore), no internet is used.

## Getting started

### First launch

1. Install the APK (see "Installation" below).
2. The app will ask you for the **date of birth** — this step is mandatory. All calculations are based on it: decades, ages on the year cards, statistics.
3. You can change it at any time in **Profile → Date of birth**.

### Everyday use

- **Make an entry for today** — three ways:
  - the **Today** tab → the "+" button (FAB);
  - the **Journal** tab → the "+" button (FAB);
  - **Map** → tap the needed week → "Add entry".
- **Find an old entry** — the **Journal** tab, search by text.
- **Add an event** (a friend's birthday, an anniversary, etc.) — the **Events** tab → the "+" button → name, color, date.
- **See what happened 10 years ago** — **Map** → "10 years" mode → tap the "1995–2004" decade → tap the needed year → the "Year" section for that year, or look at the announcements on the year card right away.
- **Estimate your life progress** — the **Profile** tab: age (years/weeks), percentage of life lived, weeks remaining, the number of entries and events.
- **Move data to another device** — **Profile → Backup**: export JSON → send the file → import JSON on the new device.

### What the colors and labels mean

- Green dot — "Birthday" (always virtual, cannot be deleted).
- Dots of other colors — your events; the color is chosen when creating.
- Rows without a dot in the "10 years" cards — journal entries.
- "+N more…" — not everything fits in the card, open the year/decade card.
- "· future" on a year card — the year has not come yet; its announcements are empty until then.

## Installation

1. Download the `life-calendar-vX.Y.Z.apk` file from [Releases](https://github.com/PrEvAl85/life-calendar-android/releases).
2. Open the file on your device and confirm the installation from unknown sources (the system will prompt you).

### Play Protect and the unsigned APK

The APK is not signed by Google Play and has not passed Google Play Protect verification — on the first install Android may show a "Play Protect blocked the app" warning or ask you to confirm the installation. The file is safe: it is a build of the open-source code of this repository.

- How to install: in the Play Protect dialog choose **"More details" → "Install anyway"** (once), or in Android settings: **Security → Unknown app installation → allow** for your file manager/browser.
- Download the APK only from the **Releases** section of this repository.

## Building from source

Requirements: JDK 17, Android SDK (compileSdk 35), Gradle 8.10.2 (the repository includes the wrapper).

```
gradlew assembleRelease
```

The signed APK will appear at `app/build/outputs/apk/release/app-release.apk`.

Note: if the project path contains non-ASCII characters, Gradle may refuse to build (`StopExecutionException: ... non-ASCII characters`). The `android.overridePathCheck=true` flag is already added in `gradle.properties`, which solves this problem.

## Project structure

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — entry point, handler for uncaught exceptions (log at `filesDir/crash.log`).
  - `ui/AppNav.kt` — navigation and the bottom bar with 5 tabs (Today · Map · Journal · Events · Profile).
  - `ui/grid/` — the "Map" tab:
    - `DecadeOverviewScreen.kt` — decade and year cards with announcements and ages;
    - `YearOverviewScreen.kt` — the yearly overview by months with a `stickyHeader`;
    - `YearPickerSheet.kt` — quick year picker;
    - `WeekDetailSheet.kt` — the week panel (events/entries/open week);
    - `WeekGridViewModel.kt` — state: weeks, events, entries, settings.
  - `ui/entries/` — the "Journal" tab: all entries, search, CRUD.
  - `ui/events/` — the "Events" tab: a list with colors, the pinned "Birthday".
  - `ui/profile/` — the "Profile" tab: statistics + settings + backup.
  - `ui/onboarding/` — first launch: asking for the birth date.
  - `ui/week/` — the week screen (the "Today" tab and navigation from the map).
  - `ui/common/` — shared entry dialog, date picker dialog, common colors (e.g. green for "Birthday").
  - `util/LanguageManager.kt` — app language handling (device region + manual choice in Profile).
  - `data/` — Room (entities, DAO, database), DataStore (settings), `BackupManager.kt` (JSON backup).
  - `util/Dates.kt` — date and week math.

## Support the Project

Life Calendar is created and maintained in free time; the app is free and ad-free. If it is useful to you — help its development:

- ⭐ **Star on GitHub** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **Bug reports and ideas** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **Tell others** — share with those who might find it useful

**Financial support:**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**Cryptocurrency:**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

Thank you for using Life Calendar!

## License

MIT. Details in `LICENSE`.
