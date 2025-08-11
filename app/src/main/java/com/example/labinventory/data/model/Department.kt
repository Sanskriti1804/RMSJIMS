package com.example.labinventory.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Department(
    val id : Int,
    val name : String,
    @SerialName("created_at") val createdAt: String? = null
)