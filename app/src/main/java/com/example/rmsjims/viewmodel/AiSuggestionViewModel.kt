package com.example.rmsjims.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.model.AiSuggestion
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.remote.apiservice.GeminiApiService
import com.example.rmsjims.repository.AiSuggestionRepository
import com.example.rmsjims.repository.EquipmentRepository
import com.example.rmsjims.repository.BuildingsRepository
import com.example.rmsjims.repository.DepartmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AiSuggestionViewModel(
    private val aiSuggestionRepository: AiSuggestionRepository,
    private val equipmentRepository: EquipmentRepository,
    private val buildingsRepository: BuildingsRepository,
    private val departmentsRepository: DepartmentsRepository
) : ViewModel() {
    
    private val _suggestionState = MutableStateFlow<UiState<AiSuggestion>>(UiState.Loading)
    val suggestionState: StateFlow<UiState<AiSuggestion>> = _suggestionState.asStateFlow()
    
    fun getSuggestions(projectDescription: String) {
        viewModelScope.launch {
            _suggestionState.value = UiState.Loading
            
            try {
                // Fetch all required data directly from repositories
                val equipment = equipmentRepository.getAllEquipment()
                val buildings = buildingsRepository.getAllBuildings().associateBy { it.id }
                val departments = departmentsRepository.getAllDepartments().associateBy { it.id }
                
                // Convert to GeminiApiService.EquipmentInfo format
                val equipmentInfoList = equipment.map { eq ->
                    val building = eq.departmentId?.let { deptId ->
                        departments[deptId]?.buildingId?.let { buildingId ->
                            buildings[buildingId]
                        }
                    }
                    val department = eq.departmentId?.let { departments[it] }
                    
                    val isAvailable = eq.requestStatus?.lowercase()?.contains("available") == true ||
                            eq.requestStatus?.lowercase()?.contains("in use") != true
                    
                    GeminiApiService.EquipmentInfo(
                        name = eq.name,
                        buildingName = building?.buildingName,
                        buildingNumber = building?.buildingNumber,
                        departmentName = department?.departmentName,
                        location = eq.location,
                        requestStatus = eq.requestStatus,
                        isAvailable = isAvailable
                    )
                }
                
                Log.d("AiSuggestionViewModel", "Requesting suggestions for ${equipmentInfoList.size} equipment items")
                Log.d("AiSuggestionViewModel", "Project description length: ${projectDescription.length}")
                
                if (equipmentInfoList.isEmpty()) {
                    Log.w("AiSuggestionViewModel", "No equipment available in database")
                    _suggestionState.value = UiState.Error(Exception("No equipment available in database. Please add equipment first."))
                    return@launch
                }
                
                val result = aiSuggestionRepository.getEquipmentSuggestions(
                    projectDescription,
                    equipmentInfoList
                )
                
                result.fold(
                    onSuccess = { suggestion ->
                        _suggestionState.value = UiState.Success(suggestion)
                        Log.d("AiSuggestionViewModel", "Successfully got ${suggestion.suggestions.size} suggestions")
                    },
                    onFailure = { error ->
                        _suggestionState.value = UiState.Error(error)
                        Log.e("AiSuggestionViewModel", "Error getting suggestions: ${error.message}", error)
                        error.printStackTrace()
                    }
                )
                
            } catch (e: Exception) {
                _suggestionState.value = UiState.Error(e)
                Log.e("AiSuggestionViewModel", "Exception getting suggestions", e)
            }
        }
    }
    
    fun clearSuggestions() {
        _suggestionState.value = UiState.Loading
    }
}

