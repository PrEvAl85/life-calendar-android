package com.prev85.lifecalendar.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.prev85.lifecalendar.R
import com.prev85.lifecalendar.ui.common.AppDatePickerDialog
import com.prev85.lifecalendar.util.Dates
import java.time.LocalDate

@Composable
fun OnboardingScreen(
    onDone: () -> Unit,
    viewModel: OnboardingViewModel = viewModel()
) {
    var showPicker by remember { mutableStateOf(false) }
    var picked by remember { mutableStateOf<LocalDate?>(null) }

    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Favorite,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(72.dp)
            )
            Spacer(Modifier.height(16.dp))
            Text(stringResource(R.string.app_welcome_title), style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(12.dp))
            Text(
                stringResource(R.string.welcome_message),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(24.dp))
            picked?.let {
                Text(
                    stringResource(R.string.birth_date_label, Dates.ddmmyyyy(it)),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(12.dp))
            }
            Button(onClick = { showPicker = true }) {
                Text(if (picked == null) stringResource(R.string.pick_birth_date) else stringResource(R.string.change_date))
            }
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = {
                    picked?.let { date ->
                        viewModel.setBirthDate(date)
                        onDone()
                    }
                },
                enabled = picked != null,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.continue_action))
            }
        }
    }

    if (showPicker) {
        AppDatePickerDialog(
            initialDate = picked ?: LocalDate.of(1990, 1, 1),
            onDismiss = { showPicker = false },
            onConfirm = { date ->
                picked = date
                showPicker = false
            }
        )
    }
}
