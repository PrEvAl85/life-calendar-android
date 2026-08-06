# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

L'app Android **Life Calendar** mostra tutta la tua vita come una griglia di settimane, dalla tua data di nascita fino alla fine della tua aspettativa di vita. Una comoda panoramica per decenni e anni, un diario con appunti, eventi e statistiche. Tutto è archiviato localmente sul tuo dispositivo.

Basata sull'idea di [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) di WaitButWhy.

## Funzionalità

### Schede

L'app è composta da 5 schede (navigazione in basso):

- **Oggi** — la schermata della settimana corrente: appunti ed eventi, navigazione tra le settimane vicine (← →), il pulsante «Oggi».
- **Mappa** — una panoramica di tutta la tua vita: il selettore **«10 anni | Anno»**.
- **Diario** — tutti gli appunti in un unico elenco: i più recenti prima, ricerca per testo, crea/modifica/elimina.
- **Eventi** — un elenco di eventi con colori; **«Compleanno» è fissato in alto** (automaticamente, dalla data di nascita nelle impostazioni).
- **Profilo** — statistiche di vita + tutte le impostazioni + backup in un'unica schermata.

### Mappa: modalità «10 anni»

- Schede dei decenni a partire dall'anno di nascita: «1985–1994», «1995–2004», … (l'ultima è parziale).
- Ogni scheda ha **4 righe di annunci**: prima gli eventi (punto colorato + data + nome), poi gli appunti (data + testo). Se ci sono più annunci — «+N in più…».
- **Il tuo compleanno appare automaticamente negli eventi** dell'anno di nascita (in verde, senza una voce nel database).
- Tocca un decennio → le schede degli anni al suo interno. Ogni scheda: **«Anno — età»** con le corrette forme delle parole («1991 — 6 anni», «2026 — 41 anni», l'anno di nascita — «1985 — nascita», futuro — «2031 — 46 anni · futuro»).
- Tocca un anno → la sezione «Anno» per quell'anno.

### Mappa: modalità «Anno»

- Schede per tutte le settimane dell'anno selezionato, raggruppate per mesi con **intestazioni fisse «Gennaio»…«Dicembre»**.
- Ogni scheda mostra: le date della settimana, gli eventi come punti colorati, le anteprime degli appunti, l'evidenziazione di «oggi».
- Tocca una settimana → si apre un pannello di dettaglio.
- Tocca «Anno 2026» → seleziona rapidamente qualsiasi anno dalla nascita a quello corrente.

### Pannello settimana (BottomSheet)

Toccare una settimana già vissuta sulla mappa/nell'anno apre un pannello: le date della settimana, il pulsante **«Apri settimana»** (a schermo intero), **«Aggiungi appunto»** e le sezioni «Eventi» e «Appunti» (tocca un appunto per modificarlo, l'icona del cestino per eliminarlo).

### Il futuro non è disponibile

- I tocchi sulle settimane future vengono ignorati; nella modalità «Anno» sono attenuate e marcate come «Futuro».
- Il pulsante di aggiunta appunti è nascosto sulle settimane future; la scelta di date future nel calendario degli appunti è vietata.

### Varie

- **Onboarding al primo avvio**: chiede la data di nascita prima di poter iniziare.
- **Backup**: esporta tutti i dati in JSON (e li reimporta) tramite il dialogo di sistema di apertura/salvataggio dei file.
- **Temi scuro e chiaro** — seguono il sistema.
- **Multilingua**: la lingua dell'app segue la regione del dispositivo (17 traduzioni più l'inglese) e può essere cambiata manualmente in Profilo → Lingua.
- Tutti i dati sono solo locali (Room + DataStore), non viene utilizzato Internet.

## Per iniziare

### Primo avvio

1. Installa l'APK (vedi «Installazione» sotto).
2. L'app ti chiederà la **data di nascita** — questo passaggio è obbligatorio. Tutti i calcoli si basano su di essa: decenni, età sulle schede degli anni, statistiche.
3. Puoi modificarla in qualsiasi momento in **Profilo → Data di nascita**.

### Uso quotidiano

- **Fare un appunto per oggi** — tre modi:
  - la scheda **Oggi** → il pulsante «+» (FAB);
  - la scheda **Diario** → il pulsante «+» (FAB);
  - **Mappa** → tocca la settimana desiderata → «Aggiungi appunto».
