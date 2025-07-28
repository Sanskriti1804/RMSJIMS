package com.example.labinventory.data.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import com.example.labinventory.ui.theme.bookedColor
import com.example.labinventory.ui.theme.highlightColor

data class ProductInfo(
    val imageRes: Int,
    val title: String,
    val code: String,
    val location: String,
    val status: Status
)

enum class Status(val label: String, val color: Color) {
    PENDING("Verification Pending", highlightColor),
    BOOKED("Booked", bookedColor),
    CANCELLED("Cancelled", Color.Red)
}


data class InChargeInfo(
    val profName: String,
    val asstName: String,
    val asstIcons: List<Int>
)

data class BookingDates(
    val fromDate: String,
    val toDate: String
)

