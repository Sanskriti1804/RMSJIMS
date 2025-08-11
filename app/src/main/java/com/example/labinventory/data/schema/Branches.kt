package com.example.labinventory.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Branch(
    val id : Int,
    val name : String,
    val department_id : Int,
    @SerialName("created_at") val createdAt: String? = null
)
