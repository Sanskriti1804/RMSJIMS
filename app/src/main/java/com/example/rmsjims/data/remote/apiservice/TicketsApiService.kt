package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Ticket

interface TicketsApiService {
    suspend fun getAllTickets(): List<Ticket>
    suspend fun getTicketById(id: Int): Ticket?
    suspend fun getTicketsByDepartment(departmentId: Int): List<Ticket>
    suspend fun getTicketsByEquipment(equipmentId: Int): List<Ticket>
    suspend fun getTicketsByStatus(status: String): List<Ticket>
}

