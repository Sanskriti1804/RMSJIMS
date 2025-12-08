package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Room

interface RoomsApiService {
    suspend fun getAllRooms(): List<Room>
    suspend fun getRoomById(id: Int): Room?
    suspend fun getRoomsByBuilding(buildingId: Int): List<Room>
    suspend fun getRoomsByDepartment(departmentId: Int): List<Room>
}

