package com.example.labinventory.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Items(
    val id: Int,
    val faciilty_id: Int? = null,
    val parent_categoy_id: Int? = null,
    val category_id: Int? = null,
    val name: String,
    val specification : JsonObject? = null,
    val description : String,
    val image_url : String,
    val usage_instructions : String? = null,
    val is_available: Boolean? = null,
    @SerialName("created_at") val createdAt: String? = null
)