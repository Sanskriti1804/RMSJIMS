package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.model.AiSuggestion

interface GeminiApiService {
    suspend fun getEquipmentSuggestions(
        projectDescription: String,
        equipmentList: List<EquipmentInfo>
    ): Result<AiSuggestion>
    
    data class EquipmentInfo(
        val name: String,
        val buildingName: String?,
        val buildingNumber: String?,
        val departmentName: String?,
        val location: String?,
        val requestStatus: String?,
        val isAvailable: Boolean
    )
}

