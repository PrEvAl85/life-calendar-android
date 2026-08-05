package com.prev85.lifecalendar.ui.grid

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prev85.lifecalendar.LifeCalendarApp
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import com.prev85.lifecalendar.util.Dates
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class WeekGridViewModel(app: Application) : AndroidViewModel(app) {

    private val ctx = app as LifeCalendarApp

    data class UiState(
        val birthDate: LocalDate? = null,
        val birthMonth: Int = 0,
        val birthDay: Int = 0,
        val lifespanYears: Int = 100,
        val todayKey: String = "",
        val weekKeys: List<LocalDate> = emptyList(),
        val entriesByWeek: Map<String, List<Entry>> = emptyMap(),
        val eventsByWeek: Map<String, List<Event>> = emptyMap(),
        val entries: List<Entry> = emptyList(),
        val events: List<Event> = emptyList(),
    )

    val uiState: StateFlow<UiState> = combine(
        ctx.settings.birthDate,
        ctx.settings.lifespanYears,
        ctx.database.entryDao().getAll(),
        ctx.database.eventDao().getAll()
    ) { birthS, lifespan, entries, events ->
        runCatching {
            val birth = birthS?.let { s -> LocalDate.parse(s) }
            val weekKeys = birth?.let { Dates.allWeekKeys(it, lifespan) } ?: emptyList()
            UiState(
                birthDate = birth,
                birthMonth = birth?.monthValue ?: 0,
                birthDay = birth?.dayOfMonth ?: 0,
                lifespanYears = lifespan,
                todayKey = Dates.weekKey(LocalDate.now()),
                weekKeys = weekKeys,
                entriesByWeek = entries.groupBy { Dates.weekKey(LocalDate.parse(it.date)) },
                eventsByWeek = events.groupBy { Dates.weekKey(LocalDate.parse(it.date)) },
                entries = entries.sortedBy { it.date },
                events = events.sortedBy { it.date },
            )
        }.onFailure { e ->
            Log.e("WeekGridVM", "Ошибка сборки UiState", e)
        }.getOrDefault(UiState(lifespanYears = lifespan))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())

    fun addEntry(date: LocalDate, text: String) {
        viewModelScope.launch {
            ctx.database.entryDao().insert(Entry(date = Dates.iso(date), text = text))
        }
    }

    fun updateEntry(entry: Entry) {
        viewModelScope.launch { ctx.database.entryDao().update(entry) }
    }

    fun deleteEntry(entry: Entry) {
        viewModelScope.launch { ctx.database.entryDao().delete(entry) }
    }
}
