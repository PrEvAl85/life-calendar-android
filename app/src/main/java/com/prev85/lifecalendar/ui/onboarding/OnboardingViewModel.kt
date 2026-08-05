package com.prev85.lifecalendar.ui.onboarding

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.prev85.lifecalendar.LifeCalendarApp
import kotlinx.coroutines.launch
import java.time.LocalDate

class OnboardingViewModel(app: Application) : AndroidViewModel(app) {

    private val ctx = app as LifeCalendarApp

    fun setBirthDate(date: LocalDate) {
        viewModelScope.launch { ctx.settings.setBirthDate(date.toString()) }
    }
}
