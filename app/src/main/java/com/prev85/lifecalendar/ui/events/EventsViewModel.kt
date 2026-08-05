package com.prev85.lifecalendar.ui.events

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prev85.lifecalendar.LifeCalendarApp
import com.prev85.lifecalendar.data.db.Event
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class EventsViewModel(app: Application) : AndroidViewModel(app) {

    private val ctx = app as LifeCalendarApp

    val events: StateFlow<List<Event>> = ctx.database.eventDao().getAll()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val birthDate: StateFlow<LocalDate?> = ctx.settings.birthDate
        .map { s -> s?.let { runCatching { LocalDate.parse(it) }.getOrNull() } }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    fun addEvent(date: LocalDate, title: String, color: Long) {
        viewModelScope.launch {
            ctx.database.eventDao().insert(Event(date = date.toString(), title = title, color = color))
        }
    }

    fun updateEvent(event: Event) {
        viewModelScope.launch { ctx.database.eventDao().update(event) }
    }

    fun deleteEvent(event: Event) {
        viewModelScope.launch { ctx.database.eventDao().delete(event) }
    }
}
