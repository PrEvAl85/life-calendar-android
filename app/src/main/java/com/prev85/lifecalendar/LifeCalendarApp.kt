package com.prev85.lifecalendar

import android.app.Application
import android.os.Process
import android.util.Log
import com.prev85.lifecalendar.data.db.AppDatabase
import com.prev85.lifecalendar.data.SettingsRepository
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LifeCalendarApp : Application() {
    val database: AppDatabase by lazy { AppDatabase.get(this) }
    val settings: SettingsRepository by lazy { SettingsRepository(this) }

    override fun onCreate() {
        super.onCreate()
        installCrashLogger()
    }

    private fun installCrashLogger() {
        val previous = Thread.getDefaultUncaughtExceptionHandler()
        Thread.setDefaultUncaughtExceptionHandler { thread, throwable ->
            try {
                val stamp = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(Date())
                val stack = Log.getStackTraceString(throwable)
                val file = File(filesDir, "crash.log")
                val entry = "[$stamp] ${thread.name}\n$stack\n----------------------------------------\n"
                file.appendText(entry)
                Log.e("LifeCalendar", entry)
            } catch (_: Exception) {
            }
            previous?.uncaughtException(thread, throwable)
                ?: Process.killProcess(Process.myPid())
        }
    }
}
