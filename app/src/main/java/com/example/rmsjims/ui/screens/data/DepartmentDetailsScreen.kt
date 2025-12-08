package com.example.rmsjims.ui.screens.data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Department
import com.example.rmsjims.data.schema.Equipment
import com.example.rmsjims.data.schema.Room
import com.example.rmsjims.data.schema.Ticket
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.DepartmentsViewModel
import com.example.rmsjims.viewmodel.EquipmentViewModel
import com.example.rmsjims.viewmodel.RoomsViewModel
import com.example.rmsjims.viewmodel.TicketsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DepartmentDetailsScreen(
    departmentId: Int,
    navController: NavHostController,
    departmentsViewModel: DepartmentsViewModel = koinViewModel(),
    equipmentViewModel: EquipmentViewModel = koinViewModel(),
    roomsViewModel: RoomsViewModel = koinViewModel(),
    ticketsViewModel: TicketsViewModel = koinViewModel()
) {
    val departmentState by departmentsViewModel.departmentsState.collectAsState()
    val equipmentState by equipmentViewModel.equipmentByDepartmentState.collectAsState()
    val roomsState by roomsViewModel.roomsByDepartmentState.collectAsState()
    val ticketsState by ticketsViewModel.ticketsByDepartmentState.collectAsState()

    // Fetch department details and related data
    LaunchedEffect(departmentId) {
        equipmentViewModel.fetchEquipmentByDepartment(departmentId)
        roomsViewModel.fetchRoomsByDepartment(departmentId)
        ticketsViewModel.fetchTicketsByDepartment(departmentId)
    }

    val department = when (val state = departmentState) {
        is UiState.Success -> state.data.find { it.id == departmentId }
        else -> null
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = department?.departmentName ?: "Department Details",
                onNavigationClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        containerColor = whiteColor
    ) { paddingValues ->
        if (department == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = primaryColor)
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(
                    horizontal = ResponsiveLayout.getHorizontalPadding(),
                    vertical = ResponsiveLayout.getVerticalPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
            ) {
                // Department Information Card
                item {
                    DepartmentInfoCard(department = department)
                }

                // Equipment Section
                item {
                    CustomLabel(
                        header = "Equipment",
                        headerColor = onSurfaceColor,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 22.sp, 24.sp),
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier.padding(top = ResponsiveLayout.getVerticalPadding())
                    )
                }
                when (val state = equipmentState) {
                    is UiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(ResponsiveLayout.getVerticalPadding()),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = primaryColor)
                            }
                        }
                    }
                    is UiState.Success -> {
                        if (state.data.isEmpty()) {
                            item {
                                CustomLabel(
                                    header = "No equipment found",
                                    headerColor = onSurfaceColor.copy(alpha = 0.6f),
                                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                                    modifier = Modifier.padding(ResponsiveLayout.getVerticalPadding())
                                )
                            }
                        } else {
                            items(state.data) { equipment ->
                                EquipmentDetailCard(equipment = equipment)
                            }
                        }
                    }
                    is UiState.Error -> {
                        item {
                            CustomLabel(
                                header = "Error loading equipment: ${state.exception.message}",
                                headerColor = Color.Red,
                                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                            )
                        }
                    }
                }

                // Rooms Section
                item {
                    CustomLabel(
                        header = "Rooms",
                        headerColor = onSurfaceColor,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 22.sp, 24.sp),
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier.padding(top = ResponsiveLayout.getVerticalPadding())
                    )
                }
                when (val state = roomsState) {
                    is UiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(ResponsiveLayout.getVerticalPadding()),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = primaryColor)
                            }
                        }
                    }
                    is UiState.Success -> {
                        if (state.data.isEmpty()) {
                            item {
                                CustomLabel(
                                    header = "No rooms found",
                                    headerColor = onSurfaceColor.copy(alpha = 0.6f),
                                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                                    modifier = Modifier.padding(ResponsiveLayout.getVerticalPadding())
                                )
                            }
                        } else {
                            items(state.data) { room ->
                                RoomDetailCard(room = room)
                            }
                        }
                    }
                    is UiState.Error -> {
                        item {
                            CustomLabel(
                                header = "Error loading rooms: ${state.exception.message}",
                                headerColor = Color.Red,
                                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                            )
                        }
                    }
                }

                // Tickets Section
                item {
                    CustomLabel(
                        header = "Tickets",
                        headerColor = onSurfaceColor,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 22.sp, 24.sp),
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                        modifier = Modifier.padding(top = ResponsiveLayout.getVerticalPadding())
                    )
                }
                when (val state = ticketsState) {
                    is UiState.Loading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(ResponsiveLayout.getVerticalPadding()),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = primaryColor)
                            }
                        }
                    }
                    is UiState.Success -> {
                        if (state.data.isEmpty()) {
                            item {
                                CustomLabel(
                                    header = "No tickets found",
                                    headerColor = onSurfaceColor.copy(alpha = 0.6f),
                                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                                    modifier = Modifier.padding(ResponsiveLayout.getVerticalPadding())
                                )
                            }
                        } else {
                            items(state.data) { ticket ->
                                TicketDetailCard(ticket = ticket)
                            }
                        }
                    }
                    is UiState.Error -> {
                        item {
                            CustomLabel(
                                header = "Error loading tickets: ${state.exception.message}",
                                headerColor = Color.Red,
                                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DepartmentInfoCard(department: Department) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ResponsiveLayout.getResponsiveSize(2.dp, 3.dp, 4.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
        ) {
            CustomLabel(
                header = "Department Information",
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
            )
            CustomLabel(
                header = "Name: ${department.departmentName}",
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
            )
            department.address?.let {
                CustomLabel(
                    header = "Address: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            department.buildingId?.let {
                CustomLabel(
                    header = "Building ID: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
        }
    }
}

@Composable
fun EquipmentDetailCard(equipment: Equipment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)),
        colors = CardDefaults.cardColors(containerColor = whiteColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ResponsiveLayout.getResponsiveSize(2.dp, 3.dp, 4.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
        ) {
            CustomLabel(
                header = equipment.name,
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
            equipment.location?.let {
                CustomLabel(
                    header = "Location: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            equipment.requestStatus?.let {
                CustomLabel(
                    header = "Status: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
        }
    }
}

@Composable
fun RoomDetailCard(room: Room) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)),
        colors = CardDefaults.cardColors(containerColor = whiteColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ResponsiveLayout.getResponsiveSize(2.dp, 3.dp, 4.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
        ) {
            CustomLabel(
                header = room.roomName,
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
            room.roomNumber?.let {
                CustomLabel(
                    header = "Room Number: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            room.location?.let {
                CustomLabel(
                    header = "Location: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            room.capacity?.let {
                CustomLabel(
                    header = "Capacity: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
        }
    }
}

@Composable
fun TicketDetailCard(ticket: Ticket) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)),
        colors = CardDefaults.cardColors(containerColor = whiteColor),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ResponsiveLayout.getResponsiveSize(2.dp, 3.dp, 4.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
        ) {
            CustomLabel(
                header = ticket.name,
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
            ticket.description?.let {
                CustomLabel(
                    header = it,
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            ticket.status?.let {
                CustomLabel(
                    header = "Status: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            ticket.urgency?.let {
                CustomLabel(
                    header = "Urgency: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
        }
    }
}

