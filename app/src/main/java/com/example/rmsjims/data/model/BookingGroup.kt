package com.example.rmsjims.data.model

data class BookingGroup(
    val pendingApprovals: List<BookingItem>,
    val newRequests: List<BookingItem>,
    val priorityRequests: List<BookingItem>,
    val flaggedRequests: List<BookingItem>
)

object BookingGroupProvider {
    fun sampleBookings(): BookingGroup {
        // Placeholder - will be populated with actual booking data
        return BookingGroup(
            pendingApprovals = emptyList(),
            newRequests = emptyList(),
            priorityRequests = emptyList(),
            flaggedRequests = emptyList()
        )
    }
}

