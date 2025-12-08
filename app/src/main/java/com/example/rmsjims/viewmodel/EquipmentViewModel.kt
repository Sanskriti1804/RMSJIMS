package com.example.rmsjims.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Equipment
import com.example.rmsjims.repository.EquipmentRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EquipmentViewModel(
    private val equipmentRepository: EquipmentRepository
) : ViewModel() {

    private val _equipmentState = MutableStateFlow<UiState<List<Equipment>>>(UiState.Loading)
    val equipmentState: StateFlow<UiState<List<Equipment>>> = _equipmentState.asStateFlow()

    private val _equipmentByDepartmentState = MutableStateFlow<UiState<List<Equipment>>>(UiState.Loading)
    val equipmentByDepartmentState: StateFlow<UiState<List<Equipment>>> = _equipmentByDepartmentState.asStateFlow()

    private val _equipmentByRoomState = MutableStateFlow<UiState<List<Equipment>>>(UiState.Loading)
    val equipmentByRoomState: StateFlow<UiState<List<Equipment>>> = _equipmentByRoomState.asStateFlow()

    init {
        fetchAllEquipment()
    }

    fun fetchAllEquipment() {
        viewModelScope.launch {
            _equipmentState.value = UiState.Loading
            try {
                val equipment = equipmentRepository.getAllEquipment()
                _equipmentState.value = UiState.Success(equipment)
                Log.d("EquipmentViewModel", "Fetched ${equipment.size} equipment items")
            } catch (e: Exception) {
                _equipmentState.value = UiState.Error(e)
                Log.e("EquipmentViewModel", "Error fetching equipment", e)
            }
        }
    }

    fun fetchEquipmentByDepartment(departmentId: Int) {
        viewModelScope.launch {
            _equipmentByDepartmentState.value = UiState.Loading
            try {
                val equipment = equipmentRepository.getEquipmentByDepartment(departmentId)
                _equipmentByDepartmentState.value = UiState.Success(equipment)
                Log.d("EquipmentViewModel", "Fetched ${equipment.size} equipment items for department $departmentId")
            } catch (e: Exception) {
                _equipmentByDepartmentState.value = UiState.Error(e)
                Log.e("EquipmentViewModel", "Error fetching equipment by department", e)
            }
        }
    }

    fun fetchEquipmentByRoom(roomId: Int) {
        viewModelScope.launch {
            _equipmentByRoomState.value = UiState.Loading
            try {
                val equipment = equipmentRepository.getEquipmentByRoom(roomId)
                _equipmentByRoomState.value = UiState.Success(equipment)
                Log.d("EquipmentViewModel", "Fetched ${equipment.size} equipment items for room $roomId")
            } catch (e: Exception) {
                _equipmentByRoomState.value = UiState.Error(e)
                Log.e("EquipmentViewModel", "Error fetching equipment by room", e)
            }
        }
    }

    suspend fun getEquipmentById(id: Int): Equipment? {
        return try {
            equipmentRepository.getEquipmentById(id)
        } catch (e: Exception) {
            Log.e("EquipmentViewModel", "Error fetching equipment by id", e)
            null
        }
    }

    fun refresh() {
        fetchAllEquipment()
    }
}

