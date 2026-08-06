# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

**Life Calendar** Android 应用将你的一生显示为周网格——从你的出生日期到预期寿命的终点。按十年和年份方便地概览，还有日记条目、事件和统计数据。所有内容都存储在设备本地。

基于 WaitButWhy 的 [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) 理念。

## 功能

### 选项卡

应用由 5 个选项卡组成（底部导航）：

- **今天** — 当前周屏幕：条目和事件，相邻周之间的导航（← →），“今天”按钮。
- **地图** — 整个人生的概览：**“10 年 | 年份”**切换器。
- **日志** — 所有条目为单个列表：最新的在前，按文本搜索，创建/编辑/删除。
- **事件** — 带颜色的事件列表；**“生日”固定在顶部**（自动，根据设置中的出生日期）。
- **个人资料** — 生活统计 + 所有设置 + 备份，在一个屏幕中。

### 地图：“10 年”模式

- 从出生年份开始的年代卡片：“1985–1994”、“1995–2004”、……（最后一张不完整）。
- 每张卡片有 **4 行公告**：先是事件（彩色圆点 + 日期 + 名称），然后是条目（日期 + 文本）。如果公告更多——“+N more…”。
- **你的生日会自动出现在出生年份的事件中**（绿色，无需数据库条目）。
- 点击年代 → 其中的年份卡片。每张卡片：**“年份 — 年龄”**，带正确的词形（“1991 — 6 years”、“2026 — 41 years”，出生年份——“1985 — birth”，未来——“2031 — 46 years · future”）。
- 点击年份 → 该年份的“年份”部分。

### 地图：“年份”模式

- 所选年份所有周的卡片，按月分组，带**粘性“January”…“December”页眉**。
- 每张卡片显示：周日期、彩色圆点表示的事件、条目预览、“今天”高亮。
- 点击一周 → 详情面板。
- 点击“Year 2026”→ 快速选择从出生到当前任意年份。

### 周面板（BottomSheet）

点击地图/年份上已度过的一周会打开一个面板：周日期、**“Open week”**按钮（全屏）、**“Add entry”**，以及“Events”和“Entries”部分（点击条目可编辑，垃圾桶图标可删除）。

### 未来不可用

- 点击未来周会被忽略；在“年份”模式下它们会变暗并标记为“Future”。
- 未来周上隐藏添加条目按钮；在条目日历中选择未来日期被禁止。

### 其他

- **首次启动引导**：在你可以开始之前要求输入出生日期。
- **备份**：通过系统的文件保存/打开对话框将所有数据导出为 JSON（并重新导入）。
- **深色和浅色主题** — 跟随系统。
- **多语言**：应用语言跟随设备区域（17 种翻译加英语），也可以在“个人资料 → 语言”中手动更改。
- 所有数据仅存储在本地（Room + DataStore），不使用互联网。

## 开始使用

### 首次启动

1. 安装 APK（见下方“安装”）。
2. 应用会要求你输入**出生日期**——此步骤是必须的。所有计算都基于它：年代、年份卡片上的年龄、统计数据。
3. 你可以随时在**“个人资料 → 出生日期”**中更改。

### 日常使用

- **为今天写一条记录** — 三种方式：
  - **今天**选项卡 → “+”按钮（FAB）；
  - **日志**选项卡 → “+”按钮（FAB）；
  - **地图** → 点击所需周 →“添加条目”。
- **查找旧记录** — **日志**选项卡，按文本搜索。
- **添加事件**（朋友的生日、纪念日等）— **事件**选项卡 → “+”按钮 → 名称、颜色、日期。
- **查看 10 年前发生了什么** — **地图** → “10 年”模式 → 点击“1995–2004”年代 → 点击所需年份 → 该年份的“年份”部分，或直接查看年份卡片上的公告。
- **估算你的人生进度** — **个人资料**选项卡：年龄（年/周）、已度过的人生百分比、剩余周数、条目和事件的数量。
- **将数据移动到另一台设备** — **个人资料 → 备份**：导出 JSON → 发送文件 → 在新设备上导入 JSON。

