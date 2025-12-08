package com.example.rmsjims.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Booking
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.BookingViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookingApprovalScreen(
    navController: NavHostController,
    bookingViewModel: BookingViewModel = koinViewModel()
) {
    val bookingsState by bookingViewModel.bookingsState.collectAsState()
    val updateBookingState by bookingViewModel.updateBookingState.collectAsState()
    var selectedFilter by remember { mutableStateOf("pending") }
    
    // Load pending bookings by default
    LaunchedEffect(Unit) {
        bookingViewModel.loadBookingsByStatus("pending")
    }
    
    // Reload bookings when status is updated
    LaunchedEffect(updateBookingState) {
        when (updateBookingState) {
            is UiState.Success -> {
                // Reload the current filter after successful update
                bookingViewModel.loadBookingsByStatus(selectedFilter)
            }
            else -> {}
        }
    }
    
    // Refresh bookings periodically or when screen becomes visible
    LaunchedEffect(selectedFilter) {
        bookingViewModel.loadBookingsByStatus(selectedFilter)
    }
    
    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Booking Approvals",
                navController = navController
            )
        },
        containerColor = whiteColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(ResponsiveLayout.getHorizontalPadding())
        ) {
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(20.dp, 24.dp, 28.dp)))
            
            // Filter buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedFilter == "pending",
                    onClick = { 
                        selectedFilter = "pending"
                        bookingViewModel.loadBookingsByStatus("pending") 
                    },
                    label = { Text("Pending") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = selectedFilter == "approved",
                    onClick = { 
                        selectedFilter = "approved"
                        bookingViewModel.loadBookingsByStatus("approved") 
                    },
                    label = { Text("Approved") },
                    modifier = Modifier.weight(1f)
                )
                FilterChip(
                    selected = selectedFilter == "rejected",
                    onClick = { 
                        selectedFilter = "rejected"
                        bookingViewModel.loadBookingsByStatus("rejected") 
                    },
                    label = { Text("Rejected") },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            when (val state = bookingsState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = primaryColor)
                    }
                }
                is UiState.Success -> {
                    if (state.data.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CustomLabel(
                                header = "No bookings found",
                                headerColor = onSurfaceColor.copy(alpha = 0.6f),
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.data) { booking ->
                                BookingApprovalCard(
                                    booking = booking,
                                    onApprove = { bookingId ->
                                        bookingViewModel.updateBookingStatus(
                                            id = bookingId,
                                            status = "approved",
                                            approvedBy = null // TODO: Get from user session
                                        )
                                    },
                                    onReject = { bookingId ->
                                        bookingViewModel.updateBookingStatus(
                                            id = bookingId,
                                            status = "rejected",
                                            rejectionReason = "Rejected by admin"
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
                is UiState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CustomLabel(
                                header = "Error: ${state.exception.message}",
                                headerColor = Color.Red,
                                fontSize = 14.sp
                            )
                            AppButton(
                                buttonText = "Retry",
                                onClick = { bookingViewModel.loadBookingsByStatus("pending") }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BookingApprovalCard(
    booking: Booking,
    onApprove: (Int) -> Unit,
    onReject: (Int) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = whiteColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Status badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CustomLabel(
                    header = "Booking #${booking.id}",
                    headerColor = onSurfaceColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Box(
                    modifier = Modifier
                        .background(
                            color = when (booking.status) {
                                "pending" -> Color(0xFFFF9800).copy(alpha = 0.2f)
                                "approved" -> Color(0xFF4CAF50).copy(alpha = 0.2f)
                                "rejected" -> Color(0xFFF44336).copy(alpha = 0.2f)
                                else -> Color.Gray.copy(alpha = 0.2f)
                            },
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    CustomLabel(
                        header = booking.status.uppercase(),
                        headerColor = when (booking.status) {
                            "pending" -> Color(0xFFFF9800)
                            "approved" -> Color(0xFF4CAF50)
                            "rejected" -> Color(0xFFF44336)
                            else -> Color.Gray
                        },
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Divider()
            
            // Booking details
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomLabel(
                    header = "Booker: ${booking.bookerName}",
                    headerColor = onSurfaceColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                CustomLabel(
                    header = "Project: ${booking.projectName}",
                    headerColor = onSurfaceColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
                booking.productName?.let {
                    CustomLabel(
                        header = "Product: $it",
                        headerColor = onSurfaceColor.copy(alpha = 0.8f),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
                booking.productDescription?.let {
                    CustomLabel(
                        header = "Product Description: $it",
                        headerColor = onSurfaceColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
                booking.bookingDate?.let {
                    CustomLabel(
                        header = "Booking Date: $it",
                        headerColor = onSurfaceColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
                booking.equipmentId?.let {
                    CustomLabel(
                        header = "Equipment ID: $it",
                        headerColor = onSurfaceColor.copy(alpha = 0.6f),
                        fontSize = 13.sp
                    )
                }
                booking.guideName?.let {
                    CustomLabel(
                        header = "Guide: $it",
                        headerColor = onSurfaceColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
                booking.branch?.let {
                    CustomLabel(
                        header = "Branch: $it",
                        headerColor = onSurfaceColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
                booking.department?.let {
                    CustomLabel(
                        header = "Department: $it",
                        headerColor = onSurfaceColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
                booking.projectDescription?.let {
                    CustomLabel(
                        header = "Description: $it",
                        headerColor = onSurfaceColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
                booking.teamMembers?.let {
                    CustomLabel(
                        header = "Team: $it",
                        headerColor = onSurfaceColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
                if (booking.startDate != null && booking.endDate != null) {
                    CustomLabel(
                        header = "Dates: ${booking.startDate} to ${booking.endDate}",
                        headerColor = onSurfaceColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }
            
            // Action buttons (only show for pending bookings)
            if (booking.status == "pending" && booking.id != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AppButton(
                        buttonText = "Approve",
                        onClick = { onApprove(booking.id) },
                        modifier = Modifier.weight(1f),
                        containerColor = Color(0xFF4CAF50)
                    )
                    AppButton(
                        buttonText = "Reject",
                        onClick = { onReject(booking.id) },
                        modifier = Modifier.weight(1f),
                        containerColor = Color(0xFFF44336)
                    )
                }
            }
            
            // Show admin notes if available
            booking.adminNotes?.let {
                Divider()
                CustomLabel(
                    header = "Admin Notes: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.8f),
                    fontSize = 13.sp
                )
            }
            
            // Show rejection reason if rejected
            booking.rejectionReason?.let {
                Divider()
                CustomLabel(
                    header = "Rejection Reason: $it",
                    headerColor = Color(0xFFF44336),
                    fontSize = 13.sp
                )
            }
        }
    }
}

