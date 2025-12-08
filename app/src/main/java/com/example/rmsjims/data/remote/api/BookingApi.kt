package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.schema.Booking
import com.example.rmsjims.data.remote.apiservice.BookingApiService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Instant

class BookingApi(
    private val supabaseClient: SupabaseClient
) : BookingApiService {

    private val table = supabaseClient.postgrest["bookings"]

    override suspend fun createBooking(booking: Booking): Result<Booking> = withContext(Dispatchers.IO) {
        try {
            Log.d("BookingApi", "Creating booking: ${booking.projectName}")
            val insertResult = table.insert(booking)
            val createdBooking = insertResult.decodeSingle<Booking>()
            Log.d("BookingApi", "Booking created successfully with id: ${createdBooking.id}")
            Result.success(createdBooking)
        } catch (e: Exception) {
            Log.e("BookingApi", "Error creating booking", e)
            Result.failure(e)
        }
    }

    override suspend fun getAllBookings(): List<Booking> = withContext(Dispatchers.IO) {
        try {
            Log.d("BookingApi", "Fetching all bookings")
            val bookings = table.select().decodeList<Booking>()
            Log.d("BookingApi", "Fetched ${bookings.size} bookings")
            bookings
        } catch (e: Exception) {
            Log.e("BookingApi", "Error fetching all bookings", e)
            emptyList()
        }
    }

    override suspend fun getBookingById(id: Int): Booking? = withContext(Dispatchers.IO) {
        try {
            Log.d("BookingApi", "Fetching booking by id: $id")
            val booking = table.select {
                filter {
                    eq("id", id)
                }
            }.decodeSingleOrNull<Booking>()
            booking
        } catch (e: Exception) {
            Log.e("BookingApi", "Error fetching booking by id", e)
            null
        }
    }

    override suspend fun getBookingsByUserId(userId: Int): List<Booking> = withContext(Dispatchers.IO) {
        try {
            Log.d("BookingApi", "Fetching bookings for user: $userId")
            val bookings = table.select {
                filter {
                    eq("user_id", userId)
                }
                order("created_at", Order.DESCENDING)
            }.decodeList<Booking>()
            Log.d("BookingApi", "Fetched ${bookings.size} bookings for user $userId")
            bookings
        } catch (e: Exception) {
            Log.e("BookingApi", "Error fetching bookings by user id", e)
            emptyList()
        }
    }

    override suspend fun getBookingsByStatus(status: String): List<Booking> = withContext(Dispatchers.IO) {
        try {
            Log.d("BookingApi", "Fetching bookings with status: $status")
            val bookings = table.select {
                filter {
                    eq("status", status)
                }
                order("created_at", Order.DESCENDING)
            }.decodeList<Booking>()
            Log.d("BookingApi", "Fetched ${bookings.size} bookings with status $status")
            bookings
        } catch (e: Exception) {
            Log.e("BookingApi", "Error fetching bookings by status", e)
            emptyList()
        }
    }

    override suspend fun updateBookingStatus(
        id: Int,
        status: String,
        adminNotes: String?,
        rejectionReason: String?,
        approvedBy: Int?
    ): Result<Booking> = withContext(Dispatchers.IO) {
        try {
            Log.d("BookingApi", "Updating booking $id status to $status")
            
            val updateData = buildMap<String, Any> {
                put("status", status)
                put("updated_at", Instant.now().toString())
                adminNotes?.let { put("admin_notes", it) }
                rejectionReason?.let { put("rejection_reason", it) }
                if (status == "approved") {
                    approvedBy?.let { put("approved_by", it) }
                    put("approved_at", Instant.now().toString())
                }
            }
            
            val updatedBooking = table.update(updateData) {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<Booking>()
            
            Log.d("BookingApi", "Booking status updated successfully")
            Result.success(updatedBooking)
        } catch (e: Exception) {
            Log.e("BookingApi", "Error updating booking status", e)
            Result.failure(e)
        }
    }

    override suspend fun updateBooking(id: Int, booking: Booking): Result<Booking> = withContext(Dispatchers.IO) {
        try {
            Log.d("BookingApi", "Updating booking: $id")
            val updateData = booking.copy(
                id = id,
                updatedAt = Instant.now().toString()
            )
            val updatedBooking = table.update(updateData) {
                filter {
                    eq("id", id)
                }
            }.decodeSingle<Booking>()
            Log.d("BookingApi", "Booking updated successfully")
            Result.success(updatedBooking)
        } catch (e: Exception) {
            Log.e("BookingApi", "Error updating booking", e)
            Result.failure(e)
        }
    }
}

