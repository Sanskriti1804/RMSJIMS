package com.example.rmsjims.data.model

import androidx.compose.ui.graphics.Color
import com.example.rmsjims.ui.theme.successColor
import com.example.rmsjims.ui.theme.primaryColor

data class ProductInfo(
    val imageRes: Int,
    val title: String,
    val code: String,
    val location: String,
    val status: Status,
    val timing : String? = null,
)

enum class Status(val label: String, val color: Color) {
    PENDING("Verification Pending", primaryColor),
    BOOKED("Booked", successColor),
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

