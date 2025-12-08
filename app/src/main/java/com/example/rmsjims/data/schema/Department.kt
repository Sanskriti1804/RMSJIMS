package com.example.rmsjims.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Department(
    val id: Int? = null,
    @SerialName("department_name") val departmentName: String,
    val address: String? = null,
    @SerialName("building_id") val buildingId: Int? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)
