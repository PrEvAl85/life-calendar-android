package com.prev85.lifecalendar.ui.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.prev85.lifecalendar.ui.common.BIRTHDAY_COLOR
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate

private const val ROWS_PER_CARD = 4
private val CARD_HEIGHT = 136.dp

private data class AnnounceRow(
    val date: String,
    val text: String,
    val color: Color?,
)

/** Карточка-анонс: заголовок + фиксированная область из 4 строк (события, затем записи). */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AnnounceCard(
    title: String,
    rows: List<AnnounceRow>,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(CARD_HEIGHT)
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(4.dp))
            if (rows.isEmpty()) {
                Text(
                    text = "Нет событий и записей",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            } else {
                val extra = rows.size - (ROWS_PER_CARD - 1)
                val visibleCount = if (extra > 0) ROWS_PER_CARD - 1 else rows.size
                rows.take(visibleCount).forEach { row ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (row.color != null) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(row.color, shape = CircleShape)
                            )
                            Spacer(Modifier.width(6.dp))
                        }
                        Text(
                            text = "${row.date}  ${row.text}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (row.color != null) {
                                MaterialTheme.colorScheme.onSurface
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            },
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (extra > 0) {
                    Text(
                        text = "+$extra ещё…",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

/** События и записи, чья дата попадает в [range], в хронологическом порядке. */
private fun announceRows(
    state: WeekGridViewModel.UiState,
    range: IntRange,
): List<AnnounceRow> {
    val birth = state.birthDate
    val eventRows = state.events
        .filter { LocalDate.parse(it.date).year in range }
        .map { AnnounceRow(Dates.ddmmyyyy(LocalDate.parse(it.date)), it.title, Color(it.color)) }
        .toMutableList()
    if (birth != null && birth.year in range) {
        eventRows.add(
            AnnounceRow(Dates.ddmmyyyy(birth), "День рождения", BIRTHDAY_COLOR)
        )
    }
    eventRows.sortBy { it.date }
    val entryRows = state.entries
        .filter { LocalDate.parse(it.date).year in range }
        .map { e -> AnnounceRow(Dates.ddmmyyyy(LocalDate.parse(e.date)), e.text.replace('\n', ' ').trim(), null) }
        .sortedBy { it.date }
    return eventRows + entryRows
}

/** Уровень «10 лет»: карточки десятилетий от года рождения до конца жизни. */
@Composable
fun DecadeList(
    state: WeekGridViewModel.UiState,
    onDecade: (IntRange) -> Unit,
) {
    val birth = state.birthDate ?: return
    val startYear = birth.year
    val endYear = birth.year + state.lifespanYears
    val decades = remember(startYear, endYear) {
        buildList {
            var s = startYear
            while (s <= endYear) {
                add(s..minOf(s + 9, endYear))
                s += 10
            }
        }
    }
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(decades, key = { it.first }) { range ->
            val title = if (range.first == range.last) {
                range.first.toString()
            } else {
                "${range.first}–${range.last}"
            }
            AnnounceCard(
                title = title,
                rows = announceRows(state, range),
                onClick = { onDecade(range) }
            )
        }
    }
}

/** Уровень «Декада»: карточки годов выбранного десятилетия. */
@Composable
fun DecadeYearList(
    state: WeekGridViewModel.UiState,
    range: IntRange,
    onBack: () -> Unit,
    onYear: (Int) -> Unit,
) {
    val birthYear = state.birthDate?.year ?: range.first
    val currentYear = LocalDate.now().year
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "К десятилетиям")
            }
            Text(
                text = if (range.first == range.last) {
                    range.first.toString()
                } else {
                    "${range.first}–${range.last}"
                },
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.weight(1f)
            )
            Spacer(Modifier.size(48.dp))
        }
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(range.toList(), key = { it }) { year ->
                AnnounceCard(
                    title = "$year — ${ageLabel(birthYear, year, currentYear)}",
                    rows = announceRows(state, year..year),
                    onClick = { onYear(year) }
                )
            }
        }
    }
}

/** Сколько лет исполнилось в [year]; будущие года помечаются. */
private fun ageLabel(birthYear: Int, year: Int, currentYear: Int): String {
    val age = year - birthYear
    val base = if (age == 0) "рождение" else "$age ${pluralYears(age)}"
    return if (year > currentYear) "$base · будущее" else base
}

private fun pluralYears(n: Int): String {
    val m10 = n % 10
    val m100 = n % 100
    return when {
        m100 in 11..14 -> "лет"
        m10 == 1 -> "год"
        m10 in 2..4 -> "года"
        else -> "лет"
    }
}
