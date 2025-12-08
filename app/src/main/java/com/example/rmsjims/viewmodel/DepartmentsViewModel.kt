package com.example.rmsjims.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Department
import com.example.rmsjims.repository.DepartmentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DepartmentsViewModel(
    private val departmentsRepository: DepartmentsRepository
) : ViewModel() {

    private val _departmentsState = MutableStateFlow<UiState<List<Department>>>(UiState.Loading)
    val departmentsState: StateFlow<UiState<List<Department>>> = _departmentsState.asStateFlow()

    private val _departmentsByBuildingState = MutableStateFlow<UiState<List<Department>>>(UiState.Loading)
    val departmentsByBuildingState: StateFlow<UiState<List<Department>>> = _departmentsByBuildingState.asStateFlow()

    init {
        fetchAllDepartments()
    }

    fun fetchAllDepartments() {
        viewModelScope.launch {
            _departmentsState.value = UiState.Loading
            try {
                val departments = departmentsRepository.getAllDepartments()
                _departmentsState.value = UiState.Success(departments)
                Log.d("DepartmentsViewModel", "Fetched ${departments.size} departments")
            } catch (e: Exception) {
                _departmentsState.value = UiState.Error(e)
                Log.e("DepartmentsViewModel", "Error fetching departments", e)
            }
        }
    }

    fun fetchDepartmentsByBuilding(buildingId: Int) {
        viewModelScope.launch {
            _departmentsByBuildingState.value = UiState.Loading
            try {
                val departments = departmentsRepository.getDepartmentsByBuilding(buildingId)
                _departmentsByBuildingState.value = UiState.Success(departments)
                Log.d("DepartmentsViewModel", "Fetched ${departments.size} departments for building $buildingId")
            } catch (e: Exception) {
                _departmentsByBuildingState.value = UiState.Error(e)
                Log.e("DepartmentsViewModel", "Error fetching departments by building", e)
            }
        }
    }

    fun refresh() {
        fetchAllDepartments()
    }
}

