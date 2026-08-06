# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

**Life Calendar** Android uygulaması tüm hayatınızı bir hafta ızgarası olarak gösterir — doğum tarihinizden tahmini yaşam sürenizin sonuna kadar. On yıllar ve yıllar üzerinden kullanışlı genel bakış, günlük kayıtları, etkinlikler ve istatistikler. Her şey cihazınızda yerel olarak saklanır.

WaitButWhy'in [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) fikrine dayanır.

## Özellikler

### Sekmeler

Uygulama 5 sekmeden oluşur (alt gezinme):

- **Bugün** — geçerli hafta ekranı: kayıtlar ve etkinlikler, komşu haftalar arasında gezinme (← →), "Bugün" düğmesi.
- **Harita** — tüm hayatınıza genel bakış: **"10 yıl | Yıl"** değiştirici.
- **Günlük** — tüm kayıtlar tek bir liste olarak: en yeniden eskiye, metne göre arama, oluştur/düzenle/sil.
- **Etkinlikler** — renkli etkinliklerin listesi; **"Doğum günü" en üste sabitlenir** (otomatik olarak, ayarlardaki doğum tarihinden).
- **Profil** — yaşam istatistikleri + tüm ayarlar + yedekleme tek ekranda.

### Harita: "10 yıl" modu

- Doğum yılından başlayan on yıl kartları: "1985–1994", "1995–2004", … (sonuncusu kısmi).
- Her kartın **4 duyuru satırı** vardır: önce etkinlikler (renkli nokta + tarih + ad), sonra kayıtlar (tarih + metin). Daha fazla duyuru varsa — "+N daha fazla…".
- **Doğum gününüz, doğum yılının etkinliklerinde otomatik olarak görünür** (yeşil, veritabanı kaydı olmadan).
- Bir on yıla dokunun → içindeki yıl kartları. Her kart: **"Yıl — yaş"** doğru kelime biçimleriyle ("1991 — 6 yaş", "2026 — 41 yaş", doğum yılı — "1985 — doğum", gelecek — "2031 — 46 yaş · gelecek").
- Bir yıla dokunun → o yılın "Yıl" bölümü.

### Harita: "Yıl" modu

- Seçilen yılın tüm haftaları için kartlar, **sabit "Ocak"…"Aralık" başlıklarıyla** aylara göre gruplanmış.
- Her kart şunları gösterir: hafta tarihleri, renkli noktalar olarak etkinlikler, kayıt önizlemeleri, "bugün" vurgusu.
- Bir haftaya dokunun → ayrıntı paneli.
- "Yıl 2026"ya dokunun → doğum yılından günümüze herhangi bir yılı hızlıca seçin.

### Hafta paneli (BottomSheet)

Haritada/yılda yaşanmış bir haftaya dokunmak bir panel açar: hafta tarihleri, **"Haftayı aç"** düğmesi (tam ekran), **"Kayıt ekle"** ve "Etkinlikler" ile "Kayıtlar" bölümleri (düzenlemek için bir kayda dokunun, silmek için çöp kutusu simgesi).

### Gelecek kullanılamaz

- Gelecek haftalara dokunuşlar yok sayılır; "Yıl" modunda bunlar karartılır ve "Gelecek" olarak işaretlenir.
- Kayıt ekle düğmesi gelecek haftalarda gizlenir; kayıt takviminde gelecek tarihlerin seçilmesi yasaktır.

### Diğer

- **İlk açılış tanıtımı**: başlamadan önce doğum tarihini sorar.
- **Yedekleme**: tüm verileri sistemin dosya kaydet/aç iletişim kutusu aracılığıyla JSON'a dışa aktarın (ve geri içe aktarın).
- **Koyu ve açık temalar** — sistemi takip eder.
- **Çok dilli**: uygulama dili cihaz bölgesini takip eder (İngilizceye ek olarak 17 çeviri) ve Profil → Dil bölümünden elle değiştirilebilir.
- Tüm veriler yalnızca yereldir (Room + DataStore), internet kullanılmaz.

## Başlarken

### İlk açılış

1. APK'yi kurun (aşağıdaki "Kurulum" bölümüne bakın).
2. Uygulama sizden **doğum tarihini** isteyecektir — bu adım zorunludur. Tüm hesaplamalar buna dayanır: on yıllar, yıl kartlarındaki yaşlar, istatistikler.
3. İstediğiniz zaman **Profil → Doğum tarihi** bölümünden değiştirebilirsiniz.

### Günlük kullanım

- **Bugün için bir kayıt oluşturun** — üç yol:
  - **Bugün** sekmesi → "+" düğmesi (FAB);
  - **Günlük** sekmesi → "+" düğmesi (FAB);
  - **Harita** → ihtiyacınız olan haftaya dokunun → "Kayıt ekle".
