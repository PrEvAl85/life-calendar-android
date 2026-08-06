package com.prev85.lifecalendar.ui.grid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prev85.lifecalendar.R
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import com.prev85.lifecalendar.ui.common.BIRTHDAY_COLOR_ARGB
import com.prev85.lifecalendar.ui.common.EntryDialog
import com.prev85.lifecalendar.ui.common.EventDialog
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate
import java.time.Period
import java.time.temporal.ChronoUnit

private enum class MapViewMode { DECADES, YEAR }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekGridScreen(
    onWeekClick: (String) -> Unit,
    viewModel: WeekGridViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    var viewMode by remember { mutableStateOf(MapViewMode.DECADES) }
    var decadeRange by remember { mutableStateOf<IntRange?>(null) }
    var year by remember { mutableIntStateOf(LocalDate.now().year) }
    var sheetMonday by remember { mutableStateOf<LocalDate?>(null) }
    var editing by remember { mutableStateOf<Entry?>(null) }
    var editingEvent by remember { mutableStateOf<Event?>(null) }
    var adding by remember { mutableStateOf(false) }
    var addDate by remember { mutableStateOf(LocalDate.now()) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.map_title)) })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            state.birthDate?.let { birth ->
                HeaderRow(birth = birth, state = state)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                SingleChoiceSegmentedButtonRow {
                    SegmentedButton(
                        selected = viewMode == MapViewMode.DECADES,
                        onClick = { viewMode = MapViewMode.DECADES },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2)
                    ) {
                        Text(stringResource(R.string.decades_tab))
                    }
                    SegmentedButton(
                        selected = viewMode == MapViewMode.YEAR,
                        onClick = { viewMode = MapViewMode.YEAR },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2)
                    ) {
                        Text(stringResource(R.string.year_tab))
                    }
                }
                Spacer(Modifier.weight(1f))
            }

            when {
                state.birthDate == null -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.set_birth_hint))
                }
                viewMode == MapViewMode.DECADES -> {
                    val range = decadeRange
                    if (range == null) {
                        DecadeList(
                            state = state,
                            onDecade = { decadeRange = it }
                        )
                    } else {
                        DecadeYearList(
                            state = state,
                            range = range,
                            onBack = { decadeRange = null },
                            onYear = { y ->
                                year = y
                                viewMode = MapViewMode.YEAR
                            }
                        )
                    }
                }
                else -> YearOverview(
                    state = state,
                    year = year,
                    onYearChange = { year = it },
                    onSheet = { sheetMonday = it }
                )
            }
        }
    }

    if (adding) {
        EntryDialog(
            entry = null,
            defaultDate = addDate,
            maxDate = LocalDate.now(),
            onDismiss = { adding = false },
            onSave = { date, text ->
                viewModel.addEntry(date, text)
                adding = false
            }
        )
    }
    editing?.let { entry ->
        EntryDialog(
            entry = entry,
            defaultDate = LocalDate.parse(entry.date),
            maxDate = LocalDate.now(),
            onDismiss = { editing = null },
            onSave = { date, text ->
                viewModel.updateEntry(entry.copy(date = Dates.iso(date), text = text))
                editing = null
            }
        )
    }
    editingEvent?.let { event ->
        EventDialog(
            event = event,
            defaultDate = LocalDate.parse(event.date),
            onDismiss = { editingEvent = null },
            onSave = { date, title, color ->
                viewModel.updateEvent(event.copy(date = Dates.iso(date), title = title, color = color))
                editingEvent = null
            }
        )
    }

    sheetMonday?.let { monday ->
        val wk = Dates.iso(monday)
        WeekDetailSheet(
            monday = monday,
            weekNumber = state.weekKeys.indexOf(monday) + 1,
            events = state.eventsByWeek[wk].orEmpty() + listOfNotNull(
                birthdayEvent(state, monday, stringResource(R.string.birthday_event))
            ),
            entries = state.entriesByWeek[wk].orEmpty(),
            onDismiss = { sheetMonday = null },
            onAddEntry = {
                addDate = monday
                adding = true
            },
            onEditEntry = { editing = it },
            onDeleteEntry = { viewModel.deleteEntry(it) },
            onEditEvent = { if (it.id >= 0) editingEvent = it },
            onDeleteEvent = { if (it.id >= 0) viewModel.deleteEvent(it) },
            onOpenWeek = {
                sheetMonday = null
                onWeekClick(wk)
            }
        )
    }
}

@Composable
private fun HeaderRow(birth: LocalDate, state: WeekGridViewModel.UiState) {
    val today = LocalDate.now()
    val years = Period.between(birth, today).years
    val lastBday = birth.plusYears(years.toLong())
    val weeks = ChronoUnit.DAYS.between(lastBday, today) / 7
    val total = state.weekKeys.size
    val lived = if (total > 0) {
        (ChronoUnit.DAYS.between(birth, today) / 7).toInt().coerceIn(0, total)
    } else 0
    val percent = if (total > 0) lived * 100 / total else 0

    Text(
        text = stringResource(R.string.map_header_bd) + " ${Dates.ddmmyyyy(birth)} · $years " +
            stringResource(R.string.years_short) + " $weeks " + stringResource(R.string.weeks_short) +
            " · " + stringResource(R.string.lived_label) + " $percent% · " +
            stringResource(R.string.entries_label) + ": ${state.entriesByWeek.size} " +
            stringResource(R.string.weeks_short),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}

private fun birthdayEvent(
    state: WeekGridViewModel.UiState,
    monday: LocalDate,
    title: String,
): Event? {
    val birth = state.birthDate ?: return null
    return if (!birth.isBefore(monday) && !birth.isAfter(monday.plusDays(6))) {
        Event(
            id = -1L,
            date = Dates.iso(birth),
            title = title,
            color = BIRTHDAY_COLOR_ARGB
        )
    } else null
}
