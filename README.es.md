# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

La aplicación de Android **Life Calendar** muestra toda tu vida como una cuadrícula de semanas, desde tu fecha de nacimiento hasta el final de tu esperanza de vida. Una vista cómoda por décadas y años, un diario con anotaciones, eventos y estadísticas. Todo se almacena localmente en tu dispositivo.

Basada en la idea de [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) de WaitButWhy.

## Funciones

### Pestañas

La aplicación consta de 5 pestañas (navegación inferior):

- **Hoy** — la pantalla de la semana actual: anotaciones y eventos, navegación entre semanas vecinas (← →), el botón «Hoy».
- **Mapa** — una vista general de toda tu vida: el conmutador **«10 años | Año»**.
- **Diario** — todas las anotaciones en una sola lista: las más recientes primero, búsqueda por texto, crear/editar/eliminar.
- **Eventos** — una lista de eventos con colores; **«Cumpleaños» está fijado arriba** (automáticamente, a partir de la fecha de nacimiento en los ajustes).
- **Perfil** — estadísticas de vida + todos los ajustes + copia de seguridad en una sola pantalla.

### Mapa: modo «10 años»

- Tarjetas de décadas a partir del año de nacimiento: «1985–1994», «1995–2004», … (la última es parcial).
- Cada tarjeta tiene **4 filas de anuncios**: primero los eventos (punto de color + fecha + nombre), luego las anotaciones (fecha + texto). Si hay más anuncios — «+N más…».
- **Tu cumpleaños aparece automáticamente en los eventos** del año de nacimiento (en verde, sin registro en la base de datos).
- Toca una década → las tarjetas de años dentro de ella. Cada tarjeta: **«Año — edad»** con las formas correctas de las palabras («1991 — 6 años», «2026 — 41 años», el año de nacimiento — «1985 — nacimiento», futuro — «2031 — 46 años · futuro»).
- Toca un año → la sección «Año» para ese año.

### Mapa: modo «Año»

- Tarjetas para todas las semanas del año seleccionado, agrupadas por meses con **encabezados fijos «Enero»…«Diciembre»**.
- Cada tarjeta muestra: las fechas de la semana, los eventos como puntos de color, las vistas previas de las anotaciones, el resaltado de «hoy».
- Toca una semana → se abre un panel de detalles.
- Toca «Año 2026» → selecciona rápidamente cualquier año desde el nacimiento hasta el actual.

### Panel de semana (BottomSheet)

Tocar una semana vivida en el mapa/en el año abre un panel: las fechas de la semana, el botón **«Abrir semana»** (pantalla completa), **«Añadir anotación»**, y las secciones «Eventos» y «Anotaciones» (toca una anotación para editarla, el icono de papelera para eliminarla).

### El futuro no está disponible

- Los toques en las semanas futuras se ignoran; en el modo «Año» están atenuadas y marcadas como «Futuro».
- El botón de añadir anotación está oculto en las semanas futuras; elegir fechas futuras en el calendario de anotaciones está prohibido.

### Varios

- **Integración del primer inicio**: pregunta la fecha de nacimiento antes de poder empezar.
- **Copia de seguridad**: exporta todos los datos a JSON (y vuelve a importarlos) mediante el diálogo de sistema de abrir/guardar archivos.
- **Temas oscuro y claro** — siguen al sistema.
- **Multilingüe**: el idioma de la aplicación sigue la región del dispositivo (17 traducciones más el inglés) y se puede cambiar manualmente en Perfil → Idioma.
- Todos los datos son solo locales (Room + DataStore), no se usa Internet.

## Primeros pasos

### Primer inicio

1. Instala el APK (ver «Instalación» abajo).
2. La aplicación te pedirá tu **fecha de nacimiento** — este paso es obligatorio. Todos los cálculos se basan en ella: décadas, edades en las tarjetas de años, estadísticas.
3. Puedes cambiarla en cualquier momento en **Perfil → Fecha de nacimiento**.

### Uso cotidiano

- **Hacer una anotación para hoy** — tres formas:
  - la pestaña **Hoy** → el botón «+» (FAB);
  - la pestaña **Diario** → el botón «+» (FAB);
  - **Mapa** → toca la semana deseada → «Añadir anotación».
- **Encontrar una anotación antigua** — la pestaña **Diario**, búsqueda por texto.
- **Añadir un evento** (el cumpleaños de un amigo, un aniversario, etc.) — la pestaña **Eventos** → el botón «+» → nombre, color, fecha.
- **Ver qué pasó hace 10 años** — **Mapa** → modo «10 años» → toca la década «1995–2004» → toca el año deseado → la sección «Año» para ese año, o mira directamente los anuncios en la tarjeta del año.
- **Estimar tu progreso de vida** — la pestaña **Perfil**: edad (años/semanas), porcentaje de vida vivido, semanas restantes, el número de anotaciones y eventos.
- **Mover datos a otro dispositivo** — **Perfil → Copia de seguridad**: exporta el JSON → envía el archivo → importa el JSON en el nuevo dispositivo.

