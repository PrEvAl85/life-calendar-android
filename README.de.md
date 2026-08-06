# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

Die Android-App **Life Calendar** zeigt dein ganzes Leben als Wochenraster — von deinem Geburtsdatum bis zum Ende deiner erwarteten Lebensspanne. Praktische Übersicht nach Jahrzehnten und Jahren, ein Tagebuch mit Einträgen, Ereignissen und Statistiken. Alles wird lokal auf deinem Gerät gespeichert.

Basiert auf der Idee von [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) von WaitButWhy.

## Funktionen

### Tabs

Die App besteht aus 5 Tabs (untere Navigation):

- **Heute** — der Bildschirm mit der aktuellen Woche: Einträge und Ereignisse, Navigation zwischen benachbarten Wochen (← →), der Button „Heute“.
- **Karte** — eine Übersicht über dein ganzes Leben: der Umschalter **„10 Jahre | Jahr“**.
- **Tagebuch** — alle Einträge als eine einzige Liste: neueste zuerst, Textsuche, Erstellen/Bearbeiten/Löschen.
- **Ereignisse** — eine Liste der Ereignisse mit Farben; **„Geburtstag“ ist oben angeheftet** (automatisch, anhand des Geburtsdatums in den Einstellungen).
- **Profil** — Lebensstatistiken + alle Einstellungen + Backup in einem Bildschirm.

### Karte: Modus „10 Jahre“

- Jahrzehnt-Karten ab dem Geburtsjahr: „1985–1994“, „1995–2004“, … (die letzte ist unvollständig).
- Jede Karte hat **4 Ankündigungszeilen**: zuerst Ereignisse (farbiger Punkt + Datum + Name), dann Einträge (Datum + Text). Gibt es mehr Ankündigungen — „+N mehr…“.
- **Dein Geburtstag erscheint automatisch in den Ereignissen** des Geburtsjahres (in Grün, ohne Datenbankeintrag).
- Tippe auf ein Jahrzehnt → darin werden Jahreskarten angezeigt. Jede Karte: **„Jahr — Alter“** mit korrekten Wortformen („1991 — 6 Jahre“, „2026 — 41 Jahre“, das Geburtsjahr — „1985 — Geburt“, Zukunft — „2031 — 46 Jahre · Zukunft“).
- Tippe auf ein Jahr → der Abschnitt „Jahr“ für dieses Jahr.

### Karte: Modus „Jahr“

- Karten für alle Wochen des ausgewählten Jahres, gruppiert nach Monaten mit **klebrigen Kopfzeilen „Januar“…„Dezember“**.
- Jede Karte zeigt: Wochentermine, Ereignisse als farbige Punkte, Vorschauen der Einträge, die Hervorhebung „heute“.
- Tippe auf eine Woche → ein Detailfeld wird geöffnet.
- Tippe auf „Jahr 2026“ → beliebiges Jahr von der Geburt bis zum aktuellen Jahr schnell auswählen.

### Wochen-Panel (BottomSheet)

Ein Tipp auf eine gelebte Woche auf der Karte/im Jahr öffnet ein Panel: Wochentermine, der Button **„Woche öffnen“** (Vollbild), **„Eintrag hinzufügen“** sowie die Abschnitte „Ereignisse“ und „Einträge“ (Tipp auf einen Eintrag zum Bearbeiten, Papierkorbsymbol zum Löschen).

### Die Zukunft ist nicht verfügbar

- Tipps auf zukünftige Wochen werden ignoriert; im Modus „Jahr“ sind sie abgeblendet und mit „Zukunft“ markiert.
- Der Button zum Hinzufügen von Einträgen ist auf zukünftigen Wochen ausgeblendet; die Auswahl zukünftiger Daten im Eintragskalender ist verboten.

### Sonstiges

- **Onboarding beim ersten Start**: fragt nach dem Geburtsdatum, bevor du beginnen kannst.
- **Backup**: exportiere alle Daten als JSON (und importiere sie wieder) über den systemeigenen Dialog zum Öffnen/Speichern von Dateien.
- **Dunkle und helle Themen** — folgen dem System.
- **Mehrsprachig**: Die Sprache der App folgt der Region des Geräts (17 Übersetzungen plus Englisch) und kann manuell in Profil → Sprache geändert werden.
- Alle Daten sind nur lokal (Room + DataStore), es wird kein Internet verwendet.

## Erste Schritte

### Erster Start

1. Installiere die APK (siehe „Installation“ unten).
2. Die App fragt dich nach dem **Geburtsdatum** — dieser Schritt ist Pflicht. Alle Berechnungen basieren darauf: Jahrzehnte, Alter auf den Jahreskarten, Statistiken.
3. Du kannst es jederzeit unter **Profil → Geburtsdatum** ändern.

### Tägliche Nutzung

- **Einen Eintrag für heute machen** — drei Möglichkeiten:
  - der Tab **Heute** → der Button „+“ (FAB);
  - der Tab **Tagebuch** → der Button „+“ (FAB);
  - **Karte** → tippe auf die gewünschte Woche → „Eintrag hinzufügen“.
