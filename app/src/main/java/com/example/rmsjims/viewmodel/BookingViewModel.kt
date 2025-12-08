package com.example.rmsjims.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Booking
import com.example.rmsjims.repository.BookingRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookingViewModel(
    private val bookingRepository: BookingRepository
) : ViewModel() {

    private val _bookingsState = MutableStateFlow<UiState<List<Booking>>>(UiState.Loading)
    val bookingsState: StateFlow<UiState<List<Booking>>> = _bookingsState.asStateFlow()

    private val _createBookingState = MutableStateFlow<UiState<Booking>>(UiState.Loading)
    val createBookingState: StateFlow<UiState<Booking>> = _createBookingState.asStateFlow()

    private val _updateBookingState = MutableStateFlow<UiState<Booking>>(UiState.Loading)
    val updateBookingState: StateFlow<UiState<Booking>> = _updateBookingState.asStateFlow()

    init {
        loadAllBookings()
    }

    fun loadAllBookings() {
        viewModelScope.launch {
            _bookingsState.value = UiState.Loading
            try {
                val bookings = bookingRepository.getAllBookings()
                _bookingsState.value = UiState.Success(bookings)
                Log.d("BookingViewModel", "Loaded ${bookings.size} bookings")
            } catch (e: Exception) {
                _bookingsState.value = UiState.Error(e)
                Log.e("BookingViewModel", "Error loading bookings", e)
            }
        }
    }

    fun loadBookingsByStatus(status: String) {
        viewModelScope.launch {
            _bookingsState.value = UiState.Loading
            try {
                val bookings = bookingRepository.getBookingsByStatus(status)
                _bookingsState.value = UiState.Success(bookings)
                Log.d("BookingViewModel", "Loaded ${bookings.size} bookings with status: $status")
            } catch (e: Exception) {
                _bookingsState.value = UiState.Error(e)
                Log.e("BookingViewModel", "Error loading bookings by status", e)
            }
        }
    }

    fun loadBookingsByUserId(userId: Int) {
        viewModelScope.launch {
            _bookingsState.value = UiState.Loading
            try {
                val bookings = bookingRepository.getBookingsByUserId(userId)
                _bookingsState.value = UiState.Success(bookings)
                Log.d("BookingViewModel", "Loaded ${bookings.size} bookings for user: $userId")
            } catch (e: Exception) {
                _bookingsState.value = UiState.Error(e)
                Log.e("BookingViewModel", "Error loading bookings by user id", e)
            }
        }
    }

    fun createBooking(booking: Booking) {
        viewModelScope.launch {
            _createBookingState.value = UiState.Loading
            try {
                val result = bookingRepository.createBooking(booking)
                result.fold(
                    onSuccess = { createdBooking ->
                        _createBookingState.value = UiState.Success(createdBooking)
                        Log.d("BookingViewModel", "Booking created successfully: ${createdBooking.id}")
                        // Reload all bookings
                        loadAllBookings()
                    },
                    onFailure = { error ->
                        _createBookingState.value = UiState.Error(error)
                        Log.e("BookingViewModel", "Error creating booking", error)
                    }
                )
            } catch (e: Exception) {
                _createBookingState.value = UiState.Error(e)
                Log.e("BookingViewModel", "Exception creating booking", e)
            }
        }
    }

    fun updateBookingStatus(
        id: Int,
        status: String,
        adminNotes: String? = null,
        rejectionReason: String? = null,
        approvedBy: Int? = null
    ) {
        viewModelScope.launch {
            _updateBookingState.value = UiState.Loading
            try {
                val result = bookingRepository.updateBookingStatus(id, status, adminNotes, rejectionReason, approvedBy)
                result.fold(
                    onSuccess = { updatedBooking ->
                        _updateBookingState.value = UiState.Success(updatedBooking)
                        Log.d("BookingViewModel", "Booking status updated successfully: $id to $status")
                        // Reload bookings to reflect the update
                        loadAllBookings()
                    },
                    onFailure = { error ->
                        _updateBookingState.value = UiState.Error(error)
                        Log.e("BookingViewModel", "Error updating booking status", error)
                    }
                )
            } catch (e: Exception) {
                _updateBookingState.value = UiState.Error(e)
                Log.e("BookingViewModel", "Exception updating booking status", e)
            }
        }
    }
}
