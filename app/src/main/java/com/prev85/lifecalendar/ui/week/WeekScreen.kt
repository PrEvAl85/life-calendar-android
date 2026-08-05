package com.prev85.lifecalendar.ui.week

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prev85.lifecalendar.LifeCalendarApp
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.ui.common.EntryDialog
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeekScreen(
    monday: String,
    onBack: () -> Unit,
    viewModel: WeekViewModel = viewModel(
        factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as LifeCalendarApp
                WeekViewModel(app, monday)
            }
        }
    )
) {
    val entries by viewModel.entries.collectAsStateWithLifecycle()
    var editing by remember { mutableStateOf<Entry?>(null) }
    var adding by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(Dates.formatWeekRange(viewModel.mondayDate)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { adding = true }) {
                Icon(Icons.Filled.Add, contentDescription = "Добавить запись")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (entries.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Записей за неделю нет",
                        modifier = Modifier.align(androidx.compose.ui.Alignment.CenterHorizontally)
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(entries, key = { it.id }) { entry ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
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
                                    contentDescription = "Удалить",
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
            defaultDate = viewModel.mondayDate,
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
            onDismiss = { editing = null },
            onSave = { date, text ->
                viewModel.updateEntry(entry.copy(date = Dates.iso(date), text = text))
                editing = null
            }
        )
    }
}
