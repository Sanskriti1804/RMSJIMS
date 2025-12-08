package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.remote.apiservice.DepartmentsApiService
import com.example.rmsjims.data.schema.Department
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.query.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DepartmentsApi(
    private val supabaseClient: SupabaseClient
) : DepartmentsApiService {

    private val table = supabaseClient.postgrest["departments"]

    override suspend fun getAllDepartments(): List<Department> = withContext(Dispatchers.IO) {
        Log.d("DepartmentsApi", "Fetching all departments from Supabase")
        return@withContext try {
            val result = table.select {
                order("id", Order.ASCENDING)
            }.decodeList<Department>()
            Log.d("DepartmentsApi", "Successfully fetched ${result.size} departments")
            result
        } catch (e: Exception) {
            Log.e("DepartmentsApi", "Error fetching departments: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getDepartmentById(id: Int): Department? = withContext(Dispatchers.IO) {
        Log.d("DepartmentsApi", "Fetching department with id: $id")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("id", id)
                }
            }.decodeList<Department>().firstOrNull()
            Log.d("DepartmentsApi", if (result != null) "Department found" else "Department not found")
            result
        } catch (e: Exception) {
            Log.e("DepartmentsApi", "Error fetching department: ${e.message}", e)
            throw e
        }
    }

    override suspend fun getDepartmentsByBuilding(buildingId: Int): List<Department> = withContext(Dispatchers.IO) {
        Log.d("DepartmentsApi", "Fetching departments for building id: $buildingId")
        return@withContext try {
            val result = table.select {
                filter {
                    eq("building_id", buildingId)
                }
                order("id", Order.ASCENDING)
            }.decodeList<Department>()
            Log.d("DepartmentsApi", "Successfully fetched ${result.size} departments for building $buildingId")
            result
        } catch (e: Exception) {
            Log.e("DepartmentsApi", "Error fetching departments by building: ${e.message}", e)
            throw e
        }
    }
}

