package com.prev85.lifecalendar.data

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsRepository(private val context: Context) {

    private object Keys {
        val BIRTH_DATE = stringPreferencesKey("birth_date")
        val LIFESPAN_YEARS = intPreferencesKey("lifespan_years")
    }

    val birthDate: Flow<String?> = context.dataStore.data.map { it[Keys.BIRTH_DATE] }
    val lifespanYears: Flow<Int> = context.dataStore.data.map { it[Keys.LIFESPAN_YEARS] ?: DEFAULT_LIFESPAN }

    suspend fun setBirthDate(value: String?) {
        context.dataStore.edit {
            if (value == null) it.remove(Keys.BIRTH_DATE) else it[Keys.BIRTH_DATE] = value
        }
    }

    suspend fun setLifespanYears(value: Int) {
        context.dataStore.edit { it[Keys.LIFESPAN_YEARS] = value }
    }

    companion object {
        const val DEFAULT_LIFESPAN = 100
    }
}
