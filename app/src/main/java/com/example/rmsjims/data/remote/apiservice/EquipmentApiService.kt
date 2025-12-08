package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Equipment

interface EquipmentApiService {
    suspend fun getAllEquipment(): List<Equipment>
    suspend fun getEquipmentById(id: Int): Equipment?
    suspend fun getEquipmentByDepartment(departmentId: Int): List<Equipment>
    suspend fun getEquipmentByRoom(roomId: Int): List<Equipment>
}

