package com.prev85.lifecalendar.ui.entries

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prev85.lifecalendar.R
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.ui.common.EntryDialog
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntriesScreen(
    viewModel: EntriesViewModel = viewModel()
) {
    val entries by viewModel.entries.collectAsStateWithLifecycle()
    var query by remember { mutableStateOf("") }
    var editing by remember { mutableStateOf<Entry?>(null) }
    var adding by remember { mutableStateOf(false) }

    val q = query.trim()
    val filtered = if (q.isEmpty()) entries else entries.filter {
        it.text.contains(q, ignoreCase = true)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(R.string.diary_title)) })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { adding = true }) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.new_entry_desc))
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                placeholder = { Text(stringResource(R.string.search_hint)) },
                leadingIcon = {
                    Icon(Icons.Filled.Search, contentDescription = null)
                },
                singleLine = true
            )

            if (filtered.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        when {
                            entries.isEmpty() -> stringResource(R.string.no_entries_yet)
                            else -> stringResource(R.string.nothing_found)
                        }
                    )
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(filtered, key = { it.id }) { entry ->
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
            defaultDate = LocalDate.now(),
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
            onDismiss = { editing = null },
            onSave = { date, text ->
                viewModel.updateEntry(entry.copy(date = Dates.iso(date), text = text))
                editing = null
            }
        )
    }
}
