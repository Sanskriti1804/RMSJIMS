package com.example.rmsjims.data.model

data class EquipmentGroup(
    val pendingApprovals: List<PropertyRequest>,
    val newRequests: List<PropertyRequest>,
    val priorityRequests: List<PropertyRequest>,
    val flaggedRequests: List<PropertyRequest>
)

object EquipmentGroupProvider {
    fun sampleEquipments(): EquipmentGroup {
        // Placeholder - will be populated with actual equipment data
        return EquipmentGroup(
            pendingApprovals = emptyList(),
            newRequests = emptyList(),
            priorityRequests = emptyList(),
            flaggedRequests = emptyList()
        )
    }
}

