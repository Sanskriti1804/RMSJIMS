package com.example.labinventory.data.remote.apiservice

import com.example.labinventory.data.schema.Department

interface DepartmentApiService{
    suspend fun getDepartments() : List<Department>
}