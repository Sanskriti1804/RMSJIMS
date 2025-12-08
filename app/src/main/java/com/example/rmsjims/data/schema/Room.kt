package com.example.rmsjims.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Room(
    val id: Int? = null,
    @SerialName("room_name") val roomName: String,
    @SerialName("room_number") val roomNumber: String? = null,
    val location: String? = null,
    val capacity: Int? = null,
    val status: String? = null,
    @SerialName("building_id") val buildingId: Int? = null,
    @SerialName("department_id") val departmentId: Int? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

