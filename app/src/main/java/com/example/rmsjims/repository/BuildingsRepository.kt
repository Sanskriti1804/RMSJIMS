package com.example.rmsjims.repository

import com.example.rmsjims.data.remote.apiservice.BuildingsApiService
import com.example.rmsjims.data.schema.Building

class BuildingsRepository(
    private val buildingsApiService: BuildingsApiService
) {
    suspend fun getAllBuildings(): List<Building> {
        return buildingsApiService.getAllBuildings()
    }

    suspend fun getBuildingById(id: Int): Building? {
        return buildingsApiService.getBuildingById(id)
    }
}

