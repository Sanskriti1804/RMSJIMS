package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Building

interface BuildingsApiService {
    suspend fun getAllBuildings(): List<Building>
    suspend fun getBuildingById(id: Int): Building?
}

