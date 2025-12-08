package com.example.rmsjims.repository

import com.example.rmsjims.data.remote.apiservice.DepartmentsApiService
import com.example.rmsjims.data.schema.Department

class DepartmentsRepository(
    private val departmentsApiService: DepartmentsApiService
) {
    suspend fun getAllDepartments(): List<Department> {
        return departmentsApiService.getAllDepartments()
    }

    suspend fun getDepartmentById(id: Int): Department? {
        return departmentsApiService.getDepartmentById(id)
    }

    suspend fun getDepartmentsByBuilding(buildingId: Int): List<Department> {
        return departmentsApiService.getDepartmentsByBuilding(buildingId)
    }
}

