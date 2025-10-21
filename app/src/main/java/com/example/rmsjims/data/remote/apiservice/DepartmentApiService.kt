package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Department

interface DepartmentApiService{
    suspend fun getDepartments() : List<Department>
}