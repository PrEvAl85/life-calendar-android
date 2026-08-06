# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

Aplikace **Life Calendar** pro Android zobrazuje celý váš život jako mřížku týdnů — od data narození až do konce předpokládané délky života. Přehledný náhled po desetiletích a rocích, deník záznamů, události a statistiky. Vše je uloženo lokálně ve vašem zařízení.

Vychází z myšlenky [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) ze serveru WaitButWhy.

## Funkce

### Karty

Aplikace se skládá z 5 karet (spodní navigace):

- **Dnes** — obrazovka aktuálního týdne: záznamy a události, navigace mezi sousedními týdny (← →), tlačítko „Dnes".
- **Mapa** — přehled celého vašeho života: přepínač **„10 let | Rok"**.
- **Deník** — všechny záznamy jako jediný seznam: od nejnovějších, vyhledávání podle textu, vytvoření/úprava/smazání.
- **Události** — seznam událostí s barvami; **„Narozeniny" jsou připnuty nahoře** (automaticky z data narození v nastavení).
- **Profil** — životní statistiky + veškeré nastavení + záloha na jedné obrazovce.

### Mapa: režim „10 let"

- Karty desetiletí od roku narození: „1985–1994", „1995–2004", … (poslední je částečná).
- Každá karta má **4 řádky oznámení**: nejprve události (barevná tečka + datum + název), poté záznamy (datum + text). Pokud je oznámení více — „+N další…".
- **Vaše narozeniny se automaticky objeví v událostech** roku narození (zeleně, bez záznamu v databázi).
- Klepněte na desetiletí → karty roků uvnitř. Každá karta: **„Rok — věk"** se správnými tvary slov („1991 — 6 let", „2026 — 41 let", rok narození — „1985 — narození", budoucnost — „2031 — 46 let · budoucnost").
- Klepněte na rok → sekce „Rok" pro daný rok.

### Mapa: režim „Rok"

- Karty všech týdnů vybraného roku, seskupené podle měsíců s **přilepenými hlavičkami „Leden"…„Prosinec"**.
- Každá karta zobrazuje: data týdne, události jako barevné tečky, náhledy záznamů, zvýraznění „dnes".
- Klepněte na týden → panel podrobností.
- Klepněte na „Rok 2026" → rychlý výběr libovolného roku od narození po aktuální.

### Panel týdne (BottomSheet)

Klepnutí na prožitý týden na mapě/v roce otevře panel: data týdne, tlačítko **„Otevřít týden"** (celá obrazovka), **„Přidat záznam"** a sekce „Události" a „Záznamy" (klepnutím na záznam jej upravíte, ikona koše pro smazání).

### Budoucnost je nedostupná

- Klepnutí na budoucí týdny jsou ignorována; v režimu „Rok" jsou ztlumená a označená jako „Budoucnost".
- Tlačítko přidání záznamu je na budoucích týdnech skryté; výběr budoucích dat v kalendáři záznamů je zakázán.

### Různé

- **Prvotní onboarding**: před spuštěním vyžaduje datum narození.
- **Záloha**: export všech dat do JSON (a jejich import zpět) přes systémové dialogové okno pro uložení/otevření souboru.
- **Tmavé a světlé téma** — řídí se systémem.
- **Vícejazyčnost**: jazyk aplikace se řídí regionem zařízení (17 překladů plus angličtina) a lze jej ručně změnit v Profil → Jazyk.
- Všechna data jsou pouze lokální (Room + DataStore), internet se nepoužívá.

## Začínáme

### První spuštění

