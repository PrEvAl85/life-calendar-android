package com.prev85.lifecalendar.ui.stats

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prev85.lifecalendar.LifeCalendarApp
import com.prev85.lifecalendar.util.Dates
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class StatsViewModel(app: Application) : AndroidViewModel(app) {

    private val ctx = app as LifeCalendarApp

    data class Stats(
        val birthDate: LocalDate? = null,
        val lifespanYears: Int = 100,
        val totalWeeks: Int = 0,
        val livedWeeks: Int = 0,
        val weeksLeft: Int = 0,
        val percentLived: Float = 0f,
        val ageYears: Long = 0,
        val ageMonths: Long = 0,
        val ageDays: Long = 0,
        val entriesCount: Int = 0,
        val eventsCount: Int = 0,
        val birthdays: Long = 0,
    )

    val stats: StateFlow<Stats> = combine(
        ctx.settings.birthDate,
        ctx.settings.lifespanYears,
        ctx.database.entryDao().count(),
        ctx.database.eventDao().getAll()
    ) { birthS, lifespan, entryCount, events ->
        val birth = birthS?.let { runCatching { LocalDate.parse(it) }.getOrNull() }
        val now = LocalDate.now()
        val weekKeys = birth?.let { Dates.allWeekKeys(it, lifespan) } ?: emptyList()
        val total = weekKeys.size
        val lived = birth?.let {
            (ChronoUnit.DAYS.between(it, now) / 7).toInt().coerceIn(0, total)
        } ?: 0
        val ageYears = birth?.let { ChronoUnit.YEARS.between(it, now) } ?: 0
        val ageMonths = birth?.let { ChronoUnit.MONTHS.between(it, now) } ?: 0
        val ageDays = birth?.let { ChronoUnit.DAYS.between(it, now) } ?: 0
        Stats(
            birthDate = birth,
            lifespanYears = lifespan,
            totalWeeks = total,
            livedWeeks = lived,
            weeksLeft = (total - lived).coerceAtLeast(0),
            percentLived = if (total > 0) lived.toFloat() / total else 0f,
            ageYears = ageYears,
            ageMonths = ageMonths,
            ageDays = ageDays,
            entriesCount = entryCount,
            eventsCount = events.size,
            birthdays = ageYears.coerceAtLeast(0),
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), Stats())
}
