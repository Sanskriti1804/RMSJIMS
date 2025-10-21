package com.example.rmsjims.repository

import com.example.rmsjims.data.remote.apiservice.FacilitesApiService
import com.example.rmsjims.data.schema.Facilities

class FacilitiesRepository(
    private val facilitiesApiService: FacilitesApiService
){
    suspend fun getFacilities() : List<Facilities>{
        return facilitiesApiService.getFacilities()
    }
}