package com.example.rmsjims.repository

import com.example.rmsjims.data.remote.apiservice.EquipmentApiService
import com.example.rmsjims.data.schema.Equipment

class EquipmentRepository(
    private val equipmentApiService: EquipmentApiService
) {
    suspend fun getAllEquipment(): List<Equipment> {
        return equipmentApiService.getAllEquipment()
    }

    suspend fun getEquipmentById(id: Int): Equipment? {
        return equipmentApiService.getEquipmentById(id)
    }

    suspend fun getEquipmentByDepartment(departmentId: Int): List<Equipment> {
        return equipmentApiService.getEquipmentByDepartment(departmentId)
    }

    suspend fun getEquipmentByRoom(roomId: Int): List<Equipment> {
        return equipmentApiService.getEquipmentByRoom(roomId)
    }
}

