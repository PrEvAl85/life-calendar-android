package com.prev85.lifecalendar.ui.week

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prev85.lifecalendar.LifeCalendarApp
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.util.Dates
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate

class WeekViewModel(app: Application, monday: String) : AndroidViewModel(app) {

    private val ctx = app as LifeCalendarApp
    val mondayDate: LocalDate = Dates.parse(monday)
    private val sundayDate = mondayDate.plusDays(6)

    val entries: StateFlow<List<Entry>> = ctx.database.entryDao()
        .getBetween(Dates.iso(mondayDate), Dates.iso(sundayDate))
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

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
