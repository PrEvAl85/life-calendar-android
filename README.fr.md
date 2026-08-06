# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

L'application Android **Life Calendar** affiche toute votre vie sous forme d'une grille de semaines — de votre date de naissance jusqu'à la fin de votre espérance de vie. Vue pratique par décennies et par années, un journal avec des notes, des événements et des statistiques. Tout est stocké localement sur votre appareil.

Basé sur l'idée de [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) de WaitButWhy.

## Fonctionnalités

### Onglets

L'application se compose de 5 onglets (navigation inférieure) :

- **Aujourd'hui** — l'écran de la semaine en cours : notes et événements, navigation entre les semaines voisines (← →), le bouton « Aujourd'hui ».
- **Carte** — une vue d'ensemble de toute votre vie : le sélecteur **« 10 ans | Année »**.
- **Journal** — toutes les notes en une seule liste : les plus récentes en premier, recherche par texte, création/modification/suppression.
- **Événements** — une liste d'événements avec des couleurs ; **« Anniversaire » est épinglé en haut** (automatiquement, à partir de la date de naissance dans les réglages).
- **Profil** — statistiques de vie + tous les réglages + sauvegarde dans un seul écran.

### Carte : mode « 10 ans »

- Cartes de décennies à partir de l'année de naissance : « 1985–1994 », « 1995–2004 », … (la dernière est partielle).
- Chaque carte comporte **4 lignes d'annonces** : d'abord les événements (point coloré + date + nom), puis les notes (date + texte). S'il y a plus d'annonces — « +N de plus… ».
- **Votre anniversaire apparaît automatiquement dans les événements** de l'année de naissance (en vert, sans entrée en base de données).
- Touchez une décennie → les cartes des années qu'elle contient. Chaque carte : **« Année — âge »** avec les formes correctes des mots (« 1991 — 6 ans », « 2026 — 41 ans », l'année de naissance — « 1985 — naissance », avenir — « 2031 — 46 ans · futur »).
- Touchez une année → la section « Année » pour cette année.

### Carte : mode « Année »

- Cartes pour toutes les semaines de l'année sélectionnée, groupées par mois avec des **en-têtes collants « Janvier »…« Décembre »**.
- Chaque carte affiche : les dates de la semaine, les événements sous forme de points colorés, les aperçus des notes, la mise en évidence « aujourd'hui ».
- Touchez une semaine → un panneau de détail s'ouvre.
- Touchez « Année 2026 » → sélectionnez rapidement n'importe quelle année de la naissance à l'année en cours.

### Panneau semaine (BottomSheet)

Toucher une semaine déjà vécue sur la carte/dans l'année ouvre un panneau : les dates de la semaine, le bouton **« Ouvrir la semaine »** (plein écran), **« Ajouter une note »**, ainsi que les sections « Événements » et « Notes » (touchez une note pour la modifier, l'icône corbeille pour la supprimer).

### L'avenir est indisponible

- Les touches sur les semaines futures sont ignorées ; dans le mode « Année », elles sont estompées et marquées « Futur ».
- Le bouton d'ajout de note est masqué sur les semaines futures ; la sélection de dates futures dans le calendrier de notes est interdite.

### Divers

