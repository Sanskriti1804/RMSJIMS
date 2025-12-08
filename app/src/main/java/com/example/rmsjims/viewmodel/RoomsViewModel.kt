package com.example.rmsjims.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Room
import com.example.rmsjims.repository.RoomsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RoomsViewModel(
    private val roomsRepository: RoomsRepository
) : ViewModel() {

    private val _roomsState = MutableStateFlow<UiState<List<Room>>>(UiState.Loading)
    val roomsState: StateFlow<UiState<List<Room>>> = _roomsState.asStateFlow()

    private val _roomsByBuildingState = MutableStateFlow<UiState<List<Room>>>(UiState.Loading)
    val roomsByBuildingState: StateFlow<UiState<List<Room>>> = _roomsByBuildingState.asStateFlow()

    private val _roomsByDepartmentState = MutableStateFlow<UiState<List<Room>>>(UiState.Loading)
    val roomsByDepartmentState: StateFlow<UiState<List<Room>>> = _roomsByDepartmentState.asStateFlow()

    init {
        fetchAllRooms()
    }

    fun fetchAllRooms() {
        viewModelScope.launch {
            _roomsState.value = UiState.Loading
            try {
                val rooms = roomsRepository.getAllRooms()
                _roomsState.value = UiState.Success(rooms)
                Log.d("RoomsViewModel", "Fetched ${rooms.size} rooms")
            } catch (e: Exception) {
                _roomsState.value = UiState.Error(e)
                Log.e("RoomsViewModel", "Error fetching rooms", e)
            }
        }
    }

    fun fetchRoomsByBuilding(buildingId: Int) {
        viewModelScope.launch {
            _roomsByBuildingState.value = UiState.Loading
            try {
                val rooms = roomsRepository.getRoomsByBuilding(buildingId)
                _roomsByBuildingState.value = UiState.Success(rooms)
                Log.d("RoomsViewModel", "Fetched ${rooms.size} rooms for building $buildingId")
            } catch (e: Exception) {
                _roomsByBuildingState.value = UiState.Error(e)
                Log.e("RoomsViewModel", "Error fetching rooms by building", e)
            }
        }
    }

    fun fetchRoomsByDepartment(departmentId: Int) {
        viewModelScope.launch {
            _roomsByDepartmentState.value = UiState.Loading
            try {
                val rooms = roomsRepository.getRoomsByDepartment(departmentId)
                _roomsByDepartmentState.value = UiState.Success(rooms)
                Log.d("RoomsViewModel", "Fetched ${rooms.size} rooms for department $departmentId")
            } catch (e: Exception) {
                _roomsByDepartmentState.value = UiState.Error(e)
                Log.e("RoomsViewModel", "Error fetching rooms by department", e)
            }
        }
    }

    fun refresh() {
        fetchAllRooms()
    }
}

