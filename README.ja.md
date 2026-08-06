# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

**Life Calendar** Android アプリは、あなたの人生全体を週のグリッドとして表示します——誕生日から予想寿命の終わりまで。10年単位と年単位の便利な概要、日記の記録、イベント、統計。すべてデバイス上にローカル保存されます。

WaitButWhy の [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) のアイデアに基づいています。

## 機能

### タブ

アプリは 5 つのタブで構成されています（下部ナビゲーション）：

- **Today（今日）** — 現在の週の画面：記録とイベント、隣接する週の間のナビゲーション（← →）、「今日」ボタン。
- **Map（マップ）** — 人生全体の概要：**「10 years | Year」**切り替え。
- **Journal（日誌）** — すべての記録が 1 つのリスト：新しい順、テキスト検索、作成/編集/削除。
- **Events（イベント）** — 色付きのイベントのリスト；**「Birthday（誕生日）」が上部に固定**（設定の生年月日から自動的に）。
- **Profile（プロフィール）** — 人生の統計 + すべての設定 + バックアップを 1 つの画面で。

### マップ：「10 years」モード

- 生まれた年から始まる10年単位のカード：「1985–1994」「1995–2004」、……（最後は部分的なもの）。
- 各カードには **4 行のアナウンス**：最初にイベント（色付きの点 + 日付 + 名前）、次に記録（日付 + テキスト）。それ以上ある場合は「+N more…」。
- **誕生日は生まれた年のイベントに自動的に表示されます**（緑色、データベースエントリなし）。
- 10年単位をタップ → 中の年カード。各カード：**「Year — age」** 正しい語形付き（「1991 — 6 years」「2026 — 41 years」、生まれた年は「1985 — birth」、未来は「2031 — 46 years · future」）。
- 年をタップ → その年の「Year」セクション。

### マップ：「Year」モード

- 選択した年のすべての週のカード。月ごとにグループ化され、**固定された「January」…「December」のヘッダー**付き。
- 各カードに表示：週の日付、イベントの色付きの点、記録のプレビュー、「今日」の強調表示。
- 週をタップ → 詳細パネル。
- 「Year 2026」をタップ → 生まれた年から現在までの任意の年をすばやく選択。

### 週パネル（BottomSheet）

マップ/年で生きた週をタップするとパネルが開きます：週の日付、**「Open week」**ボタン（全画面）、**「Add entry」**、および「Events」と「Entries」セクション（記録をタップすると編集、ゴミ箱アイコンで削除）。

### 未来は利用できません

- 未来の週のタップは無視されます。「Year」モードでは暗く表示され「Future」とマークされます。
- 未来の週では記録追加ボタンは非表示です。記録カレンダーで未来の日付を選ぶことはできません。

### その他

- **初回起動時オンボーディング**：開始する前に生年月日を尋ねます。
- **バックアップ**：システムのファイル保存/開くダイアログを通じて、すべてのデータを JSON にエクスポート（および再インポート）。
- **ダークテーマとライトテーマ** — システムに追従します。
- **多言語**：アプリの言語はデバイスの地域に追従します（17 言語の翻訳と英語）。プロフィール → 言語で手動で変更できます。
- すべてのデータはローカルのみです（Room + DataStore）、インターネットは使用しません。

## はじめに

### 初回起動

1. APK をインストールします（下記「インストール」を参照）。
2. アプリは**生年月日**を尋ねます——この手順は必須です。すべての計算はこれに基づきます：10年単位、年カードの年齢、統計。
3. いつでも**プロフィール → 生年月日**で変更できます。

### 毎日の使い方

- **今日の記録を作成** — 3 つの方法：
  - **Today（今日）**タブ →「+」ボタン（FAB）；
  - **Journal（日誌）**タブ →「+」ボタン（FAB）；
  - **Map（マップ）**→ 目的の週をタップ →「記録を追加」。
- **古い記録を探す** — **Journal（日誌）**タブ、テキスト検索。
- **イベントを追加**（友人の誕生日、記念日など）— **Events（イベント）**タブ →「+」ボタン → 名前、色、日付。
- **10年前の出来事を見る** — **Map（マップ）**→「10 years」モード →「1995–2004」の10年単位をタップ → 目的の年をタップ → その年の「Year」セクション、または年カードのアナウンスをすぐに見る。
- **人生の進捗を確認する** — **Profile（プロフィール）**タブ：年齢（年/週）、生きた人生の割合、残り週数、記録とイベントの数。
- **データを別のデバイスへ移行** — **プロフィール → バックアップ**：JSON をエクスポート → ファイルを送信 → 新しいデバイスで JSON をインポート。

