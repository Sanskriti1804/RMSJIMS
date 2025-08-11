package com.example.labinventory.data.remote.apiservice

import com.example.labinventory.data.schema.Facilities

interface FacilitesApiService {
    suspend fun getFacilities() : List<Facilities>
}