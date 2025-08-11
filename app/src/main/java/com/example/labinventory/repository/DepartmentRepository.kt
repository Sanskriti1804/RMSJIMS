package com.example.labinventory.repository

import com.example.labinventory.data.schema.Department
import com.example.labinventory.data.remote.DepartmentApiService

class DepartmentRepository (
    private val departmentApiService: DepartmentApiService
){
    suspend fun fetchDepartment() : List<Department>{
        return departmentApiService.getDepartments()
    }
}