### 色とラベルの意味

- 緑の点 — 「誕生日」（常に仮想的で、削除できません）。
- その他の色の点 — あなたのイベント。色は作成時に選びます。
- 「10 years」カードで点のない行 — 日誌の記録。
- 「+N more…」 — カードにすべてが収まっていません。年/10年単位のカードを開いてください。
- 年カードの「· future」— その年はまだ来ていません。それまではアナウンスは空です。

## インストール

1. [Releases](https://github.com/PrEvAl85/life-calendar-android/releases) から `life-calendar-vX.Y.Z.apk` ファイルをダウンロードします。
2. デバイスでファイルを開き、不明なソースからのインストールを確認します（システムがプロンプトを表示します）。

### Play Protect と未署名の APK

この APK は Google Play で署名されておらず、Google Play Protect の検証も通過していません——初回インストール時に Android が「Play Protect がアプリをブロックしました」という警告を表示したり、インストールの確認を求めたりすることがあります。このファイルは安全です：このリポジトリのオープンソースコードからビルドされたものです。

- インストール方法：Play Protect のダイアログで**「詳細」→「それでもインストール」**を選択（1回だけ）、または Android の設定で：**セキュリティ → 不明なアプリのインストール → 許可**をファイルマネージャー/ブラウザーに対して行います。
- APK はこのリポジトリの **Releases** セクションからのみダウンロードしてください。

## ソースからのビルド

要件：JDK 17、Android SDK（compileSdk 35）、Gradle 8.10.2（リポジトリには wrapper が含まれています）。

```
gradlew assembleRelease
```

署名済みの APK は `app/build/outputs/apk/release/app-release.apk` に生成されます。

注意：プロジェクトのパスに非 ASCII 文字が含まれると、Gradle がビルドを拒否する場合があります（`StopExecutionException: ... non-ASCII characters`）。`android.overridePathCheck=true` フラグが `gradle.properties` にすでに追加されており、この問題を解決します。

## プロジェクト構造

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — エントリポイント、捕捉されない例外のハンドラー（ログは `filesDir/crash.log`）。
  - `ui/AppNav.kt` — ナビゲーションと 5 つのタブを持つ下部バー（Today · Map · Journal · Events · Profile）。
  - `ui/grid/` —「Map」タブ：
    - `DecadeOverviewScreen.kt` — アナウンスと年齢付きの10年単位・年カード；
    - `YearOverviewScreen.kt` — `stickyHeader` 付きの月別年間概要；
    - `YearPickerSheet.kt` — クイック年選択；
    - `WeekDetailSheet.kt` — 週パネル（イベント/記録/週を開く）；
    - `WeekGridViewModel.kt` — 状態：週、イベント、記録、設定。
  - `ui/entries/` —「Journal」タブ：すべての記録、検索、CRUD。
  - `ui/events/` —「Events」タブ：色付きリスト、固定された「Birthday」。
  - `ui/profile/` —「Profile」タブ：統計 + 設定 + バックアップ。
  - `ui/onboarding/` — 初回起動：生年月日の入力を求める。
  - `ui/week/` — 週画面（「Today」タブとマップからのナビゲーション）。
  - `ui/common/` — 共有の記録ダイアログ、日付選択ダイアログ、共通の色（例：「Birthday」の緑）。
  - `util/LanguageManager.kt` — アプリ言語の処理（デバイスの地域 + プロフィールでの手動選択）。
  - `data/` — Room（エンティティ、DAO、データベース）、DataStore（設定）、`BackupManager.kt`（JSON バックアップ）。
  - `util/Dates.kt` — 日付と週の計算。

## プロジェクトを支援する

Life Calendar は空き時間に作成・維持されています。アプリは無料で広告もありません。もし役に立っているなら——開発を支援してください：

- ⭐ **GitHub でスター** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **バグ報告やアイデア** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **他の人に伝える** — 役に立つかもしれない人にシェア

**金銭的支援：**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**暗号通貨：**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

Life Calendar をご利用いただきありがとうございます！

## ライセンス

MIT。詳細は `LICENSE` を参照してください。
