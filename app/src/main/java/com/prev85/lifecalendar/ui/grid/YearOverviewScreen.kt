package com.prev85.lifecalendar.ui.grid

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import com.prev85.lifecalendar.ui.common.BIRTHDAY_COLOR_ARGB
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate

private val MONTH_NAMES = listOf(
    "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
    "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun YearOverview(
    state: WeekGridViewModel.UiState,
    year: Int,
    onYearChange: (Int) -> Unit,
    onSheet: (LocalDate) -> Unit,
) {
    val weeks = remember(year) { Dates.weeksOfYear(year) }
    val currentYear = LocalDate.now().year
    val todayMonday = remember { Dates.mondayOf(LocalDate.now()) }
    var showYearPicker by remember { mutableStateOf(false) }

    val months = remember(weeks, year) {
        weeks.groupBy { monday ->
            if (monday.year < year) 1 else monday.monthValue
        }.toSortedMap()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onYearChange(year - 1) }) {
                Icon(Icons.Filled.ChevronLeft, contentDescription = "Прошлый год")
            }
            Text(
                text = "Год $year",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .weight(1f)
                    .clickable { showYearPicker = true }
            )
            IconButton(onClick = { onYearChange(year + 1) }) {
                Icon(Icons.Filled.ChevronRight, contentDescription = "Следующий год")
            }
        }
        if (year != currentYear) {
            TextButton(
                onClick = { onYearChange(currentYear) },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("К текущему году")
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            months.forEach { (month, mondays) ->
                stickyHeader(key = "m$month") {
                    MonthHeader(MONTH_NAMES[month - 1])
                }
                items(mondays, key = { Dates.iso(it) }) { monday ->
                    val wk = Dates.iso(monday)
                    val events = state.eventsByWeek[wk].orEmpty().toMutableList()
                    birthdayEvent(state, monday)?.let { events.add(it) }
                    val entries = state.entriesByWeek[wk].orEmpty()
                    val isToday = wk == state.todayKey
                    val isFuture = monday.isAfter(todayMonday)
                    WeekCard(
                        monday = monday,
                        events = events,
                        entries = entries,
                        isToday = isToday,
                        isFuture = isFuture,
                        onClick = { onSheet(monday) }
                    )
                }
            }
        }
    }

    if (showYearPicker) {
        val birthYear = state.weekKeys.firstOrNull()?.year ?: year
        YearPickerSheet(
            years = (birthYear..currentYear).toList(),
            selected = year,
            onDismiss = { showYearPicker = false },
            onSelect = {
                onYearChange(it)
                showYearPicker = false
            }
        )
    }
}

@Composable
private fun MonthHeader(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

private fun birthdayEvent(
    state: WeekGridViewModel.UiState,
    monday: LocalDate,
): Event? {
    val birth = state.birthDate ?: return null
    return if (!birth.isBefore(monday) && !birth.isAfter(monday.plusDays(6))) {
        Event(
            id = -1L,
            date = Dates.iso(birth),
            title = "День рождения",
            color = BIRTHDAY_COLOR_ARGB
        )
    } else null
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeekCard(
    monday: LocalDate,
    events: List<Event>,
    entries: List<Entry>,
    isToday: Boolean,
    isFuture: Boolean,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        enabled = !isFuture,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isToday) {
                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
            }
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = Dates.formatWeekRange(monday),
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                events.forEach { event ->
                    Row(
                        modifier = Modifier.padding(top = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .background(Color(event.color), shape = CircleShape)
                        )
                        Text(
                            text = "  ${event.title}",
                            style = MaterialTheme.typography.bodyMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(start = 2.dp)
                        )
                    }
                }
                val preview = entries.firstOrNull()?.text?.replace('\n', ' ')?.trim().orEmpty()
                if (preview.isNotEmpty()) {
                    Text(
                        text = preview,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                    if (entries.size > 1) {
                        Text(
                            text = "+${entries.size - 1} записей",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    Text(
                        text = if (isFuture) "Будущее" else "Нет записей",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
            if (isToday) {
                Text(
                    text = "сегодня",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
