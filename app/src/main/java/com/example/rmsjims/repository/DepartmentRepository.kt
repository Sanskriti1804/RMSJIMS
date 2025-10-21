package com.example.rmsjims.repository

import com.example.rmsjims.data.schema.Department
import com.example.rmsjims.data.remote.apiservice.DepartmentApiService

class DepartmentRepository (
    private val departmentApiService: DepartmentApiService
){
    suspend fun fetchDepartment() : List<Department>{
        return departmentApiService.getDepartments()
    }
}