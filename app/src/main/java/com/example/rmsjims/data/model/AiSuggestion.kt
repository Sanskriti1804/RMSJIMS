package com.example.rmsjims.data.model

import kotlinx.serialization.Serializable

@Serializable
data class AiSuggestion(
    val suggestions: List<SuggestedEquipment>
) {
    @Serializable
    data class SuggestedEquipment(
        val equipmentName: String,
        val buildingName: String,
        val buildingNumber: String? = null,
        val departmentName: String,
        val location: String,
        val status: String,
        val reason: String
    )
}