- **Eski bir kaydı bulun** — **Günlük** sekmesi, metne göre arama.
- **Bir etkinlik ekleyin** (bir arkadaşın doğum günü, bir yıl dönümü vb.) — **Etkinlikler** sekmesi → "+" düğmesi → ad, renk, tarih.
- **10 yıl önce ne olduğunu görün** — **Harita** → "10 yıl" modu → "1995–2004" on yılına dokunun → ihtiyacınız olan yıla dokunun → o yılın "Yıl" bölümü veya hemen yıl kartındaki duyurulara bakın.
- **Yaşam ilerlemenizi değerlendirin** — **Profil** sekmesi: yaş (yıl/hafta), yaşanan yaşam yüzdesi, kalan haftalar, kayıt ve etkinlik sayısı.
- **Verileri başka bir cihaza taşıyın** — **Profil → Yedekleme**: JSON dışa aktarın → dosyayı gönderin → yeni cihazda JSON içe aktarın.

### Renkler ve etiketler ne anlama gelir

- Yeşil nokta — "Doğum günü" (her zaman sanaldır, silinemez).
- Diğer renklerdeki noktalar — etkinlikleriniz; renk oluştururken seçilir.
- "10 yıl" kartlarında noktası olmayan satırlar — günlük kayıtları.
- "+N daha fazla…" — her şey karta sığmaz, yıl/on yıl kartını açın.
- Yıl kartında "· gelecek" — yıl henüz gelmedi; o zamana kadar duyuruları boştur.

## Kurulum

1. `life-calendar-vX.Y.Z.apk` dosyasını [Releases](https://github.com/PrEvAl85/life-calendar-android/releases) bölümünden indirin.
2. Dosyayı cihazınızda açın ve bilinmeyen kaynaklardan kurulumu onaylayın (sistem sizi yönlendirecektir).

### Play Protect ve imzasız APK

APK, Google Play tarafından imzalanmamıştır ve Google Play Protect doğrulamasından geçmemiştir — ilk kurulumda Android, "Play Protect uygulamayı engelledi" uyarısını gösterebilir veya kurulumu onaylamanızı isteyebilir. Dosya güvenlidir: bu deponun açık kaynak kodunun bir derlemesidir.

- Nasıl kurulur: Play Protect iletişim kutusunda **"Daha fazla ayrıntı" → "Yine de kur"** seçeneğini seçin (bir kez) veya Android ayarlarında: **Güvenlik → Bilinmeyen uygulama kurulumu → dosya yöneticiniz/tarayıcınız için izin verin**.
- APK'yi yalnızca bu deponun **Releases** bölümünden indirin.

## Kaynak koddan derleme

Gereksinimler: JDK 17, Android SDK (compileSdk 35), Gradle 8.10.2 (depo wrapper içerir).

```
gradlew assembleRelease
```

İmzalı APK, `app/build/outputs/apk/release/app-release.apk` konumunda görünecektir.

Not: proje yolu ASCII olmayan karakterler içeriyorsa Gradle derlemeyi reddedebilir (`StopExecutionException: ... non-ASCII characters`). `android.overridePathCheck=true` işareti `gradle.properties` dosyasına zaten eklenmiştir ve bu sorunu çözer.

## Proje yapısı

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — giriş noktası, yakalanmamış istisnalar için işleyici (log: `filesDir/crash.log`).
  - `ui/AppNav.kt` — gezinme ve 5 sekmeli alt çubuk (Bugün · Harita · Günlük · Etkinlikler · Profil).
  - `ui/grid/` — "Harita" sekmesi:
    - `DecadeOverviewScreen.kt` — duyurular ve yaşlarla birlikte on yıl ve yıl kartları;
    - `YearOverviewScreen.kt` — `stickyHeader` ile aylara göre yıllık genel bakış;
    - `YearPickerSheet.kt` — hızlı yıl seçici;
    - `WeekDetailSheet.kt` — hafta paneli (etkinlikler/kayıtlar/haftayı aç);
    - `WeekGridViewModel.kt` — durum: haftalar, etkinlikler, kayıtlar, ayarlar.
  - `ui/entries/` — "Günlük" sekmesi: tüm kayıtlar, arama, CRUD.
  - `ui/events/` — "Etkinlikler" sekmesi: renkli liste, sabitlenmiş "Doğum günü".
  - `ui/profile/` — "Profil" sekmesi: istatistikler + ayarlar + yedekleme.
  - `ui/onboarding/` — ilk açılış: doğum tarihi sorma.
  - `ui/week/` — hafta ekranı ("Bugün" sekmesi ve haritadan gezinme).
  - `ui/common/` — ortak kayıt iletişim kutusu, tarih seçici iletişim kutusu, ortak renkler (örn. "Doğum günü" için yeşil).
  - `util/LanguageManager.kt` — uygulama dili yönetimi (cihaz bölgesi + Profil'de elle seçim).
  - `data/` — Room (varlıklar, DAO, veritabanı), DataStore (ayarlar), `BackupManager.kt` (JSON yedekleme).
  - `util/Dates.kt` — tarih ve hafta hesaplamaları.

## Projeyi Destekleyin

Life Calendar boş zamanlarda oluşturulur ve bakımı yapılır; uygulama ücretsizdir ve reklamsızdır. Sizin için yararlıysa — geliştirilmesine yardımcı olun:

- ⭐ **GitHub'da yıldız verin** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **Hata bildirimleri ve fikirler** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **Başkalarına söyleyin** — işine yarayacaklarla paylaşın

**Maddi destek:**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**Kripto para:**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

Life Calendar'ı kullandığınız için teşekkürler!

## Lisans

MIT. Ayrıntılar `LICENSE` dosyasında.
