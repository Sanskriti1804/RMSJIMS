package com.example.rmsjims.data.model

import androidx.compose.ui.graphics.Color
import com.example.rmsjims.ui.theme.primaryColor

enum class MaintenanceTab {
    Pending,
    Approved,
    Rejected,
    In_Progress
}

enum class MaintenanceStatusType(val label: String, val color: Color) {
    PENDING("Pending", Color(0xFFE67824)),
    APPROVED("Approved", Color(0xFF26BB64)),
    REJECTED("Rejected", Color(0xFFE64646)),
    IN_PROGRESS("In Progress", primaryColor),
    COMPLETE("Complete", primaryColor)
}

enum class UrgencyType(val label: String, val color: Color) {
    HIGH("High", Color(0xFFE64646)),
    MEDIUM("Medium", Color(0xFFE67824)),
    LOW("Low", Color(0xFF26BB64))
}

data class MaintenanceTabItem(
    val tab: MaintenanceTab,
    val label: String,
    val iconRes: Int,
    val isSelected: Boolean
)

