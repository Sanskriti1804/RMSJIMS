package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.remote.apiservice.RoomsApiService
import com.example.rmsjims.data.schema.Room
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoomsApi(
    private val supabaseClient: SupabaseClient
) : RoomsApiService {

    private val table = supabaseClient.postgrest["rooms"]

    override suspend fun getAllRooms(): List<Room> = withContext(Dispatchers.IO) {
        Log.d("RoomsApi", "Fetching all rooms from Supabase")
        return@withContext try {
            val result = table.select {
                order("id", Order.ASCENDING)
            }.decodeList<Room>()
            Log.d("RoomsApi", "Successfully fetched ${result.size} rooms")
            result
        } catch (e: Exception) {
            Log.e("RoomsApi", "Error fetching rooms: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getRoomById(id: Int): Room? = withContext(Dispatchers.IO) {
        Log.d("RoomsApi", "Fetching room with id: $id")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("id", id)
                }
            }.decodeList<Room>().firstOrNull()
            Log.d("RoomsApi", if (result != null) "Room found" else "Room not found")
            result
        } catch (e: Exception) {
            Log.e("RoomsApi", "Error fetching room: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getRoomsByBuilding(buildingId: Int): List<Room> = withContext(Dispatchers.IO) {
        Log.d("RoomsApi", "Fetching rooms for building id: $buildingId")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("building_id", buildingId)
                }
                order("id", Order.ASCENDING)
            }.decodeList<Room>()
            Log.d("RoomsApi", "Successfully fetched ${result.size} rooms for building $buildingId")
            result
        } catch (e: Exception) {
            Log.e("RoomsApi", "Error fetching rooms by building: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getRoomsByDepartment(departmentId: Int): List<Room> = withContext(Dispatchers.IO) {
        Log.d("RoomsApi", "Fetching rooms for department id: $departmentId")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("department_id", departmentId)
                }
                order("id", Order.ASCENDING)
            }.decodeList<Room>()
            Log.d("RoomsApi", "Successfully fetched ${result.size} rooms for department $departmentId")
            result
        } catch (e: Exception) {
            Log.e("RoomsApi", "Error fetching rooms by department: ${e.message}", e)
            throw e
        }
    }
}

