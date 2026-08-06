package com.prev85.lifecalendar.ui.settings

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prev85.lifecalendar.LifeCalendarApp
import com.prev85.lifecalendar.R
import com.prev85.lifecalendar.data.BackupManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class SettingsViewModel(app: Application) : AndroidViewModel(app) {

    private val ctx = app as LifeCalendarApp
    val backup = BackupManager(ctx, ctx.database, ctx.settings)

    data class UiState(
        val birthDate: LocalDate? = null,
        val lifespanYears: Int = 100,
        val busy: Boolean = false,
        val message: String? = null,
    )

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state

    init {
        viewModelScope.launch {
            ctx.settings.birthDate.collect { s ->
                _state.update {
                    it.copy(birthDate = s?.let { b -> runCatching { LocalDate.parse(b) }.getOrNull() })
                }
            }
        }
        viewModelScope.launch {
            ctx.settings.lifespanYears.collect { v ->
                _state.update { it.copy(lifespanYears = v) }
            }
        }
    }

    fun setBirthDate(date: LocalDate) {
        viewModelScope.launch { ctx.settings.setBirthDate(date.toString()) }
    }

    fun setLifespanYears(years: Int) {
        viewModelScope.launch { ctx.settings.setLifespanYears(years) }
    }

    fun exportTo(uri: Uri) {
        viewModelScope.launch {
            _state.update { it.copy(busy = true) }
            runCatching { backup.exportTo(uri) }
                .onSuccess { _state.update { it.copy(message = ctx.getString(R.string.backup_saved)) } }
                .onFailure { e -> _state.update { it.copy(message = ctx.getString(R.string.backup_error, e.message ?: "")) } }
            _state.update { it.copy(busy = false) }
        }
    }

    fun importFrom(uri: Uri) {
        viewModelScope.launch {
            _state.update { it.copy(busy = true) }
            runCatching { backup.importFrom(uri) }
                .onSuccess { msg -> _state.update { it.copy(message = msg) } }
                .onFailure { e -> _state.update { it.copy(message = ctx.getString(R.string.import_error, e.message ?: "")) } }
            _state.update { it.copy(busy = false) }
        }
    }

    fun consumeMessage() {
        _state.update { it.copy(message = null) }
    }
}
