package com.example.labinventory.repository

import com.example.labinventory.data.remote.apiservice.FacilitesApiService
import com.example.labinventory.data.schema.Facilities

class FacilitiesRepository(
    private val facilitiesApiService: FacilitesApiService
){
    suspend fun getFacilities() : List<Facilities>{
        return facilitiesApiService.getFacilities()
    }
}