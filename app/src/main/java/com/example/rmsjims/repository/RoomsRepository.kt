package com.example.rmsjims.repository

import com.example.rmsjims.data.remote.apiservice.RoomsApiService
import com.example.rmsjims.data.schema.Room

class RoomsRepository(
    private val roomsApiService: RoomsApiService
) {
    suspend fun getAllRooms(): List<Room> {
        return roomsApiService.getAllRooms()
    }

    suspend fun getRoomById(id: Int): Room? {
        return roomsApiService.getRoomById(id)
    }

    suspend fun getRoomsByBuilding(buildingId: Int): List<Room> {
        return roomsApiService.getRoomsByBuilding(buildingId)
    }

    suspend fun getRoomsByDepartment(departmentId: Int): List<Room> {
        return roomsApiService.getRoomsByDepartment(departmentId)
    }
}

