package com.example.rmsjims.ui.screens.data

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.data.schema.Building
import com.example.rmsjims.data.schema.Department
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.BuildingsViewModel
import com.example.rmsjims.viewmodel.DepartmentsViewModel
import com.example.rmsjims.viewmodel.EquipmentViewModel
import com.example.rmsjims.viewmodel.UserSessionViewModel
import org.koin.androidx.compose.koinViewModel

data class DepartmentWithDetails(
    val department: Department,
    val building: Building?,
    val equipmentCount: Int
)

@Composable
fun BuildingsScreen(
    navController: NavHostController,
    viewModel: BuildingsViewModel = koinViewModel(),
    departmentsViewModel: DepartmentsViewModel = koinViewModel(),
    equipmentViewModel: EquipmentViewModel = koinViewModel(),
    sessionViewModel: UserSessionViewModel = koinViewModel()
) {
    val buildingsState by viewModel.buildingsState.collectAsState()
    val departmentsState by departmentsViewModel.departmentsState.collectAsState()
    val equipmentState by equipmentViewModel.equipmentState.collectAsState()
    val userRole = sessionViewModel.userRole

    // Filter departments based on user role - show only departments that have equipment
    // Also calculate equipment count and get building info for each department
    val filteredDepartmentsWithDetails = remember(buildingsState, departmentsState, equipmentState, userRole) {
        when (val buildings = buildingsState) {
            is UiState.Success -> {
                when (val departments = departmentsState) {
                    is UiState.Success -> {
                        when (val equipment = equipmentState) {
                            is UiState.Success -> {
                                val buildingsMap = buildings.data.associateBy { it.id }
                                val equipmentCountByDept = equipment.data
                                    .mapNotNull { it.departmentId }
                                    .groupingBy { it }
                                    .eachCount()
                                
                                val filtered = if (userRole == UserRole.ADMIN) {
                                    departments.data
                                } else {
                                    val departmentIdsWithEquipment = equipment.data
                                        .mapNotNull { it.departmentId }
                                        .distinct()
                                    departments.data.filter { department ->
                                        department.id != null && department.id in departmentIdsWithEquipment
                                    }
                                }
                                
                                filtered.map { department ->
                                    val building = department.buildingId?.let { buildingsMap[it] }
                                    val equipmentCount = department.id?.let { equipmentCountByDept[it] ?: 0 } ?: 0
                                    DepartmentWithDetails(department, building, equipmentCount)
                                }
                            }
                            else -> emptyList()
                        }
                    }
                    else -> emptyList()
                }
            }
            else -> emptyList()
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Buildings",
                onNavigationClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            // Only show FAB for Admin users to add building
            if (userRole == UserRole.ADMIN) {
                FloatingActionButton(
                    onClick = {
                        // TODO: Navigate to Add Building screen
                        // navController.navigate(Screen.AddBuildingScreen.route)
                    },
                    containerColor = primaryColor,
                    contentColor = whiteColor,
                    shape = CircleShape,
                    modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(56.dp, 64.dp, 72.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Building",
                        modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(24.dp, 28.dp, 32.dp))
                    )
                }
            }
        },
        containerColor = whiteColor
    ) { paddingValues ->
        when (val state = departmentsState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = primaryColor)
                }
            }
            is UiState.Success -> {
                if (filteredDepartmentsWithDetails.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomLabel(
                            header = if (userRole == UserRole.ADMIN) "No departments found" else "No departments with equipment found",
                            headerColor = onSurfaceColor.copy(alpha = 0.6f),
                            fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                        )
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
                        items(filteredDepartmentsWithDetails) { deptWithDetails ->
                            DepartmentCard(
                                department = deptWithDetails.department,
                                building = deptWithDetails.building,
                                equipmentCount = deptWithDetails.equipmentCount,
                                onClick = {
                                    // Navigate to department details screen
                                    deptWithDetails.department.id?.let { departmentId ->
                                        navController.navigate(Screen.DepartmentDetailsScreen.createRoute(departmentId))
                                    }
                                }
                            )
                        }
                    }
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CustomLabel(
                        header = "Error: ${state.exception.message}",
                        headerColor = Color.Red,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun DepartmentCard(
    department: Department,
    building: Building?,
    equipmentCount: Int,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
                header = department.departmentName,
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
            building?.buildingNumber?.let {
                CustomLabel(
                    header = it,
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                department.address?.let {
                    CustomLabel(
                        header = it,
                        headerColor = onSurfaceColor.copy(alpha = 0.6f),
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
                    )
                }
                CustomLabel(
                    header = "$equipmentCount Equipment",
                    headerColor = primaryColor,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Medium
                )
            }
        }
    }
}

