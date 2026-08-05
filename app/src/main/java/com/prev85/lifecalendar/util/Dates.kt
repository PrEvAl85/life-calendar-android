package com.prev85.lifecalendar.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

object Dates {
    private val ISO = DateTimeFormatter.ISO_LOCAL_DATE

    fun iso(d: LocalDate): String = d.format(ISO)

    fun parse(s: String): LocalDate = LocalDate.parse(s, ISO)

    fun mondayOf(d: LocalDate): LocalDate = d.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))

    /** Ключ недели (ISO-дата понедельника) */
    fun weekKey(d: LocalDate): String = iso(mondayOf(d))

    fun startOfYearWeek(year: Int): LocalDate = mondayOf(LocalDate.of(year, 1, 1))

    /** Понедельники года, с учётом хвостов прошлого/начала следующего года */
    fun weeksOfYear(year: Int): List<LocalDate> {
        val start = startOfYearWeek(year)
        val weeks = mutableListOf<LocalDate>()
        var w = start
        while (w.year <= year) {
            weeks.add(w)
            w = w.plusWeeks(1)
        }
        return weeks
    }

    fun ddmmyyyy(d: LocalDate): String =
        String.format("%02d.%02d.%04d", d.dayOfMonth, d.monthValue, d.year)

    fun formatWeekRange(monday: LocalDate): String =
        "${ddmmyyyy(monday)}–${ddmmyyyy(monday.plusDays(6))}"

    /** Все понедельники от рождения до конца срока жизни */
    fun allWeekKeys(birthDate: LocalDate, lifespanYears: Int): List<LocalDate> {
        val last = birthDate.plusYears(lifespanYears.toLong())
        val result = mutableListOf<LocalDate>()
        var w = mondayOf(birthDate)
        while (w <= last) {
            result.add(w)
            w = w.plusWeeks(1)
        }
        return result
    }
}
