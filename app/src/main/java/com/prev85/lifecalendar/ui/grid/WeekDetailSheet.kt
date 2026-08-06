package com.prev85.lifecalendar.ui.grid

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.prev85.lifecalendar.R
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekDetailSheet(
    monday: LocalDate,
    weekNumber: Int,
    events: List<Event>,
    entries: List<Entry>,
    onDismiss: () -> Unit,
    onAddEntry: () -> Unit,
    onEditEntry: (Entry) -> Unit,
    onDeleteEntry: (Entry) -> Unit,
    onEditEvent: (Event) -> Unit,
    onDeleteEvent: (Event) -> Unit,
    onOpenWeek: () -> Unit,
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 24.dp)
        ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ) {
                Text(
                    text = Dates.formatWeekRange(monday),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = stringResource(R.string.week_number_label, weekNumber),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
                Spacer(Modifier.height(12.dp))
                Button(onClick = onOpenWeek, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Filled.OpenInNew, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.open_week))
                }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = onAddEntry, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Filled.Add, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.add_entry))
                }
            }

            if (events.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                SheetSectionHeader(stringResource(R.string.events_section))
                events.forEach { event ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .then(
                                    if (event.id >= 0) {
                                        Modifier.clickable { onEditEvent(event) }
                                    } else {
                                        Modifier
                                    }
                                ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .background(Color(event.color), shape = CircleShape)
                            )
                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(
                                    text = event.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    text = Dates.ddmmyyyy(LocalDate.parse(event.date)),
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                        if (event.id >= 0) {
                            IconButton(onClick = { onDeleteEvent(event) }) {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = stringResource(R.string.delete),
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
            }

            if (entries.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                SheetSectionHeader(stringResource(R.string.entries_section))
                entries.forEach { entry ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .clickable { onEditEntry(entry) }
                        ) {
                            Text(
                                text = Dates.ddmmyyyy(LocalDate.parse(entry.date)),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = entry.text,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        IconButton(onClick = { onDeleteEntry(entry) }) {
                            Icon(
                                Icons.Filled.Delete,
                                contentDescription = stringResource(R.string.delete),
                                tint = MaterialTheme.colorScheme.outline
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun SheetSectionHeader(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outlineVariant
        )
        Text(
            text = "  $text  ",
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colorScheme.outlineVariant
        )
    }
}
