package com.example.rmsjims.ui.screens.data

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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.data.schema.Equipment
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.EquipmentViewModel
import com.example.rmsjims.viewmodel.DepartmentsViewModel
import com.example.rmsjims.viewmodel.UserSessionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun EquipmentScreen(
    navController: NavHostController,
    viewModel: EquipmentViewModel = koinViewModel(),
    departmentsViewModel: DepartmentsViewModel = koinViewModel(),
    sessionViewModel: UserSessionViewModel = koinViewModel()
) {
    val equipmentState by viewModel.equipmentState.collectAsState()
    val departmentsState by departmentsViewModel.departmentsState.collectAsState()
    val userRole = sessionViewModel.userRole

    // Filter equipment based on user role
    val filteredEquipment = remember(equipmentState, departmentsState, userRole) {
        when (val equipment = equipmentState) {
            is UiState.Success -> {
                when (val departments = departmentsState) {
                    is UiState.Success -> {
                        if (userRole == UserRole.ADMIN) {
                            // Admin sees all equipment
                            equipment.data
                        } else {
                            // Staff/Assistant only see equipment in departments that have equipment
                            val departmentIdsWithEquipment = departments.data
                                .mapNotNull { it.id }
                                .distinct()
                            equipment.data.filter { eq ->
                                eq.departmentId != null && eq.departmentId in departmentIdsWithEquipment
                            }
                        }
                    }
                    else -> equipment.data
                }
            }
            else -> emptyList()
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Equipment",
                onNavigationClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            // Only show FAB for Admin users
            if (userRole == UserRole.ADMIN) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("new_equipment_screen")
                    },
                    containerColor = primaryColor,
                    contentColor = whiteColor,
                    shape = CircleShape,
                    modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(56.dp, 64.dp, 72.dp))
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Equipment",
                        modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(24.dp, 28.dp, 32.dp))
                    )
                }
            }
        },
        containerColor = whiteColor
    ) { paddingValues ->
        when (val state = equipmentState) {
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
                if (filteredEquipment.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        CustomLabel(
                            header = if (userRole == UserRole.ADMIN) "No equipment found" else "No equipment in departments found",
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
                        items(filteredEquipment) { equipment ->
                            EquipmentCard(equipment = equipment)
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
fun EquipmentCard(equipment: Equipment) {
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
            equipment.image?.let { imageUrl ->
                AsyncImage(
                    model = imageUrl,
                    contentDescription = equipment.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(ResponsiveLayout.getResponsiveSize(150.dp, 200.dp, 250.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            CustomLabel(
                header = equipment.name,
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
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
            equipment.inchargeName?.let {
                CustomLabel(
                    header = "Incharge: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
        }
    }
}

