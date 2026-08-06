package com.prev85.lifecalendar.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.prev85.lifecalendar.R
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EntryDialog(
    entry: Entry?,
    defaultDate: LocalDate,
    maxDate: LocalDate? = null,
    onDismiss: () -> Unit,
    onSave: (LocalDate, String) -> Unit,
) {
    var text by remember { mutableStateOf(entry?.text ?: "") }
    var date by remember { mutableStateOf(defaultDate) }
    var showDatePicker by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (entry == null) stringResource(R.string.new_entry_dialog) else stringResource(R.string.edit_entry_dialog)) },
        text = {
            Column {
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
                TextButton(onClick = { showDatePicker = true }) {
                    Text(stringResource(R.string.date_label, Dates.ddmmyyyy(date)))
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val t = text.trim()
                    if (t.isNotEmpty()) onSave(date, t)
                }
            ) { Text(stringResource(R.string.save)) }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) }
        }
    )

    if (showDatePicker) {
        AppDatePickerDialog(
            initialDate = date,
            maxDate = maxDate,
            onDismiss = { showDatePicker = false },
            onConfirm = { newDate ->
                date = newDate
                showDatePicker = false
            }
        )
    }
}
