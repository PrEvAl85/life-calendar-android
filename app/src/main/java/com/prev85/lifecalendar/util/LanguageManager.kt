package com.prev85.lifecalendar.util

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageManager {

    private const val PREFS = "app_prefs"
    private const val KEY_LANGUAGE = "language"

    const val SYSTEM = "system"

    val SUPPORTED = listOf(
        "en", "ru", "uk", "be", "kk", "de", "fr", "es", "it",
        "pt", "pl", "cs", "tr", "zh", "ja", "ko", "ar", "hi"
    )

    fun current(context: Context): String {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return prefs.getString(KEY_LANGUAGE, SYSTEM) ?: SYSTEM
    }

    fun set(context: Context, lang: String) {
        val prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_LANGUAGE, lang).apply()
    }

    /** Оборачивает контекст в локализованный, если выбран конкретный язык (не system). */
    fun applyTo(base: Context): Context {
        if (base == null) return base
        return try {
            val lang = current(base)
            if (lang == SYSTEM || lang.isEmpty()) return base
            val locale = Locale.forLanguageTag(lang)
            val config = Configuration(base.resources.configuration)
            config.setLocale(locale)
            base.createConfigurationContext(config)
        } catch (_: Exception) {
            base
        }
    }

    /** Отображаемое имя языка на его родном языке (для списка выбора). */
    fun displayName(lang: String): String {
        val locale = if (lang == SYSTEM) Locale.getDefault() else Locale.forLanguageTag(lang)
        return locale.getDisplayName(locale).replaceFirstChar { it.uppercase() }
    }
}