### Qué significan los colores y las etiquetas

- Punto verde — «Cumpleaños» (siempre virtual, no se puede eliminar).
- Puntos de otros colores — tus eventos; el color se elige al crearlos.
- Filas sin punto en las tarjetas «10 años» — anotaciones del diario.
- «+N más…» — no todo cabe en la tarjeta; abre la tarjeta de año/década.
- «· futuro» en una tarjeta de año — el año aún no ha llegado; sus anuncios están vacíos hasta entonces.

## Instalación

1. Descarga el archivo `life-calendar-vX.Y.Z.apk` desde [Releases](https://github.com/PrEvAl85/life-calendar-android/releases).
2. Abre el archivo en tu dispositivo y confirma la instalación desde fuentes desconocidas (el sistema te lo pedirá).

### Play Protect y el APK sin firmar

El APK no está firmado por Google Play y no ha pasado la verificación de Google Play Protect — en la primera instalación Android puede mostrar un aviso «Play Protect bloqueó la aplicación» o pedirte que confirmes la instalación. El archivo es seguro: es una compilación del código de código abierto de este repositorio.

- Cómo instalar: en el diálogo de Play Protect elige **«Más detalles» → «Instalar de todos modos»** (una vez), o en los ajustes de Android: **Seguridad → Instalación de aplicaciones desconocidas → permitir** para tu administrador de archivos/navegador.
- Descarga el APK solo desde la sección **Releases** de este repositorio.

## Compilación desde el código fuente

Requisitos: JDK 17, Android SDK (compileSdk 35), Gradle 8.10.2 (el repositorio incluye el wrapper).

```
gradlew assembleRelease
```

El APK firmado aparecerá en `app/build/outputs/apk/release/app-release.apk`.

Nota: si la ruta del proyecto contiene caracteres no ASCII, Gradle puede negarse a compilar (`StopExecutionException: ... non-ASCII characters`). La opción `android.overridePathCheck=true` ya está añadida en `gradle.properties`, lo que resuelve este problema.

## Estructura del proyecto

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — punto de entrada, manejador de excepciones no capturadas (registro en `filesDir/crash.log`).
  - `ui/AppNav.kt` — navegación y la barra inferior con 5 pestañas (Hoy · Mapa · Diario · Eventos · Perfil).
  - `ui/grid/` — la pestaña «Mapa»:
    - `DecadeOverviewScreen.kt` — tarjetas de décadas y años con anuncios y edades;
    - `YearOverviewScreen.kt` — la vista anual por meses con un `stickyHeader`;
    - `YearPickerSheet.kt` — selector rápido de año;
    - `WeekDetailSheet.kt` — el panel de semana (eventos/anotaciones/abrir semana);
    - `WeekGridViewModel.kt` — estado: semanas, eventos, anotaciones, ajustes.
  - `ui/entries/` — la pestaña «Diario»: todas las anotaciones, búsqueda, CRUD.
  - `ui/events/` — la pestaña «Eventos»: una lista con colores, el «Cumpleaños» fijado.
  - `ui/profile/` — la pestaña «Perfil»: estadísticas + ajustes + copia de seguridad.
  - `ui/onboarding/` — primer inicio: solicitud de la fecha de nacimiento.
  - `ui/week/` — la pantalla de semana (la pestaña «Hoy» y la navegación desde el mapa).
  - `ui/common/` — diálogo común de anotación, diálogo del selector de fecha, colores comunes (por ejemplo, verde para «Cumpleaños»).
  - `util/LanguageManager.kt` — gestión del idioma de la aplicación (región del dispositivo + elección manual en Perfil).
  - `data/` — Room (entities, DAO, base de datos), DataStore (ajustes), `BackupManager.kt` (copia de seguridad JSON).
  - `util/Dates.kt` — cálculos de fechas y semanas.

## Apoya el proyecto

Life Calendar se crea y mantiene en el tiempo libre; la aplicación es gratuita y sin anuncios. Si te resulta útil — ayuda a su desarrollo:

- ⭐ **Estrella en GitHub** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **Informes de errores e ideas** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **Cuéntaselo a otros** — compártelo con quienes podría serles útil

**Apoyo económico:**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**Criptomoneda:**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

¡Gracias por usar Life Calendar!

## Licencia

MIT. Detalles en `LICENSE`.
