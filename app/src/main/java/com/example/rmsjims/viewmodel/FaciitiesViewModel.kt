package com.example.rmsjims.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Facilities
import com.example.rmsjims.repository.FacilitiesRepository
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
                Log.e("FacilitiesVM", "Failed to load facilities", e)
                facilitiesState = UiState.Error(e)
            }
        }
    }
    
    fun refresh() {
        getFacilities()
    }
}