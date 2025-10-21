package com.example.rmsjims.data.schema

import kotlinx.serialization.Serializable

@Serializable       //Turning an object into a storable/sendable format (like text, JSON) automatically
data class ItemImages(
    val id : Int,
    val item_id : Int,
    val image_url : String,
    val display_order : Int? = null,
    val created_at : String? = null,
)