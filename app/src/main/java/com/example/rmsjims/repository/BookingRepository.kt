package com.example.rmsjims.repository

import com.example.rmsjims.data.schema.Booking
import com.example.rmsjims.data.remote.apiservice.BookingApiService

class BookingRepository(
    private val bookingApiService: BookingApiService
) {
    suspend fun createBooking(booking: Booking): Result<Booking> {
        return bookingApiService.createBooking(booking)
    }

    suspend fun getAllBookings(): List<Booking> {
        return bookingApiService.getAllBookings()
    }

    suspend fun getBookingById(id: Int): Booking? {
        return bookingApiService.getBookingById(id)
    }

    suspend fun getBookingsByUserId(userId: Int): List<Booking> {
        return bookingApiService.getBookingsByUserId(userId)
    }

    suspend fun getBookingsByStatus(status: String): List<Booking> {
        return bookingApiService.getBookingsByStatus(status)
    }

    suspend fun updateBookingStatus(
        id: Int,
        status: String,
        adminNotes: String? = null,
        rejectionReason: String? = null,
        approvedBy: Int? = null
    ): Result<Booking> {
        return bookingApiService.updateBookingStatus(id, status, adminNotes, rejectionReason, approvedBy)
    }

    suspend fun updateBooking(id: Int, booking: Booking): Result<Booking> {
        return bookingApiService.updateBooking(id, booking)
    }
}

