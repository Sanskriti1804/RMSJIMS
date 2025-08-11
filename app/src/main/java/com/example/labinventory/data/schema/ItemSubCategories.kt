package com.example.labinventory.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ItemSubCategories(
    val id : Int,
    val name : String,
    val parent_category_id : Int,
    @SerialName("created_at") val created_at : String? = null
)