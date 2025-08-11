package com.example.labinventory.data.remote

import com.example.labinventory.data.schema.Department

interface DepartmentApiService{
    suspend fun getDepartments() : List<Department>
}