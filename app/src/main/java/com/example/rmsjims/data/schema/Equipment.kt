package com.example.rmsjims.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Equipment(
    val id: Int? = null,
    val name: String,
    val image: String? = null,
    val location: String? = null,
    @SerialName("request_status") val requestStatus: String? = null,
    @SerialName("request_urgency") val requestUrgency: String? = null,
    @SerialName("incharge_id") val inchargeId: Int? = null,
    @SerialName("incharge_name") val inchargeName: String? = null,
    @SerialName("incharge_designation") val inchargeDesignation: String? = null,
    @SerialName("incharge_email") val inchargeEmail: String? = null,
    @SerialName("incharge_phone") val inchargePhone: String? = null,
    @SerialName("booking_date") val bookingDate: String? = null,
    @SerialName("requester_name") val requesterName: String? = null,
    @SerialName("department_id") val departmentId: Int? = null,
    @SerialName("room_id") val roomId: Int? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

