package com.example.labinventory.data.remote

import com.example.labinventory.data.schema.Facilities

interface FacilitesApiService {
    suspend fun getFacilities() : List<Facilities>
}