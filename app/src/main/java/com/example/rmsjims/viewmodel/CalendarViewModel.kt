package com.example.rmsjims.viewmodel

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
class CalendarViewModel() : ViewModel() {

    val bookedDate = LocalDate.now().plusDays(10)
    val availableDate = LocalDate.now().plusDays(10)
    val bookOnDate = LocalDate.now().plusDays(10)

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

        // How many days to show from previous month (0 = Sunday)
        val startOffset = firstDayOfMonth.dayOfWeek.value % 7 // Sunday=0, Monday=1 ...
        val dates = mutableListOf<LocalDate>()

        // Add previous month trailing days
        for (i in startOffset downTo 1) {
            dates.add(firstDayOfMonth.minusDays(i.toLong()))
        }

        // Add current month days
        for (day in 1..lastDayOfMonth.dayOfMonth) {
            dates.add(currentMonth.atDay(day))
        }

        // Add next month leading days to fill the last week
        val endOffset = (7 - (dates.size % 7)) % 7
        for (i in 1..endOffset) {
            dates.add(lastDayOfMonth.plusDays(i.toLong()))
        }

        return dates
    }


    fun getMonths(): List<YearMonth> {
        val now = YearMonth.now()
        return (0..11).map { now.plusMonths(it.toLong()) }
    }

    fun getHolidayCount(): Int {
        return getMonthDates().count { it.dayOfWeek == DayOfWeek.SUNDAY }
    }
}

