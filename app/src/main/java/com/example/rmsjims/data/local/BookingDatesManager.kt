package com.example.rmsjims.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.rmsjims.data.model.BookingDates

class BookingDatesManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("booking_dates_prefs", Context.MODE_PRIVATE)
    
    companion object {
        private const val KEY_PREFIX = "booking_dates_"
    }
    
    fun saveBookingDates(equipmentTitle: String, dates: BookingDates) {
        val key = "$KEY_PREFIX${equipmentTitle}"
        prefs.edit()
            .putString("${key}_from", dates.fromDate)
            .putString("${key}_to", dates.toDate)
            .apply()
    }
    
    fun getBookingDates(equipmentTitle: String): BookingDates? {
        val key = "$KEY_PREFIX${equipmentTitle}"
        val fromDate = prefs.getString("${key}_from", null)
        val toDate = prefs.getString("${key}_to", null)
        
        return if (fromDate != null && toDate != null) {
            BookingDates(fromDate = fromDate, toDate = toDate)
        } else {
            null
        }
    }
    
    fun hasBookingDates(equipmentTitle: String): Boolean {
        return getBookingDates(equipmentTitle) != null
    }
    
    fun clearBookingDates(equipmentTitle: String) {
        val key = "$KEY_PREFIX${equipmentTitle}"
        prefs.edit()
            .remove("${key}_from")
            .remove("${key}_to")
            .apply()
    }
}

