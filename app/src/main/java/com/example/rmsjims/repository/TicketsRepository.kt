package com.example.rmsjims.repository

import com.example.rmsjims.data.remote.apiservice.TicketsApiService
import com.example.rmsjims.data.schema.Ticket

class TicketsRepository(
    private val ticketsApiService: TicketsApiService
) {
    suspend fun getAllTickets(): List<Ticket> {
        return ticketsApiService.getAllTickets()
    }

    suspend fun getTicketById(id: Int): Ticket? {
        return ticketsApiService.getTicketById(id)
    }

    suspend fun getTicketsByDepartment(departmentId: Int): List<Ticket> {
        return ticketsApiService.getTicketsByDepartment(departmentId)
    }

    suspend fun getTicketsByEquipment(equipmentId: Int): List<Ticket> {
        return ticketsApiService.getTicketsByEquipment(equipmentId)
    }

    suspend fun getTicketsByStatus(status: String): List<Ticket> {
        return ticketsApiService.getTicketsByStatus(status)
    }
}

