package com.example.rmsjims.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

@Serializable
data class Items(
    val id: Int,
    val facility_id: Int? = null,
    val parent_categoy_id: Int? = null,
    val category_id: Int? = null,
    @SerialName("category_name") val category_name: String? = null,
    val name: String,
    val specification : JsonObject? = null,
    val description : String,
    val image_url : String,
    val usage_instructions : String? = null,
    val is_available: Boolean? = null,
    @SerialName("created_at") val createdAt: String? = null
)