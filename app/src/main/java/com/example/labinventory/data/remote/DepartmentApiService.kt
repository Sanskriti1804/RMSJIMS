package com.example.labinventory.data.remote

import com.example.labinventory.data.model.Department

interface DepartmentApiService{
    suspend fun getDepartments() : List<Department>
}