package com.example.rmsjims.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Ticket
import com.example.rmsjims.repository.TicketsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TicketsViewModel(
    private val ticketsRepository: TicketsRepository
) : ViewModel() {

    private val _ticketsState = MutableStateFlow<UiState<List<Ticket>>>(UiState.Loading)
    val ticketsState: StateFlow<UiState<List<Ticket>>> = _ticketsState.asStateFlow()

    private val _ticketsByDepartmentState = MutableStateFlow<UiState<List<Ticket>>>(UiState.Loading)
    val ticketsByDepartmentState: StateFlow<UiState<List<Ticket>>> = _ticketsByDepartmentState.asStateFlow()

    private val _ticketsByStatusState = MutableStateFlow<UiState<List<Ticket>>>(UiState.Loading)
    val ticketsByStatusState: StateFlow<UiState<List<Ticket>>> = _ticketsByStatusState.asStateFlow()

    init {
        fetchAllTickets()
    }

    fun fetchAllTickets() {
        viewModelScope.launch {
            _ticketsState.value = UiState.Loading
            try {
                val tickets = ticketsRepository.getAllTickets()
                _ticketsState.value = UiState.Success(tickets)
                Log.d("TicketsViewModel", "Fetched ${tickets.size} tickets")
            } catch (e: Exception) {
                _ticketsState.value = UiState.Error(e)
                Log.e("TicketsViewModel", "Error fetching tickets", e)
            }
        }
    }

    fun fetchTicketsByDepartment(departmentId: Int) {
        viewModelScope.launch {
            _ticketsByDepartmentState.value = UiState.Loading
            try {
                val tickets = ticketsRepository.getTicketsByDepartment(departmentId)
                _ticketsByDepartmentState.value = UiState.Success(tickets)
                Log.d("TicketsViewModel", "Fetched ${tickets.size} tickets for department $departmentId")
            } catch (e: Exception) {
                _ticketsByDepartmentState.value = UiState.Error(e)
                Log.e("TicketsViewModel", "Error fetching tickets by department", e)
            }
        }
    }

    fun fetchTicketsByStatus(status: String) {
        viewModelScope.launch {
            _ticketsByStatusState.value = UiState.Loading
            try {
                val tickets = ticketsRepository.getTicketsByStatus(status)
                _ticketsByStatusState.value = UiState.Success(tickets)
                Log.d("TicketsViewModel", "Fetched ${tickets.size} tickets with status $status")
            } catch (e: Exception) {
                _ticketsByStatusState.value = UiState.Error(e)
                Log.e("TicketsViewModel", "Error fetching tickets by status", e)
            }
        }
    }

    fun refresh() {
        fetchAllTickets()
    }
}

