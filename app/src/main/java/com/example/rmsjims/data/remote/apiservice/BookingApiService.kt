package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Booking

interface BookingApiService {
    suspend fun createBooking(booking: Booking): Result<Booking>
    suspend fun getAllBookings(): List<Booking>
    suspend fun getBookingById(id: Int): Booking?
    suspend fun getBookingsByUserId(userId: Int): List<Booking>
    suspend fun getBookingsByStatus(status: String): List<Booking>
    suspend fun updateBookingStatus(id: Int, status: String, adminNotes: String? = null, rejectionReason: String? = null, approvedBy: Int? = null): Result<Booking>
    suspend fun updateBooking(id: Int, booking: Booking): Result<Booking>
}