- **Intégration au premier lancement** : demande la date de naissance avant de pouvoir commencer.
- **Sauvegarde** : exportez toutes les données en JSON (et réimportez-les) via la boîte de dialogue système d'ouverture/enregistrement de fichiers.
- **Thèmes sombre et clair** — suivent le système.
- **Multilingue** : la langue de l'application suit la région de l'appareil (17 traductions plus l'anglais) et peut être changée manuellement dans Profil → Langue.
- Toutes les données sont uniquement locales (Room + DataStore), aucune connexion Internet n'est utilisée.

## Pour commencer

### Premier lancement

1. Installez l'APK (voir « Installation » ci-dessous).
2. L'application vous demandera votre **date de naissance** — cette étape est obligatoire. Tous les calculs en dépendent : décennies, âges sur les cartes d'années, statistiques.
3. Vous pouvez la modifier à tout moment dans **Profil → Date de naissance**.

### Utilisation quotidienne

- **Créer une note pour aujourd'hui** — trois façons :
  - l'onglet **Aujourd'hui** → le bouton « + » (FAB) ;
  - l'onglet **Journal** → le bouton « + » (FAB) ;
  - **Carte** → touchez la semaine voulue → « Ajouter une note ».
- **Retrouver une ancienne note** — l'onglet **Journal**, recherche par texte.
- **Ajouter un événement** (un anniversaire d'ami, un anniversaire de mariage, etc.) — l'onglet **Événements** → le bouton « + » → nom, couleur, date.
- **Voir ce qui s'est passé il y a 10 ans** — **Carte** → mode « 10 ans » → touchez la décennie « 1995–2004 » → touchez l'année voulue → la section « Année » pour cette année, ou regardez directement les annonces sur la carte de l'année.
- **Estimer votre progression de vie** — l'onglet **Profil** : âge (années/semaines), pourcentage de vie vécue, semaines restantes, le nombre de notes et d'événements.
- **Transférer les données vers un autre appareil** — **Profil → Sauvegarde** : exportez le JSON → envoyez le fichier → importez le JSON sur le nouvel appareil.

### Signification des couleurs et des étiquettes

- Point vert — « Anniversaire » (toujours virtuel, ne peut pas être supprimé).
- Points d'autres couleurs — vos événements ; la couleur est choisie à la création.
- Lignes sans point dans les cartes « 10 ans » — notes du journal.
- « +N de plus… » — tout ne tient pas dans la carte ; ouvrez la carte de l'année/décennie.
- « · futur » sur une carte d'année — l'année n'est pas encore arrivée ; ses annonces sont vides jusqu'à ce moment-là.

## Installation

1. Téléchargez le fichier `life-calendar-vX.Y.Z.apk` depuis [Releases](https://github.com/PrEvAl85/life-calendar-android/releases).
2. Ouvrez le fichier sur votre appareil et confirmez l'installation depuis des sources inconnues (le système vous y invitera).

### Play Protect et l'APK non signé

L'APK n'est pas signé par Google Play et n'a pas passé la vérification de Google Play Protect — lors de la première installation, Android peut afficher un avertissement « Play Protect a bloqué l'application » ou demander de confirmer l'installation. Le fichier est sûr : il s'agit d'une compilation du code open source de ce dépôt.

- Comment installer : dans la boîte de dialogue Play Protect, choisissez **« Plus de détails » → « Installer quand même »** (une fois), ou dans les paramètres Android : **Sécurité → Installation d'applications inconnues → autoriser** pour votre gestionnaire de fichiers/navigateur.
- Ne téléchargez l'APK que depuis la section **Releases** de ce dépôt.

## Compilation à partir des sources

Prérequis : JDK 17, Android SDK (compileSdk 35), Gradle 8.10.2 (le dépôt contient le wrapper).

```
gradlew assembleRelease
```

L'APK signé apparaîtra à `app/build/outputs/apk/release/app-release.apk`.

Remarque : si le chemin du projet contient des caractères non-ASCII, Gradle peut refuser la compilation (`StopExecutionException: ... non-ASCII characters`). L'option `android.overridePathCheck=true` est déjà ajoutée dans `gradle.properties`, ce qui résout ce problème.

## Structure du projet

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — point d'entrée, gestionnaire des exceptions non interceptées (journal dans `filesDir/crash.log`).
  - `ui/AppNav.kt` — navigation et la barre inférieure à 5 onglets (Aujourd'hui · Carte · Journal · Événements · Profil).
  - `ui/grid/` — l'onglet « Carte » :
    - `DecadeOverviewScreen.kt` — cartes de décennies et d'années avec annonces et âges ;
    - `YearOverviewScreen.kt` — la vue annuelle par mois avec un `stickyHeader` ;
    - `YearPickerSheet.kt` — sélecteur rapide d'année ;
    - `WeekDetailSheet.kt` — le panneau de semaine (événements/notes/ouvrir la semaine) ;
    - `WeekGridViewModel.kt` — état : semaines, événements, notes, réglages.
  - `ui/entries/` — l'onglet « Journal » : toutes les notes, recherche, CRUD.
  - `ui/events/` — l'onglet « Événements » : une liste avec couleurs, l'« Anniversaire » épinglé.
  - `ui/profile/` — l'onglet « Profil » : statistiques + réglages + sauvegarde.
  - `ui/onboarding/` — premier lancement : demande de la date de naissance.
  - `ui/week/` — l'écran de semaine (l'onglet « Aujourd'hui » et la navigation depuis la carte).
  - `ui/common/` — dialogue commun de note, dialogue de sélecteur de date, couleurs communes (par ex. le vert pour « Anniversaire »).
  - `util/LanguageManager.kt` — gestion de la langue de l'application (région de l'appareil + choix manuel dans le Profil).
  - `data/` — Room (entities, DAO, base de données), DataStore (réglages), `BackupManager.kt` (sauvegarde JSON).
  - `util/Dates.kt` — calculs de dates et de semaines.

## Soutenir le projet

Life Calendar est créé et maintenu pendant le temps libre ; l'application est gratuite et sans publicité. Si elle vous est utile — aidez son développement :

- ⭐ **Étoile sur GitHub** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **Signalements de bugs et idées** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **Parlez-en aux autres** — partagez avec ceux à qui cela pourrait être utile

**Soutien financier :**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**Cryptomonnaie :**

- USDT (TRC20) : `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20) : `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC : `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON : `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

Merci d'utiliser Life Calendar !

## Licence

MIT. Détails dans `LICENSE`.
