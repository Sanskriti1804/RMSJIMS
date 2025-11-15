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

enum class BookingPriority(val label: String, val color: Color) {
    LOW("Low", Color(0xFF26BB64)),
    MEDIUM("Medium", Color(0xFFE67824)),
    HIGH("High", Color(0xFFE64646))
}

enum class BookingStatus(val label: String, val color: Color) {
    VERIFICATION_PENDING("Verification Pending", Color(0xFFE67824)),
    APPROVED("Booking Approved", successColor),
    REJECTED("Booking Rejected", Color(0xFFE64646))
}

data class BookingItem(
    val id: String,
    val productInfo: ProductInfo,
    val inChargeInfo: InChargeInfo,
    val bookingDates: BookingDates,
    val bookingStatus: BookingStatus,
    val priority: BookingPriority,
    val rejectionReason: String? = null
)