- **Einen alten Eintrag finden** — der Tab **Tagebuch**, Textsuche.
- **Ein Ereignis hinzufügen** (Geburtstag eines Freundes, ein Jahrestag usw.) — der Tab **Ereignisse** → der Button „+“ → Name, Farbe, Datum.
- **Sehen, was vor 10 Jahren passiert ist** — **Karte** → Modus „10 Jahre“ → tippe auf das Jahrzehnt „1995–2004“ → tippe auf das gewünschte Jahr → der Abschnitt „Jahr“ für dieses Jahr, oder sieh dir gleich die Ankündigungen auf der Jahreskarte an.
- **Deinen Lebensfortschritt einschätzen** — der Tab **Profil**: Alter (Jahre/Wochen), Prozentsatz des gelebten Lebens, verbleibende Wochen, die Anzahl der Einträge und Ereignisse.
- **Daten auf ein anderes Gerät übertragen** — **Profil → Backup**: JSON exportieren → Datei senden → JSON auf dem neuen Gerät importieren.

### Was die Farben und Beschriftungen bedeuten

- Grüner Punkt — „Geburtstag“ (immer virtuell, kann nicht gelöscht werden).
- Punkte in anderen Farben — deine Ereignisse; die Farbe wird beim Erstellen gewählt.
- Zeilen ohne Punkt in den „10 Jahre“-Karten — Tagebucheinträge.
- „+N mehr…“ — nicht alles passt in die Karte; öffne die Jahres-/Jahrzehnt-Karte.
- „· Zukunft“ auf einer Jahreskarte — das Jahr ist noch nicht gekommen; seine Ankündigungen sind bis dahin leer.

## Installation

1. Lade die Datei `life-calendar-vX.Y.Z.apk` von [Releases](https://github.com/PrEvAl85/life-calendar-android/releases) herunter.
2. Öffne die Datei auf deinem Gerät und bestätige die Installation aus unbekannten Quellen (das System fordert dich dazu auf).

### Play Protect und die unsignierte APK

Die APK ist nicht von Google Play signiert und hat die Google-Play-Protect-Prüfung nicht bestanden — bei der ersten Installation kann Android eine Warnung „Play Protect hat die App blockiert“ anzeigen oder dich bitten, die Installation zu bestätigen. Die Datei ist sicher: Es ist ein Build des Open-Source-Codes dieses Repositories.

- Installation: Wähle im Play-Protect-Dialog **„Weitere Details“ → „Trotzdem installieren“** (einmalig), oder in den Android-Einstellungen: **Sicherheit → Installation unbekannter Apps → zulassen** für deinen Dateimanager/Browser.
- Lade die APK nur aus dem Abschnitt **Releases** dieses Repositories herunter.

## Build aus dem Quellcode

Voraussetzungen: JDK 17, Android SDK (compileSdk 35), Gradle 8.10.2 (das Repository enthält den Wrapper).

```
gradlew assembleRelease
```

Die signierte APK erscheint unter `app/build/outputs/apk/release/app-release.apk`.

Hinweis: Enthält der Projektpfad nicht-ASCII-Zeichen, kann Gradle den Build verweigern (`StopExecutionException: ... non-ASCII characters`). Das Flag `android.overridePathCheck=true` ist bereits in `gradle.properties` hinzugefügt, was dieses Problem löst.

## Projektstruktur

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — Einstiegspunkt, Handler für unbehandelte Ausnahmen (Log unter `filesDir/crash.log`).
  - `ui/AppNav.kt` — Navigation und die untere Leiste mit 5 Tabs (Heute · Karte · Tagebuch · Ereignisse · Profil).
  - `ui/grid/` — der Tab „Karte“:
    - `DecadeOverviewScreen.kt` — Jahrzehnt- und Jahreskarten mit Ankündigungen und Altern;
    - `YearOverviewScreen.kt` — die Jahresübersicht nach Monaten mit einem `stickyHeader`;
    - `YearPickerSheet.kt` — schnelle Jahresauswahl;
    - `WeekDetailSheet.kt` — das Wochen-Panel (Ereignisse/Einträge/Woche öffnen);
    - `WeekGridViewModel.kt` — Status: Wochen, Ereignisse, Einträge, Einstellungen.
  - `ui/entries/` — der Tab „Tagebuch“: alle Einträge, Suche, CRUD.
  - `ui/events/` — der Tab „Ereignisse“: eine Liste mit Farben, der angeheftete „Geburtstag“.
  - `ui/profile/` — der Tab „Profil“: Statistiken + Einstellungen + Backup.
  - `ui/onboarding/` — erster Start: Abfrage des Geburtsdatums.
  - `ui/week/` — der Wochenbildschirm (der Tab „Heute“ und die Navigation von der Karte).
  - `ui/common/` — gemeinsamer Eintragsdialog, Datumsauswahl-Dialog, gemeinsame Farben (z. B. Grün für „Geburtstag“).
  - `util/LanguageManager.kt` — Verwaltung der App-Sprache (Geräteregion + manuelle Wahl im Profil).
  - `data/` — Room (Entities, DAO, Datenbank), DataStore (Einstellungen), `BackupManager.kt` (JSON-Backup).
  - `util/Dates.kt` — Datums- und Wochenberechnungen.

## Unterstütze das Projekt

Life Calendar wird in der Freizeit erstellt und gepflegt; die App ist kostenlos und werbefrei. Wenn sie dir nützlich ist — hilf bei ihrer Entwicklung:

- ⭐ **Stern auf GitHub** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **Fehlerberichte und Ideen** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **Erzähl es anderen** — teile es mit denen, denen es nützlich sein könnte

**Finanzielle Unterstützung:**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**Kryptowährung:**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

Danke, dass du Life Calendar nutzt!

## Lizenz

MIT. Details in `LICENSE`.
