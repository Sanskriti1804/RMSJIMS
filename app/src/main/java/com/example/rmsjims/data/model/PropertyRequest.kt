package com.example.rmsjims.data.model

import androidx.annotation.DrawableRes
import com.example.rmsjims.R

data class PropertyRequest(
    val id: String,
    val machineName: String,
    val department: String,
    val requestedSlot: String,
    val requestedBy: String,
    val requestedDepartment: String,
    val requestedDate: String,
    val priority: RequestPriority,
    val status: RequestStatus,
    @DrawableRes val imageRes: Int = R.drawable.temp,
    val description: String,
    val comments: List<RequestComment> = emptyList()
)

data class RequestComment(
    val author: String,
    val role: String,
    val timestamp: String,
    val message: String
)

enum class RequestPriority { HIGH, MEDIUM, LOW, CRITICAL }

enum class RequestStatus { PENDING, APPROVED, FLAGGED }

data class PropertyRequestGroup(
    val pendingApprovals: List<PropertyRequest>,
    val newRequests: List<PropertyRequest>,
    val priorityRequests: List<PropertyRequest>,
    val flaggedRequests: List<PropertyRequest>
)

object PropertyRequestProvider {
    fun sampleRequests(): PropertyRequestGroup {
        val pending = listOf(
            PropertyRequest(
                id = "REQ-001",
                machineName = "CNC Router Calibration",
                department = "Mechanical Lab",
                requestedSlot = "15 Nov 2025 • 10:00 AM - 12:00 PM",
                requestedBy = "Arun Patel",
                requestedDepartment = "Mechanical Engineering",
                requestedDate = "12 Nov 2025",
                priority = RequestPriority.HIGH,
                status = RequestStatus.PENDING,
                description = "Calibration required before the senior design review. Includes realignment and test cuts.",
                comments = listOf(
                    RequestComment(
                        author = "Nisha Rao",
                        role = "Lab Supervisor",
                        timestamp = "11:15 AM",
                        message = "Please ensure safety checklist is updated before calibration."
                    ),
                    RequestComment(
                        author = "Arun Patel",
                        role = "Requester",
                        timestamp = "11:20 AM",
                        message = "Checklist in progress, will upload before EOD."
                    )
                )
            )
        )

        val newRequests = listOf(
            PropertyRequest(
                id = "REQ-002",
                machineName = "Laser Cutter Maintenance",
                department = "Fabrication Studio",
                requestedSlot = "16 Nov 2025 • 03:00 PM - 05:00 PM",
                requestedBy = "Priya Singh",
                requestedDepartment = "Industrial Design",
                requestedDate = "11 Nov 2025",
                priority = RequestPriority.MEDIUM,
                status = RequestStatus.PENDING,
                description = "Routine maintenance with filter replacement and alignment verification.",
                comments = listOf(
                    RequestComment(
                        author = "Rahul Mehta",
                        role = "Maintenance Lead",
                        timestamp = "09:45 AM",
                        message = "Replacement filters already sourced."
                    )
                )
            ),
            PropertyRequest(
                id = "REQ-003",
                machineName = "3D Printer Capacity Extension",
                department = "Innovation Hub",
                requestedSlot = "17 Nov 2025 • 01:00 PM - 03:00 PM",
                requestedBy = "Sneha Varma",
                requestedDepartment = "Product Design",
                requestedDate = "10 Nov 2025",
                priority = RequestPriority.LOW,
                status = RequestStatus.PENDING,
                description = "Additional printer hours required for prototype batch.",
                comments = emptyList()
            )
        )

        val priorityRequests = listOf(
            PropertyRequest(
                id = "REQ-004",
                machineName = "Emergency HVAC Inspection",
                department = "Main Block",
                requestedSlot = "12 Nov 2025 • 08:30 AM - 09:30 AM",
                requestedBy = "Facilities Control",
                requestedDepartment = "Facilities",
                requestedDate = "12 Nov 2025",
                priority = RequestPriority.CRITICAL,
                status = RequestStatus.PENDING,
                description = "Temperature spike detected in server wing; immediate inspection required.",
                comments = listOf(
                    RequestComment(
                        author = "Ravi Kumar",
                        role = "Facilities Head",
                        timestamp = "08:05 AM",
                        message = "Technician dispatched, awaiting access clearance."
                    )
                )
            )
        )

        val flaggedRequests = listOf(
            PropertyRequest(
                id = "REQ-005",
                machineName = "Laser Safety Audit",
                department = "Fabrication Studio",
                requestedSlot = "18 Nov 2025 • 09:00 AM - 11:00 AM",
                requestedBy = "Compliance Team",
                requestedDepartment = "Compliance",
                requestedDate = "09 Nov 2025",
                priority = RequestPriority.HIGH,
                status = RequestStatus.FLAGGED,
                description = "Audit flagged due to missing protective gear inventory count.",
                comments = listOf(
                    RequestComment(
                        author = "Compliance Team",
                        role = "Auditor",
                        timestamp = "04:10 PM",
                        message = "Need confirmation of updated inventory before proceeding."
                    )
                )
            )
        )

        return PropertyRequestGroup(
            pendingApprovals = pending,
            newRequests = newRequests,
            priorityRequests = priorityRequests,
            flaggedRequests = flaggedRequests
        )
    }
}
