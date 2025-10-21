package com.example.rmsjims.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.schema.Department
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.repository.DepartmentRepository
import kotlinx.coroutines.launch

class DepartmentViewModel (
    private val departmentRepository: DepartmentRepository
) : ViewModel(){

    var departmentState by mutableStateOf<UiState<List<Department>>>(UiState.Loading)
        private set

    var query by mutableStateOf("")
        private set

    var filteredDepartments by mutableStateOf<List<String>>(emptyList())
        private set

    val departmentName : List<String> get() =
         when(val state = departmentState){
            is UiState.Success -> state.data.map {it.name}
        else -> emptyList()
        }


    init {
        getDepartment()
    }

    private fun getDepartment() {
        viewModelScope.launch {
            try {
                val departments = departmentRepository.fetchDepartment()
                departmentState = UiState.Success(departments)
            }
            catch (e : Exception){
                departmentState = UiState.Error(e)
            }
        }
    }

    fun onQueryChange(newQuery : String){
        query = newQuery
        filteredDepartments = if (newQuery.isBlank()){
            departmentName
        }
        else{
            departmentName.filter { it.contains(newQuery, ignoreCase = true) }
        }
    }

    fun onDepartmentSelected(department: String){
        query = department
    }
}