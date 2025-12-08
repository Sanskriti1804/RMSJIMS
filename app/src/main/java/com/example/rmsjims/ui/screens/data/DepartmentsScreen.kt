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
import com.example.rmsjims.data.schema.Department
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
import com.example.rmsjims.viewmodel.UserSessionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun DepartmentsScreen(
    navController: NavHostController,
    viewModel: DepartmentsViewModel = koinViewModel(),
    equipmentViewModel: EquipmentViewModel = koinViewModel(),
    sessionViewModel: UserSessionViewModel = koinViewModel()
) {
    val departmentsState by viewModel.departmentsState.collectAsState()
    val equipmentState by equipmentViewModel.equipmentState.collectAsState()
    val userRole = sessionViewModel.userRole

    // Filter departments based on user role
    val filteredDepartments = remember(departmentsState, equipmentState, userRole) {
        when (val departments = departmentsState) {
            is UiState.Success -> {
                when (val equipment = equipmentState) {
                    is UiState.Success -> {
                        if (userRole == UserRole.ADMIN) {
                            // Admin sees all departments
                            departments.data
                        } else {
                            // Staff/Assistant only see departments that have equipment
                            val departmentIdsWithEquipment = equipment.data
                                .mapNotNull { it.departmentId }
                                .distinct()
                            departments.data.filter { department ->
                                department.id != null && department.id in departmentIdsWithEquipment
                            }
                        }
                    }
                    else -> departments.data
                }
            }
            else -> emptyList()
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Departments",
                onNavigationClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
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
                if (filteredDepartments.isEmpty()) {
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
                        items(filteredDepartments) { department ->
                            DepartmentCard(department = department)
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
fun DepartmentCard(department: Department) {
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
                header = department.departmentName,
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
            department.address?.let {
                CustomLabel(
                    header = it,
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
        }
    }
}

