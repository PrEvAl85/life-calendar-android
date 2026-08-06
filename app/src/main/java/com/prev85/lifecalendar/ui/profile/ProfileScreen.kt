package com.prev85.lifecalendar.ui.profile

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prev85.lifecalendar.R
import com.prev85.lifecalendar.ui.common.AppDatePickerDialog
import com.prev85.lifecalendar.util.Dates
import com.prev85.lifecalendar.util.LanguageManager
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = viewModel()
) {
    val stats by viewModel.stats.collectAsStateWithLifecycle()
    val settings by viewModel.settings.collectAsStateWithLifecycle()
    val snackbar = remember { SnackbarHostState() }
    var showBirthPicker by remember { mutableStateOf(false) }
    var showLanguageSheet by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val currentLang = remember { LanguageManager.current(context) }

    val exportLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.CreateDocument("application/json")
    ) { uri ->
        if (uri != null) viewModel.exportTo(uri)
    }
    val importLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) viewModel.importFrom(uri)
    }

    LaunchedEffect(settings.message) {
        settings.message?.let {
            snackbar.showSnackbar(it)
            viewModel.consumeMessage()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.profile_title)) })
        },
        snackbarHost = { SnackbarHost(snackbar) }
    ) { padding ->
        if (stats.birthDate == null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.set_birth_hint))
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.age), style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "${stats.ageYears} ${pluralStringResource(R.plurals.years, stats.ageYears.toInt())} · " +
                                "${stats.ageMonths} ${pluralStringResource(R.plurals.months, stats.ageMonths.toInt())} · " +
                                "${stats.ageDays} ${pluralStringResource(R.plurals.days, stats.ageDays.toInt())}",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(R.string.born_prefix) + " ${Dates.ddmmyyyy(stats.birthDate!!)} · " +
                                stringResource(R.string.allotted_label) + " ${stats.lifespanYears} " +
                                pluralStringResource(R.plurals.years, stats.lifespanYears),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.lived_percent_title), style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "${(stats.percentLived * 100).toInt()}%",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { stats.percentLived },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = stringResource(R.string.lived_weeks_of, stats.livedWeeks, stats.totalWeeks) + " " +
                                pluralStringResource(R.plurals.weeks, stats.totalWeeks) + " · " +
                                stringResource(R.string.weeks_left_label) + " ${stats.weeksLeft} " +
                                pluralStringResource(R.plurals.weeks, stats.weeksLeft),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                Spacer(Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    StatCard(
                        label = stringResource(R.string.birthdays_stat),
                        value = "${stats.birthdays}",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = stringResource(R.string.entries_stat),
                        value = "${stats.entriesCount}",
                        modifier = Modifier.weight(1f)
                    )
                    StatCard(
                        label = stringResource(R.string.events_stat),
                        value = "${stats.eventsCount}",
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.profile_title), style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    stringResource(R.string.birth_date),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    settings.birthDate?.let { Dates.ddmmyyyy(it) } ?: stringResource(R.string.not_set),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                            TextButton(onClick = { showBirthPicker = true }) {
                                Text(stringResource(R.string.change_action))
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            stringResource(R.string.lifespan) + " ${settings.lifespanYears} " +
                                pluralStringResource(R.plurals.years, settings.lifespanYears)
                        )
                        Slider(
                            value = settings.lifespanYears.toFloat(),
                            onValueChange = { viewModel.setLifespanYears(it.toInt()) },
                            valueRange = 50f..120f,
                            steps = 69
                        )
                    }
                }

                Spacer(Modifier.height(16.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.language_title), style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(12.dp))
                        Row(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    stringResource(R.string.language_value, LanguageManager.displayName(currentLang)),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                            TextButton(onClick = { showLanguageSheet = true }) {
                                Text(stringResource(R.string.change_action))
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(stringResource(R.string.backup_title), style = MaterialTheme.typography.titleMedium)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            stringResource(R.string.backup_desc),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.outline
                        )
                        Spacer(Modifier.height(12.dp))
                        Button(
                            onClick = {
                                val stamp = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ROOT))
                                exportLauncher.launch("life-calendar-backup-$stamp.json")
                            },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !settings.busy
                        ) {
                            Text(stringResource(R.string.export_json))
                        }
                        Spacer(Modifier.height(8.dp))
                        OutlinedButton(
                            onClick = { importLauncher.launch(arrayOf("application/json", "text/plain")) },
                            modifier = Modifier.fillMaxWidth(),
                            enabled = !settings.busy
                        ) {
                            Text(stringResource(R.string.import_json))
                        }
                    }
                }
            }
        }
    }

    if (showBirthPicker) {
        AppDatePickerDialog(
            initialDate = settings.birthDate ?: LocalDate.of(1990, 1, 1),
            onDismiss = { showBirthPicker = false },
            onConfirm = { date ->
                viewModel.setBirthDate(date)
                showBirthPicker = false
            }
        )
    }

    if (showLanguageSheet) {
        LanguagePickerSheet(
            current = currentLang,
            onDismiss = { showLanguageSheet = false },
            onSelect = { lang ->
                LanguageManager.set(context, lang)
                showLanguageSheet = false
                context.findActivity()?.recreate()
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguagePickerSheet(
    current: String,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit,
) {
    val systemLabel = stringResource(R.string.language_system)
    val options = listOf(LanguageManager.SYSTEM to systemLabel) +
        LanguageManager.SUPPORTED.map { it to LanguageManager.displayName(it) }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(modifier = Modifier.padding(bottom = 24.dp)) {
            Text(
                text = stringResource(R.string.language_choose_title),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)
            )
            LazyColumn {
                items(options) { (code, label) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(code) }
                            .padding(horizontal = 20.dp, vertical = 14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = if (code == current) FontWeight.Bold else FontWeight.Normal
                        )
                        if (code == current) {
                            Text(
                                text = stringResource(R.string.current_label),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 20.dp),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }
            }
        }
    }
}

private fun Context.findActivity(): Activity? {
    var ctx = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}

@Composable
private fun StatCard(label: String, value: String, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }
    }
}
