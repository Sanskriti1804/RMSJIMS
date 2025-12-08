package com.example.rmsjims.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Building
import com.example.rmsjims.repository.BuildingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BuildingsViewModel(
    private val buildingsRepository: BuildingsRepository
) : ViewModel() {

    private val _buildingsState = MutableStateFlow<UiState<List<Building>>>(UiState.Loading)
    val buildingsState: StateFlow<UiState<List<Building>>> = _buildingsState.asStateFlow()

    init {
        fetchBuildings()
        setupRealtimeSubscription()
    }

    fun fetchBuildings() {
        viewModelScope.launch {
            _buildingsState.value = UiState.Loading
            try {
                val buildings = buildingsRepository.getAllBuildings()
                _buildingsState.value = UiState.Success(buildings)
                Log.d("BuildingsViewModel", "Fetched ${buildings.size} buildings")
            } catch (e: Exception) {
                _buildingsState.value = UiState.Error(e)
                Log.e("BuildingsViewModel", "Error fetching buildings", e)
            }
        }
    }

    private fun setupRealtimeSubscription() {
        viewModelScope.launch {
            try {
                // Note: Real-time subscriptions require proper Supabase configuration
                // This is a placeholder for real-time updates
                Log.d("BuildingsViewModel", "Real-time subscription setup (if configured)")
            } catch (e: Exception) {
                Log.e("BuildingsViewModel", "Error setting up real-time subscription", e)
            }
        }
    }

    fun refresh() {
        fetchBuildings()
    }
}