- **Trovare un vecchio appunto** — la scheda **Diario**, ricerca per testo.
- **Aggiungere un evento** (il compleanno di un amico, un anniversario, ecc.) — la scheda **Eventi** → il pulsante «+» → nome, colore, data.
- **Vedere cosa è successo 10 anni fa** — **Mappa** → modalità «10 anni» → tocca il decennio «1995–2004» → tocca l'anno desiderato → la sezione «Anno» per quell'anno, oppure guarda subito gli annunci sulla scheda dell'anno.
- **Stimare il tuo progresso di vita** — la scheda **Profilo**: età (anni/settimane), percentuale di vita vissuta, settimane rimanenti, il numero di appunti ed eventi.
- **Trasferire i dati su un altro dispositivo** — **Profilo → Backup**: esporta il JSON → invia il file → importa il JSON sul nuovo dispositivo.

### Cosa significano i colori e le etichette

- Punto verde — «Compleanno» (sempre virtuale, non può essere eliminato).
- Punti di altri colori — i tuoi eventi; il colore viene scelto alla creazione.
- Righe senza punto nelle schede «10 anni» — appunti del diario.
- «+N in più…» — non tutto entra nella scheda; apri la scheda dell'anno/decennio.
- «· futuro» su una scheda dell'anno — l'anno non è ancora arrivato; i suoi annunci sono vuoti fino ad allora.

## Installazione

1. Scarica il file `life-calendar-vX.Y.Z.apk` da [Releases](https://github.com/PrEvAl85/life-calendar-android/releases).
2. Apri il file sul tuo dispositivo e conferma l'installazione da fonti sconosciute (il sistema te lo chiederà).

### Play Protect e l'APK non firmato

L'APK non è firmato da Google Play e non ha superato la verifica di Google Play Protect — al primo avvio Android potrebbe mostrare un avviso «Play Protect ha bloccato l'app» o chiederti di confermare l'installazione. Il file è sicuro: è una compilazione del codice open source di questo repository.

- Come installare: nel dialogo di Play Protect scegli **«Ulteriori dettagli» → «Installa comunque»** (una volta), oppure nelle impostazioni Android: **Sicurezza → Installazione di app sconosciute → consenti** per il tuo gestore file/browser.
- Scarica l'APK solo dalla sezione **Releases** di questo repository.

## Compilazione dal codice sorgente

Requisiti: JDK 17, Android SDK (compileSdk 35), Gradle 8.10.2 (il repository include il wrapper).

```
gradlew assembleRelease
```

L'APK firmato apparirà in `app/build/outputs/apk/release/app-release.apk`.

Nota: se il percorso del progetto contiene caratteri non ASCII, Gradle potrebbe rifiutarsi di compilare (`StopExecutionException: ... non-ASCII characters`). Il flag `android.overridePathCheck=true` è già aggiunto in `gradle.properties`, il che risolve questo problema.

## Struttura del progetto

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — punto di ingresso, gestore delle eccezioni non catturate (log in `filesDir/crash.log`).
  - `ui/AppNav.kt` — navigazione e barra inferiore con 5 schede (Oggi · Mappa · Diario · Eventi · Profilo).
  - `ui/grid/` — la scheda «Mappa»:
    - `DecadeOverviewScreen.kt` — schede di decenni e anni con annunci ed età;
    - `YearOverviewScreen.kt` — la vista annuale per mesi con un `stickyHeader`;
    - `YearPickerSheet.kt` — selettore rapido dell'anno;
    - `WeekDetailSheet.kt` — il pannello della settimana (eventi/appunti/apri settimana);
    - `WeekGridViewModel.kt` — stato: settimane, eventi, appunti, impostazioni.
  - `ui/entries/` — la scheda «Diario»: tutti gli appunti, ricerca, CRUD.
  - `ui/events/` — la scheda «Eventi»: un elenco con colori, il «Compleanno» fissato.
  - `ui/profile/` — la scheda «Profilo»: statistiche + impostazioni + backup.
  - `ui/onboarding/` — primo avvio: richiesta della data di nascita.
  - `ui/week/` — la schermata della settimana (la scheda «Oggi» e la navigazione dalla mappa).
  - `ui/common/` — dialogo comune per gli appunti, dialogo del selettore data, colori comuni (ad es. il verde per «Compleanno»).
  - `util/LanguageManager.kt` — gestione della lingua dell'app (regione del dispositivo + scelta manuale nel Profilo).
  - `data/` — Room (entities, DAO, database), DataStore (impostazioni), `BackupManager.kt` (backup JSON).
  - `util/Dates.kt` — calcoli di date e settimane.

## Supporta il progetto

Life Calendar è creato e mantenuto nel tempo libero; l'app è gratuita e senza pubblicità. Se ti è utile — aiuta il suo sviluppo:

- ⭐ **Stella su GitHub** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **Segnalazioni di bug e idee** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **Dillo agli altri** — condividilo con chi potrebbe trovarlo utile

**Supporto finanziario:**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**Criptovaluta:**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

Grazie per aver usato Life Calendar!

## Licenza

MIT. Dettagli in `LICENSE`.
