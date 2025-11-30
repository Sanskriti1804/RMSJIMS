package com.example.rmsjims.data.model

data class TicketGroupData(
    val pendingApprovals: List<TicketItem>,
    val newRequests: List<TicketItem>,
    val priorityRequests: List<TicketItem>,
    val flaggedRequests: List<TicketItem>
)

data class TicketItem(
    val id: String,
    val title: String,
    val description: String,
    val status: TicketStatus,
    val priority: TicketPriority,
    val category: TicketCategory
)

object TicketGroupDataProvider {
    fun sampleTickets(): TicketGroupData {
        // Placeholder - will be populated with actual ticket data later
        return TicketGroupData(
            pendingApprovals = emptyList(),
            newRequests = emptyList(),
            priorityRequests = emptyList(),
            flaggedRequests = emptyList()
        )
    }
}