### 颜色和标签的含义

- 绿色圆点 — “生日”（始终是虚拟的，不能删除）。
- 其他颜色的圆点 — 你的事件；颜色在创建时选择。
- “10 年”卡片中没有圆点的行 — 日记条目。
- “+N more…” — 卡片放不下全部内容，请打开年份/年代卡片。
- 年份卡片上的“· future”— 该年份尚未到来；在此之前其公告为空。

## 安装

1. 从 [Releases](https://github.com/PrEvAl85/life-calendar-android/releases) 下载 `life-calendar-vX.Y.Z.apk` 文件。
2. 在设备上打开该文件并确认从未知来源安装（系统会提示你）。

### Play Protect 和未签名 APK

该 APK 未经 Google Play 签名，也未通过 Google Play Protect 验证——首次安装时 Android 可能显示“Play Protect 阻止了该应用”的警告，或要求你确认安装。该文件是安全的：它是本仓库开源代码的构建产物。

- 如何安装：在 Play Protect 对话框中选择**“更多详情” → “仍然安装”**（一次性），或在 Android 设置中：**安全 → 未知应用安装 → 允许**你的文件管理器/浏览器。
- 仅从本仓库的 **Releases** 部分下载 APK。

## 从源码构建

要求：JDK 17、Android SDK（compileSdk 35）、Gradle 8.10.2（仓库包含 wrapper）。

```
gradlew assembleRelease
```

签名后的 APK 将出现在 `app/build/outputs/apk/release/app-release.apk`。

注意：如果项目路径包含非 ASCII 字符，Gradle 可能拒绝构建（`StopExecutionException: ... non-ASCII characters`）。`android.overridePathCheck=true` 标志已添加到 `gradle.properties`，可解决此问题。

## 项目结构

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — 入口点，未捕获异常处理器（日志在 `filesDir/crash.log`）。
  - `ui/AppNav.kt` — 导航和带 5 个选项卡的底部栏（今天 · 地图 · 日志 · 事件 · 个人资料）。
  - `ui/grid/` — “地图”选项卡：
    - `DecadeOverviewScreen.kt` — 带公告和年龄的年代与年份卡片；
    - `YearOverviewScreen.kt` — 带 `stickyHeader` 的按月年度概览；
    - `YearPickerSheet.kt` — 快速年份选择器；
    - `WeekDetailSheet.kt` — 周面板（事件/条目/打开周）；
    - `WeekGridViewModel.kt` — 状态：周、事件、条目、设置。
  - `ui/entries/` — “日志”选项卡：所有条目、搜索、增删改查。
  - `ui/events/` — “事件”选项卡：带颜色的列表，固定的“生日”。
  - `ui/profile/` — “个人资料”选项卡：统计 + 设置 + 备份。
  - `ui/onboarding/` — 首次启动：要求输入出生日期。
  - `ui/week/` — 周屏幕（“今天”选项卡和从地图导航）。
  - `ui/common/` — 共享条目对话框、日期选择器对话框、通用颜色（例如“生日”的绿色）。
  - `util/LanguageManager.kt` — 应用语言处理（设备区域 + 个人资料中的手动选择）。
  - `data/` — Room（实体、DAO、数据库）、DataStore（设置）、`BackupManager.kt`（JSON 备份）。
  - `util/Dates.kt` — 日期和周的计算。

## 支持项目

Life Calendar 是在空闲时间创建和维护的；该应用免费且无广告。如果它对你有用——帮助它发展：

- ⭐ **在 GitHub 上加星** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **错误报告和想法** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **告诉他人** — 分享给可能觉得有用的人

**财务支持：**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**加密货币：**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

感谢你使用 Life Calendar！

## 许可证

MIT。详情见 `LICENSE`。
