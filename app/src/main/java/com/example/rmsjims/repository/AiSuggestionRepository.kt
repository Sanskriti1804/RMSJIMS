package com.example.rmsjims.repository

import com.example.rmsjims.data.model.AiSuggestion
import com.example.rmsjims.data.remote.apiservice.GeminiApiService

class AiSuggestionRepository(
    private val geminiApiService: GeminiApiService
) {
    suspend fun getEquipmentSuggestions(
        projectDescription: String,
        equipmentList: List<GeminiApiService.EquipmentInfo>
    ): Result<AiSuggestion> {
        return geminiApiService.getEquipmentSuggestions(projectDescription, equipmentList)
    }
}

