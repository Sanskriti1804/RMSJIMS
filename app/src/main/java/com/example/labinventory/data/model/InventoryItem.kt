package com.example.labinventory.data.model

import kotlinx.serialization.Serializable

@Serializable       //Turning an object into a storable/sendable format (like text, JSON) automatically
data class InventoryItemImages(
    val id : Int,
    val item_id : Int,
    val image_url : String,
    val display_order : Int? = null,
    val created_at : String? = null,
)