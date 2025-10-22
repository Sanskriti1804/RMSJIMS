package com.example.rmsjims.data.model

import androidx.compose.ui.graphics.Color
import com.example.rmsjims.ui.theme.errorColor
import com.example.rmsjims.ui.theme.successColor

enum class TicketStatus(
    val dispName : String,
    val dispColor : Color
){
    PENDING("Pending", errorColor),
    ACTIVE("Active", Color.Yellow),
    CLOSED("Closed", successColor),
    UNASSIGNED("Unassigned", Color.Gray)
}

enum class TicketPriority(
    val dispName: String,
    val dispColor: Color
){
    HIGH("High", Color.Red),
    MEDIUM("Medium", Color.Yellow),
    LOW("Low", Color.Green)
}

enum class TicketCategory(
    val dispName: String,
    val dispColor: Color
){
    TECHNICAL("Technical", Color.Cyan),
    NON_TECHNICAL("Non-Technical", Color.Magenta)
}

data class TicketGroup(
    val groupName : String,
    val groupColor : Color,
    val ticketCount : Int
)

