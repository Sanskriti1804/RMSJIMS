package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.remote.apiservice.BuildingsApiService
import com.example.rmsjims.data.schema.Building
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Columns
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BuildingsApi(
    private val supabaseClient: SupabaseClient
) : BuildingsApiService {

    private val table = supabaseClient.postgrest["buildings"]

    override suspend fun getAllBuildings(): List<Building> = withContext(Dispatchers.IO) {
        Log.d("BuildingsApi", "Fetching all buildings from Supabase")
        return@withContext try {
            val result = table.select {
                order("id", Order.ASCENDING)
            }.decodeList<Building>()
            Log.d("BuildingsApi", "Successfully fetched ${result.size} buildings")
            result
        } catch (e: Exception) {
            Log.e("BuildingsApi", "Error fetching buildings: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getBuildingById(id: Int): Building? = withContext(Dispatchers.IO) {
        Log.d("BuildingsApi", "Fetching building with id: $id")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("id", id)
                }
            }.decodeList<Building>().firstOrNull()
            Log.d("BuildingsApi", if (result != null) "Building found" else "Building not found")
            result
        } catch (e: Exception) {
            Log.e("BuildingsApi", "Error fetching building: ${e.message}", e)
            throw e
        }
    }
}

