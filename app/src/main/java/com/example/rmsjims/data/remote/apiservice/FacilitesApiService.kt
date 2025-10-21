package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Facilities

interface FacilitesApiService {
    suspend fun getFacilities() : List<Facilities>
}