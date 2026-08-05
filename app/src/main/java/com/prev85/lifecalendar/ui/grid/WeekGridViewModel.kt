package com.prev85.lifecalendar.ui.grid

import android.app.Application
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
import java.time.LocalDate

class WeekGridViewModel(app: Application) : AndroidViewModel(app) {

    private val ctx = app as LifeCalendarApp

    data class UiState(
        val birthDate: LocalDate? = null,
        val lifespanYears: Int = 100,
        val todayKey: String = "",
        val weekKeys: List<LocalDate> = emptyList(),
        val entriesByWeek: Map<String, List<Entry>> = emptyMap(),
        val eventsByWeek: Map<String, List<Event>> = emptyMap(),
    )

    val uiState: StateFlow<UiState> = combine(
        ctx.settings.birthDate,
        ctx.settings.lifespanYears,
        ctx.database.entryDao().getAll(),
        ctx.database.eventDao().getAll()
    ) { birthS, lifespan, entries, events ->
        val birth = birthS?.let { s -> runCatching { LocalDate.parse(s) }.getOrNull() }
        val weekKeys = birth?.let { Dates.allWeekKeys(it, lifespan) } ?: emptyList()
        UiState(
            birthDate = birth,
            lifespanYears = lifespan,
            todayKey = Dates.weekKey(LocalDate.now()),
            weekKeys = weekKeys,
            entriesByWeek = entries.groupBy { Dates.weekKey(LocalDate.parse(it.date)) },
            eventsByWeek = events.groupBy { Dates.weekKey(LocalDate.parse(it.date)) },
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UiState())
}
