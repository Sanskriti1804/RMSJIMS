package com.example.rmsjims.data.schema

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Booking(
    val id: Int? = null,
    @SerialName("user_id") val userId: Int? = null,
    @SerialName("equipment_id") val equipmentId: Int? = null,
    @SerialName("booker_name") val bookerName: String, // Name of the person who made the booking
    @SerialName("product_name") val productName: String? = null, // Equipment/product name
    @SerialName("product_description") val productDescription: String? = null, // Equipment/product description
    @SerialName("booking_date") val bookingDate: String? = null, // Date when booking was made
    @SerialName("project_name") val projectName: String,
    @SerialName("guide_name") val guideName: String? = null,
    @SerialName("project_description") val projectDescription: String? = null,
    val branch: String? = null,
    val department: String? = null,
    @SerialName("team_members") val teamMembers: String? = null, // Comma-separated or JSON
    @SerialName("start_date") val startDate: String? = null,
    @SerialName("end_date") val endDate: String? = null,
    val status: String = "pending", // pending, approved, rejected
    @SerialName("admin_notes") val adminNotes: String? = null,
    @SerialName("rejection_reason") val rejectionReason: String? = null,
    @SerialName("approved_by") val approvedBy: Int? = null,
    @SerialName("approved_at") val approvedAt: String? = null,
    @SerialName("created_at") val createdAt: String? = null,
    @SerialName("updated_at") val updatedAt: String? = null
)

