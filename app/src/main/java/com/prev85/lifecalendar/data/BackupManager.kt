package com.prev85.lifecalendar.data

import android.content.Context
import android.net.Uri
import com.prev85.lifecalendar.R
import com.prev85.lifecalendar.data.db.AppDatabase
import androidx.room.withTransaction
import com.prev85.lifecalendar.data.db.Entry
import com.prev85.lifecalendar.data.db.Event
import kotlinx.coroutines.flow.first
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class BackupEntry(val date: String, val text: String)

@Serializable
data class BackupEvent(val date: String, val title: String, val color: Long)

@Serializable
data class BackupData(
    val version: Int = 1,
    val birthDate: String? = null,
    val lifespanYears: Int = 100,
    val entries: List<BackupEntry> = emptyList(),
    val events: List<BackupEvent> = emptyList(),
)

class BackupManager(
    private val context: Context,
    private val db: AppDatabase,
    private val settings: SettingsRepository,
) {

    private val json = Json { prettyPrint = true; ignoreUnknownKeys = true }

    suspend fun buildExport(): String {
        val entries = db.entryDao().getAll().first()
        val events = db.eventDao().getAll().first()
        val birth = settings.birthDate.first()
        val lifespan = settings.lifespanYears.first()
        val data = BackupData(
            birthDate = birth,
            lifespanYears = lifespan,
            entries = entries.map { BackupEntry(it.date, it.text) },
            events = events.map { BackupEvent(it.date, it.title, it.color) },
        )
        return json.encodeToString(BackupData.serializer(), data)
    }

    suspend fun exportTo(uri: Uri) {
        val payload = buildExport()
        context.contentResolver.openOutputStream(uri)?.use { it.write(payload.toByteArray()) }
    }

    suspend fun importFrom(uri: Uri): String {
        val text = context.contentResolver.openInputStream(uri)?.use { it.readBytes().toString(Charsets.UTF_8) }
            ?: error(context.getString(R.string.read_error))
        val data = json.decodeFromString(BackupData.serializer(), text)

        db.withTransaction {
            db.entryDao().clearAll()
            db.eventDao().clearAll()
            db.entryDao().insertAll(data.entries.map { Entry(date = it.date, text = it.text) })
            db.eventDao().insertAll(data.events.map { Event(date = it.date, title = it.title, color = it.color) })
        }
        if (data.birthDate != null) settings.setBirthDate(data.birthDate)
        settings.setLifespanYears(data.lifespanYears)

        return context.getString(R.string.imported_summary, data.entries.size, data.events.size)
    }
}
