package com.prev85.lifecalendar.ui.week

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Today
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prev85.lifecalendar.LifeCalendarApp
import com.prev85.lifecalendar.R
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import com.prev85.lifecalendar.ui.common.EntryDialog
import com.prev85.lifecalendar.ui.common.EventDialog
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekScreen(
    monday: String,
    onBack: () -> Unit,
    showBack: Boolean = true,
) {
    var currentMonday by remember { mutableStateOf(Dates.parse(monday)) }
    val viewModel: WeekViewModel = viewModel(
        key = currentMonday.toString(),
        factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as LifeCalendarApp
                WeekViewModel(app, Dates.iso(currentMonday))
            }
        }
    )

    val entries by viewModel.entries.collectAsStateWithLifecycle()
    val events by viewModel.events.collectAsStateWithLifecycle()
    var editing by remember { mutableStateOf<Entry?>(null) }
    var editingEvent by remember { mutableStateOf<Event?>(null) }
    var adding by remember { mutableStateOf(false) }
    val isFutureWeek = currentMonday.isAfter(Dates.mondayOf(LocalDate.now()))

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Dates.formatWeekRange(currentMonday)) },
                navigationIcon = {
                    if (showBack) {
                        IconButton(onClick = onBack) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { currentMonday = Dates.mondayOf(LocalDate.now()) }) {
                        Icon(Icons.Filled.Today, contentDescription = stringResource(R.string.today_desc))
                    }
                    IconButton(onClick = { currentMonday = currentMonday.minusWeeks(1) }) {
                        Icon(Icons.Filled.ChevronLeft, contentDescription = stringResource(R.string.prev_week_desc))
                    }
                    IconButton(onClick = { currentMonday = currentMonday.plusWeeks(1) }) {
                        Icon(Icons.Filled.ChevronRight, contentDescription = stringResource(R.string.next_week_desc))
                    }
                }
            )
        },
        floatingActionButton = {
            if (!isFutureWeek) {
                FloatingActionButton(onClick = { adding = true }) {
                    Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.add_entry))
                }
            }
        }
    ) { padding ->
        if (events.isEmpty() && entries.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.week_empty))
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (events.isNotEmpty()) {
                    item {
                        SectionHeader(stringResource(R.string.events_section))
                    }
                    items(events, key = { "e${it.id}" }) { event ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { editingEvent = event },
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
                            IconButton(onClick = { viewModel.deleteEvent(event) }) {
                                Icon(
                                    Icons.Filled.Delete,
                                    contentDescription = stringResource(R.string.delete),
                                    tint = MaterialTheme.colorScheme.outline
                                )
                            }
                        }
                    }
                }
                if (entries.isNotEmpty()) {
                    item {
                        SectionHeader(stringResource(R.string.entries_section))
                    }
                    items(entries, key = { it.id }) { entry ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { editing = entry }
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
                            IconButton(onClick = { viewModel.deleteEntry(entry) }) {
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

    if (adding) {
        EntryDialog(
            entry = null,
            defaultDate = currentMonday,
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
}

@Composable
private fun SectionHeader(text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
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
