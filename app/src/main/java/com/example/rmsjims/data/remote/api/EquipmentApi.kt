package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.remote.apiservice.EquipmentApiService
import com.example.rmsjims.data.schema.Equipment
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EquipmentApi(
    private val supabaseClient: SupabaseClient
) : EquipmentApiService {

    private val table = supabaseClient.postgrest["equipment"]

    override suspend fun getAllEquipment(): List<Equipment> = withContext(Dispatchers.IO) {
        Log.d("EquipmentApi", "Fetching all equipment from Supabase")
        return@withContext try {
            val result = table.select {
                order("id", Order.ASCENDING)
            }.decodeList<Equipment>()
            Log.d("EquipmentApi", "Successfully fetched ${result.size} equipment items")
            result
        } catch (e: Exception) {
            Log.e("EquipmentApi", "Error fetching equipment: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getEquipmentById(id: Int): Equipment? = withContext(Dispatchers.IO) {
        Log.d("EquipmentApi", "Fetching equipment with id: $id")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("id", id)
                }
            }.decodeList<Equipment>().firstOrNull()
            Log.d("EquipmentApi", if (result != null) "Equipment found" else "Equipment not found")
            result
        } catch (e: Exception) {
            Log.e("EquipmentApi", "Error fetching equipment: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getEquipmentByDepartment(departmentId: Int): List<Equipment> = withContext(Dispatchers.IO) {
        Log.d("EquipmentApi", "Fetching equipment for department id: $departmentId")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("department_id", departmentId)
                }
                order("id", Order.ASCENDING)
            }.decodeList<Equipment>()
            Log.d("EquipmentApi", "Successfully fetched ${result.size} equipment items for department $departmentId")
            result
        } catch (e: Exception) {
            Log.e("EquipmentApi", "Error fetching equipment by department: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getEquipmentByRoom(roomId: Int): List<Equipment> = withContext(Dispatchers.IO) {
        Log.d("EquipmentApi", "Fetching equipment for room id: $roomId")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("room_id", roomId)
                }
                order("id", Order.ASCENDING)
            }.decodeList<Equipment>()
            Log.d("EquipmentApi", "Successfully fetched ${result.size} equipment items for room $roomId")
            result
        } catch (e: Exception) {
            Log.e("EquipmentApi", "Error fetching equipment by room: ${e.message}", e)
            throw e
        }
    }
}

