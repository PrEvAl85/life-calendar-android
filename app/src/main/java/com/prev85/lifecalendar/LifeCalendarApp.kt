package com.prev85.lifecalendar

import android.app.Application
import com.prev85.lifecalendar.data.db.AppDatabase
import com.prev85.lifecalendar.data.SettingsRepository

class LifeCalendarApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.get(this) }
    val settings: SettingsRepository by lazy { SettingsRepository(this) }
}
