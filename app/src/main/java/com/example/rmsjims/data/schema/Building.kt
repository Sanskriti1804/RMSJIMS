package com.example.rmsjims.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Building(
    val id: Int? = null,
    @SerialName("building_name") val buildingName: String,
    @SerialName("building_number") val buildingNumber: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

