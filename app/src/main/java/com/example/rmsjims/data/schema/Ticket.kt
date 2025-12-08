package com.example.rmsjims.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Ticket(
    val id: Int? = null,
    val name: String,
    val description: String? = null,
    val status: String? = null,
    val urgency: String? = null,
    @SerialName("requester_name") val requesterName: String? = null,
    @SerialName("assigned_to") val assignedTo: String? = null,
    @SerialName("department_id") val departmentId: Int? = null,
    @SerialName("equipment_id") val equipmentId: Int? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)
