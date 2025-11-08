package com.example.rmsjims.data.model

import androidx.compose.ui.graphics.Color
import com.example.rmsjims.ui.theme.primaryColor

enum class MachineTab {
    Active_Machines,
    Under_Maintenance,
    Available,
    Out_of_Order
}

enum class MachineStatusType(val label: String, val color: Color) {
    OPERATIONAL("Operational", Color(0xFF26BB64)),
    IDLE("Idle", Color(0xFFE67824)),
    UNDER_SCHEDULE_MAINTENANCE("Under Schedule Maintenance", Color(0xFFFFA500)),
    MARK_AS_OUT_OF_ORDER("Mark as Out of Order", Color(0xFFE64646)),
    UNDER_CHECK("Under Check", Color(0xFF024CA1)),
    OFFLINE("Offline", Color(0xFF757575))
}

data class MachineTabItem(
    val tab: MachineTab,
    val label: String,
    val iconRes: Int,
    val isSelected: Boolean
)

