package com.example.labinventory.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel : ViewModel() {

    val bookedDate = LocalDate.now().plusDays(2)
    val availableDate = LocalDate.now().plusDays(4)
    val bookOnDate = LocalDate.now().plusDays(6)

    @RequiresApi(Build.VERSION_CODES.O)
    private val today = LocalDate.now()

    var selectedDate by mutableStateOf(today)
        private set

    var currentMonth by mutableStateOf(YearMonth.now())
        private set

    val daysOfWeek: List<DayOfWeek> = DayOfWeek.values().toList()

    fun selectDate(date: LocalDate) {
        selectedDate = date
    }

    fun setMonth(month: YearMonth) {
        currentMonth = month
    }

    fun getMonthDates(): List<LocalDate> {
        val firstDayOfMonth = currentMonth.atDay(1)
        val lastDayOfMonth = currentMonth.atEndOfMonth()

        val startOfCalendar = firstDayOfMonth.with(DayOfWeek.SUNDAY)
        val endOfCalendar = lastDayOfMonth.with(DayOfWeek.SATURDAY)

        return generateSequence(startOfCalendar) { it.plusDays(1) }
            .takeWhile { !it.isAfter(endOfCalendar) }
            .toList()
    }

    fun getMonths(): List<YearMonth> {
        val now = YearMonth.now()
        return (0..11).map { now.plusMonths(it.toLong()) }
    }

    fun getHolidayCount(): Int {
        return getMonthDates().count { it.dayOfWeek == DayOfWeek.SUNDAY }
    }
}

