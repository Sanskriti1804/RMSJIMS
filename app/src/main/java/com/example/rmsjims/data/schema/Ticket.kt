package com.example.rmsjims.data.schema

import com.example.rmsjims.data.model.TicketCategory
import com.example.rmsjims.data.model.TicketPriority
import com.example.rmsjims.data.model.TicketStatus
import java.util.Date

data class Ticket(
    val ticket_id : String,
    val ticket_priority : TicketPriority,
    val ticket_status : TicketStatus,
    val ticket_category: TicketCategory,
    val created_at: Date,
    val updated_at : Date,
    val deadline : Date,
    val requested_by : String,
    val assignee : String,
//    val count : Int,
    val image_url : String
)