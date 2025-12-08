package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Department

interface DepartmentsApiService {
    suspend fun getAllDepartments(): List<Department>
    suspend fun getDepartmentById(id: Int): Department?
    suspend fun getDepartmentsByBuilding(buildingId: Int): List<Department>
}

