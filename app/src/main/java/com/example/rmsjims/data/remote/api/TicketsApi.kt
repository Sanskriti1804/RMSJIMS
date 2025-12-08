package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.remote.apiservice.TicketsApiService
import com.example.rmsjims.data.schema.Ticket
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TicketsApi(
    private val supabaseClient: SupabaseClient
) : TicketsApiService {

    private val table = supabaseClient.postgrest["tickets"]

    override suspend fun getAllTickets(): List<Ticket> = withContext(Dispatchers.IO) {
        Log.d("TicketsApi", "Fetching all tickets from Supabase")
        return@withContext try {
            val result = table.select {
                order("id", Order.ASCENDING)
            }.decodeList<Ticket>()
            Log.d("TicketsApi", "Successfully fetched ${result.size} tickets")
            result
        } catch (e: Exception) {
            Log.e("TicketsApi", "Error fetching tickets: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getTicketById(id: Int): Ticket? = withContext(Dispatchers.IO) {
        Log.d("TicketsApi", "Fetching ticket with id: $id")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("id", id)
                }
            }.decodeList<Ticket>().firstOrNull()
            Log.d("TicketsApi", if (result != null) "Ticket found" else "Ticket not found")
            result
        } catch (e: Exception) {
            Log.e("TicketsApi", "Error fetching ticket: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getTicketsByDepartment(departmentId: Int): List<Ticket> = withContext(Dispatchers.IO) {
        Log.d("TicketsApi", "Fetching tickets for department id: $departmentId")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("department_id", departmentId)
                }
                order("id", Order.ASCENDING)
            }.decodeList<Ticket>()
            Log.d("TicketsApi", "Successfully fetched ${result.size} tickets for department $departmentId")
            result
        } catch (e: Exception) {
            Log.e("TicketsApi", "Error fetching tickets by department: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getTicketsByEquipment(equipmentId: Int): List<Ticket> = withContext(Dispatchers.IO) {
        Log.d("TicketsApi", "Fetching tickets for equipment id: $equipmentId")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("equipment_id", equipmentId)
                }
                order("id", Order.ASCENDING)
            }.decodeList<Ticket>()
            Log.d("TicketsApi", "Successfully fetched ${result.size} tickets for equipment $equipmentId")
            result
        } catch (e: Exception) {
            Log.e("TicketsApi", "Error fetching tickets by equipment: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getTicketsByStatus(status: String): List<Ticket> = withContext(Dispatchers.IO) {
        Log.d("TicketsApi", "Fetching tickets with status: $status")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("status", status)
                }
                order("id", Order.ASCENDING)
            }.decodeList<Ticket>()
            Log.d("TicketsApi", "Successfully fetched ${result.size} tickets with status $status")
            result
        } catch (e: Exception) {
            Log.e("TicketsApi", "Error fetching tickets by status: ${e.message}", e)
            throw e
        }
    }
}

