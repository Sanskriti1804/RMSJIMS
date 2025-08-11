package com.example.labinventory.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labinventory.data.model.UiState
import com.example.labinventory.data.schema.Facilities
import com.example.labinventory.repository.FacilitiesRepository
import kotlinx.coroutines.launch

class FacilitiesViewModel (
    private val facilitiesRepository: FacilitiesRepository
) : ViewModel(){
    var facilitiesState by mutableStateOf<UiState<List<Facilities>>>(UiState.Loading)
        private set

    init {
        getFacilities()
    }

    private fun getFacilities() {
        viewModelScope.launch {
            try {
                val facilities = facilitiesRepository.getFacilities()
                facilitiesState = UiState.Success(facilities)
            }
            catch (e : Exception){
                facilitiesState = UiState.Error(e)
            }
        }
    }
}