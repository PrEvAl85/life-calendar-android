# Life Calendar

[English](README.md) | [Русский](README.ru.md) | [Українська](README.uk.md) | [Беларуская](README.be.md) | [Қазақша](README.kk.md) | [Deutsch](README.de.md) | [Français](README.fr.md) | [Español](README.es.md) | [Italiano](README.it.md) | [Português](README.pt.md) | [Polski](README.pl.md) | [Čeština](README.cs.md) | [Türkçe](README.tr.md) | [简体中文](README.zh.md) | [日本語](README.ja.md) | [한국어](README.ko.md) | [العربية](README.ar.md) | [हिन्दी](README.hi.md)

O aplicativo Android **Life Calendar** mostra toda a sua vida como uma grade de semanas — da sua data de nascimento até o fim da sua expectativa de vida. Visão geral conveniente por décadas e anos, um diário de entradas, eventos e estatísticas. Tudo é armazenado localmente no seu dispositivo.

Baseado na ideia de [Life in Weeks](https://waitbutwhy.com/2014/05/life-weeks.html) do WaitButWhy.

## Recursos

### Abas

O aplicativo consiste em 5 abas (navegação inferior):

- **Hoje** — a tela da semana atual: entradas e eventos, navegação entre semanas vizinhas (← →), o botão "Hoje".
- **Mapa** — uma visão geral de toda a sua vida: o seletor **"10 anos | Ano"**.
- **Diário** — todas as entradas em uma única lista: mais recentes primeiro, pesquisa por texto, criar/editar/excluir.
- **Eventos** — uma lista de eventos com cores; **"Aniversário" é fixado no topo** (automaticamente, a partir da data de nascimento nas configurações).
- **Perfil** — estatísticas de vida + todas as configurações + backup em uma única tela.

### Mapa: modo "10 anos"

- Cartões de década a partir do ano de nascimento: "1985–1994", "1995–2004", … (o último é parcial).
- Cada cartão tem **4 linhas de avisos**: primeiro os eventos (ponto colorido + data + nome), depois as entradas (data + texto). Se houver mais avisos — "+N mais…".
- **Seu aniversário aparece automaticamente nos eventos** do ano de nascimento (em verde, sem entrada no banco de dados).
- Toque em uma década → cartões de ano dentro dela. Cada cartão: **"Ano — idade"** com as formas corretas das palavras ("1991 — 6 anos", "2026 — 41 anos", o ano de nascimento — "1985 — nascimento", futuro — "2031 — 46 anos · futuro").
- Toque em um ano → a seção "Ano" daquele ano.

### Mapa: modo "Ano"

- Cartões para todas as semanas do ano selecionado, agrupados por meses com **cabeçalhos fixos "Janeiro"…"Dezembro"**.
- Cada cartão mostra: datas da semana, eventos como pontos coloridos, prévias de entradas, o destaque de "hoje".
- Toque em uma semana → um painel de detalhes.
- Toque em "Ano 2026" → seleção rápida de qualquer ano, do nascimento até o atual.

### Painel da semana (BottomSheet)

Tocar em uma semana vivida no mapa/no ano abre um painel: datas da semana, o botão **"Abrir semana"** (tela cheia), **"Adicionar entrada"** e as seções "Eventos" e "Entradas" (toque em uma entrada para editar, ícone de lixeira para excluir).

### O futuro está indisponível

- Toques em semanas futuras são ignorados; no modo "Ano" elas ficam esmaecidas e marcadas como "Futuro".
- O botão de adicionar entrada fica oculto nas semanas futuras; escolher datas futuras no calendário de entradas é proibido.

### Diversos

- **Onboarding no primeiro acesso**: pede a data de nascimento antes de você poder começar.
- **Backup**: exportar todos os dados para JSON (e importá-los de volta) através da caixa de diálogo do sistema para salvar/abrir arquivos.
- **Temas escuro e claro** — seguem o sistema.
- **Multilíngue**: o idioma do aplicativo segue a região do dispositivo (17 traduções além do inglês) e pode ser alterado manualmente em Perfil → Idioma.
- Todos os dados são apenas locais (Room + DataStore), sem uso da internet.

## Primeiros passos

### Primeiro acesso

1. Instale o APK (veja "Instalação" abaixo).
2. O aplicativo pedirá a sua **data de nascimento** — esta etapa é obrigatória. Todos os cálculos são baseados nela: décadas, idades nos cartões de ano, estatísticas.
3. Você pode alterá-la a qualquer momento em **Perfil → Data de nascimento**.

### Uso diário

- **Fazer uma entrada para hoje** — três maneiras:
  - a aba **Hoje** → o botão "+" (FAB);
  - a aba **Diário** → o botão "+" (FAB);
  - **Mapa** → toque na semana necessária → "Adicionar entrada".
- **Encontrar uma entrada antiga** — a aba **Diário**, pesquisa por texto.
- **Adicionar um evento** (aniversário de um amigo, um aniversário de casamento, etc.) — a aba **Eventos** → o botão "+" → nome, cor, data.
- **Ver o que aconteceu há 10 anos** — **Mapa** → modo "10 anos" → toque na década "1995–2004" → toque no ano necessário → a seção "Ano" daquele ano, ou veja logo os avisos no cartão do ano.
- **Avaliar o seu progresso de vida** — a aba **Perfil**: idade (anos/semanas), porcentagem de vida vivida, semanas restantes, o número de entradas e eventos.
- **Transferir dados para outro dispositivo** — **Perfil → Backup**: exportar JSON → enviar o arquivo → importar JSON no novo dispositivo.

### O que significam as cores e os rótulos

- Ponto verde — "Aniversário" (sempre virtual, não pode ser excluído).
- Pontos de outras cores — seus eventos; a cor é escolhida na criação.
- Linhas sem ponto nos cartões de "10 anos" — entradas do diário.
- "+N mais…" — nem tudo cabe no cartão, abra o cartão do ano/década.
- "· futuro" em um cartão de ano — o ano ainda não chegou; seus avisos ficam vazios até lá.

## Instalação

1. Baixe o arquivo `life-calendar-vX.Y.Z.apk` de [Releases](https://github.com/PrEvAl85/life-calendar-android/releases).
2. Abra o arquivo no seu dispositivo e confirme a instalação de fontes desconhecidas (o sistema solicitará).

### Play Protect e o APK não assinado

O APK não é assinado pelo Google Play e não passou pela verificação do Google Play Protect — na primeira instalação o Android pode mostrar um aviso de "Play Protect bloqueou o aplicativo" ou pedir que você confirme a instalação. O arquivo é seguro: é uma compilação do código-fonte aberto deste repositório.

- Como instalar: na caixa de diálogo do Play Protect escolha **"Mais detalhes" → "Instalar mesmo assim"** (uma vez), ou nas configurações do Android: **Segurança → Instalação de aplicativos desconhecidos → permitir** para o seu gerenciador de arquivos/navegador.
- Baixe o APK apenas da seção **Releases** deste repositório.

## Compilação a partir do código-fonte

Requisitos: JDK 17, Android SDK (compileSdk 35), Gradle 8.10.2 (o repositório inclui o wrapper).

```
gradlew assembleRelease
```

O APK assinado aparecerá em `app/build/outputs/apk/release/app-release.apk`.

Observação: se o caminho do projeto contiver caracteres não ASCII, o Gradle pode se recusar a compilar (`StopExecutionException: ... non-ASCII characters`). A flag `android.overridePathCheck=true` já está adicionada em `gradle.properties`, o que resolve esse problema.

## Estrutura do projeto

- `app/src/main/java/com/prev85/lifecalendar/`
  - `MainActivity.kt` — ponto de entrada, handler para exceções não capturadas (log em `filesDir/crash.log`).
  - `ui/AppNav.kt` — navegação e a barra inferior com 5 abas (Hoje · Mapa · Diário · Eventos · Perfil).
  - `ui/grid/` — a aba "Mapa":
    - `DecadeOverviewScreen.kt` — cartões de década e ano com avisos e idades;
    - `YearOverviewScreen.kt` — a visão anual por meses com um `stickyHeader`;
    - `YearPickerSheet.kt` — seletor rápido de ano;
    - `WeekDetailSheet.kt` — o painel da semana (eventos/entradas/abrir semana);
    - `WeekGridViewModel.kt` — estado: semanas, eventos, entradas, configurações.
  - `ui/entries/` — a aba "Diário": todas as entradas, pesquisa, CRUD.
  - `ui/events/` — a aba "Eventos": uma lista com cores, o "Aniversário" fixado.
  - `ui/profile/` — a aba "Perfil": estatísticas + configurações + backup.
  - `ui/onboarding/` — primeiro acesso: solicitação da data de nascimento.
  - `ui/week/` — a tela da semana (a aba "Hoje" e a navegação a partir do mapa).
  - `ui/common/` — diálogo compartilhado de entradas, diálogo de seletor de data, cores comuns (ex.: verde para "Aniversário").
  - `util/LanguageManager.kt` — manipulação do idioma do aplicativo (região do dispositivo + escolha manual no Perfil).
  - `data/` — Room (entidades, DAO, banco de dados), DataStore (configurações), `BackupManager.kt` (backup em JSON).
  - `util/Dates.kt` — cálculos de datas e semanas.

## Apoie o Projeto

O Life Calendar é criado e mantido no tempo livre; o aplicativo é gratuito e sem anúncios. Se ele é útil para você — ajude o seu desenvolvimento:

- ⭐ **Dê uma estrela no GitHub** — [PrEvAl85/life-calendar-android](https://github.com/PrEvAl85/life-calendar-android)
- 🐛 **Relatórios de bugs e ideias** — [Issues](https://github.com/PrEvAl85/life-calendar-android/issues)
- 💬 **Conte para outras pessoas** — compartilhe com quem pode achar útil

**Apoio financeiro:**

- ☕ **Boosty** — https://boosty.to/pws/donate
- 🍩 **DonationAlerts** — https://www.donationalerts.com/r/photowithoutstudio

**Criptomoedas:**

- USDT (TRC20): `TRcWS42MhyFRGdGSc6LqTH8CdTy6pLUMn6`
- USDT (BEP20): `0x0905134db34d8d54abf5b60a55406821ed7b8de0`
- BTC: `17hDrZL62DBpTjK6xNCGFFG682jN9PiVF1`
- TON: `UQCzoPJlYLHSoFGmRyh_-_ox1nOMCzx3LwG79xPR5pbjs3Aq`

Obrigado por usar o Life Calendar!

## Licença

MIT. Detalhes em `LICENSE`.