1. Nainstalujte APK (viz „Instalace" níže).
2. Aplikace se vás zeptá na **datum narození** — tento krok je povinný. Všechny výpočty jsou na něm založeny: desetiletí, věk na kartách roků, statistiky.
3. Kdykoli je můžete změnit v **Profil → Datum narození**.

### Každodenní používání

- **Vytvořte záznam na dnešek** — tři způsoby:
  - karta **Dnes** → tlačítko „+" (FAB);
  - karta **Deník** → tlačítko „+" (FAB);
  - **Mapa** → klepněte na potřebný týden → „Přidat záznam".
- **Najděte starý záznam** — karta **Deník**, vyhledávání podle textu.
- **Přidejte událost** (narozeniny kamaráda, výročí atd.) — karta **Události** → tlačítko „+" → název, barva, datum.
- **Podívejte se, co bylo před 10 lety** — **Mapa** → režim „10 let" → klepněte na desetiletí „1995–2004" → klepněte na potřebný rok → sekce „Rok" pro daný rok, nebo se rovnou podívejte na oznámení na kartě roku.
- **Odhadněte svůj životní pokrok** — karta **Profil**: věk (roky/týdny), procento prožitého života, zbývající týdny, počet záznamů a událostí.
- **Přeneste data do jiného zařízení** — **Profil → Záloha**: export JSON → odešlete soubor → import JSON na novém zařízení.

### Co znamenají barvy a popisky

- Zelená tečka — „Narozeniny" (vždy virtuální, nelze smazat).
- Tečky jiných barev — vaše události; barva se volí při vytváření.
- Řádky bez tečky v kartách „10 let" — deníkové záznamy.
- „+N další…" — ne vše se vejde do karty, otevřete kartu roku/desetiletí.
- „· budoucnost" na kartě roku — rok ještě nenastal; jeho oznámení jsou do té doby prázdná.

## Instalace

1. Stáhněte soubor `life-calendar-vX.Y.Z.apk` ze sekce [Releases](https://github.com/PrEvAl85/life-calendar-android/releases).
2. Otevřete soubor v zařízení a potvrďte instalaci z neznámých zdrojů (systém vás vyzve).

### Play Protect a nepodepsaný APK

APK není podepsán Google Play a neprošel ověřením Google Play Protect — při první instalaci může Android zobrazit upozornění „Play Protect zablokoval aplikaci" nebo vás požádat o potvrzení instalace. Soubor je bezpečný: jde o sestavení open-source kódu tohoto repozitáře.

- Jak nainstalovat: v dialogu Play Protect zvolte **„Další podrobnosti" → „Přesto nainstalovat"** (jednorázově), nebo v nastavení systému Android: **Zabezpečení → Instalace neznámých aplikací → povolit** pro váš správce souborů/prohlížeč.
- APK stahujte pouze ze sekce **Releases** tohoto repozitáře.

## Sestavení ze zdrojů

Požadavky: JDK 17, Android SDK (compileSdk 35), Gradle 8.10.2 (repozitář obsahuje wrapper).

```
gradlew assembleRelease
```

Podepsaný APK se objeví v `app/build/outputs/apk/release/app-release.apk`.

Poznámka: pokud cesta projektu obsahuje ne-ASCII znaky, Gradle může odmítnout sestavení (`StopExecutionException: ... non-ASCII characters`). Příznak `android.overridePathCheck=true` je již přidán v `gradle.properties`, což tento problém řeší.

## Struktura projektu

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — vstupní bod, obsluha nezachycených výjimek (log v `filesDir/crash.log`).
  - `ui/AppNav.kt` — navigace a spodní lišta s 5 kartami (Dnes · Mapa · Deník · Události · Profil).
  - `ui/grid/` — karta „Mapa":
    - `DecadeOverviewScreen.kt` — karty desetiletí a roků s oznámeními a věky;
    - `YearOverviewScreen.kt` — roční přehled po měsících s `stickyHeader`;
    - `YearPickerSheet.kt` — rychlý výběr roku;
    - `WeekDetailSheet.kt` — panel týdne (události/záznamy/otevřít týden);
    - `WeekGridViewModel.kt` — stav: týdny, události, záznamy, nastavení.
  - `ui/entries/` — karta „Deník": všechny záznamy, vyhledávání, CRUD.
  - `ui/events/` — karta „Události": seznam s barvami, připnuté „Narozeniny".
  - `ui/profile/` — karta „Profil": statistiky + nastavení + záloha.
  - `ui/onboarding/` — první spuštění: dotaz na datum narození.
  - `ui/week/` — obrazovka týdne (karta „Dnes" a navigace z mapy).
  - `ui/common/` — sdílené dialogové okno záznamu, dialog výběru data, společné barvy (např. zelená pro „Narozeniny").
  - `util/LanguageManager.kt` — správa jazyka aplikace (region zařízení + ruční volba v Profilu).
  - `data/` — Room (entity, DAO, databáze), DataStore (nastavení), `BackupManager.kt` (záloha JSON).
  - `util/Dates.kt` — výpočty dat a týdnů.

## Podpořte projekt

Life Calendar je vytvářen a udržován ve volném čase; aplikace je zdarma a bez reklam. Pokud je pro vás užitečná — pomozte s jejím vývojem:

- ⭐ **Dejte hvězdičku na GitHub** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **Hlášení chyb a nápady** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **Řekněte ostatním** — sdílejte s těmi, kterým by se mohla hodit

**Finanční podpora:**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**Kryptoměny:**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

Děkujeme, že používáte Life Calendar!

## Licence

MIT. Podrobnosti v `LICENSE`.
