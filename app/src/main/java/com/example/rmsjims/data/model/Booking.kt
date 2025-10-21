package com.example.rmsjims.data.model

enum class BookingTab{
    Booking_Requests, Verified_Bookings, Canceled_Bookings
}

data class TabItem(
    val tab: BookingTab,
    val label: String,
    val iconRes: Int,
    val isSelected: Boolean
)