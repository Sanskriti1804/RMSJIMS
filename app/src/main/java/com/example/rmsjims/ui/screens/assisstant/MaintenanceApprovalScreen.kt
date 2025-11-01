package com.example.rmsjims.ui.screens.assisstant

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun MaintenanceApprovalScreen(
    navController: NavHostController
) {
    // Placeholder maintenance requests
    val maintenanceRequests = listOf(
        MaintenanceRequest(
            equipmentName = "3D Printer XL",
            equipmentId = "EQ-2024-089",
            requestedBy = "Dr. Ravi Kumar",
            requestDate = "2024-01-20",
            priority = "High",
            description = "Printer head needs replacement. Calibration issues reported.",
            estimatedCost = "₹15,000"
        ),
        MaintenanceRequest(
            equipmentName = "Oscilloscope Pro",
            equipmentId = "EQ-2024-045",
            requestedBy = "Prof. Meera Sharma",
            requestDate = "2024-01-19",
            priority = "Medium",
            description = "Regular maintenance and calibration check.",
            estimatedCost = "₹5,000"
        ),
        MaintenanceRequest(
            equipmentName = "High-Performance Server",
            equipmentId = "EQ-2024-001",
            requestedBy = "Akash Singh",
            requestDate = "2024-01-18",
            priority = "High",
            description = "Server overheating. Cooling system needs repair.",
            estimatedCost = "₹25,000"
        )
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Maintenance Approval",
                onNavigationClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        containerColor = whiteColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Summary Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
                colors = CardDefaults.cardColors(
                    containerColor = onSurfaceVariant
                ),
                shape = RectangleShape
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = ResponsiveLayout.getHorizontalPadding(),
                        vertical = ResponsiveLayout.getVerticalPadding()
                    )
                ) {
                    CustomLabel(
                        header = "Pending Approvals",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                        headerColor = onSurfaceColor.copy(0.7f)
                    )
                    Spacer(modifier = Modifier.height(pxToDp(8)))
                    CustomLabel(
                        header = "${maintenanceRequests.size} Requests",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
                        headerColor = primaryColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Requests List
            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = ResponsiveLayout.getHorizontalPadding(),
                    vertical = ResponsiveLayout.getVerticalPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
            ) {
                items(maintenanceRequests) { request ->
                    MaintenanceRequestCard(request = request)
                }
            }
        }
    }
}

@Composable
fun MaintenanceRequestCard(request: MaintenanceRequest) {
    val priorityColor = when (request.priority) {
        "High" -> Color(0xFFE64646)
        "Medium" -> Color(0xFFE67824)
        "Low" -> Color(0xFF26BB64C)
        else -> onSurfaceColor
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = onSurfaceVariant
        ),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
            ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(12))
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(pxToDp(4))
                ) {
                    CustomLabel(
                        header = request.equipmentName,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                        headerColor = onSurfaceColor
                    )
                    CustomLabel(
                        header = "ID: ${request.equipmentId}",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = onSurfaceColor.copy(0.6f)
                    )
                }
                
                Box(
                    modifier = Modifier
                        .background(
                            color = priorityColor.copy(0.2f),
                            shape = RoundedCornerShape(pxToDp(12))
                        )
                        .padding(
                            horizontal = pxToDp(12),
                            vertical = pxToDp(6)
                        )
                ) {
                    CustomLabel(
                        header = request.priority,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = priorityColor
                    )
                }
            }
            
            // Details
            Column(
                verticalArrangement = Arrangement.spacedBy(pxToDp(8))
            ) {
                DetailRoww("Requested By", request.requestedBy)
                DetailRoww("Request Date", request.requestDate)
                CustomLabel(
                    header = "Description:",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor.copy(0.7f)
                )
                CustomLabel(
                    header = request.description,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor
                )
                DetailRoww("Estimated Cost", request.estimatedCost)
            }
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                AppButton(
                    buttonText = "Approve",
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )
                AppButton(
                    buttonText = "Reject",
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    containerColor = Color(0xFFE64646),
                    contentColor = whiteColor
                )
                AppButton(
                    buttonText = "View Details",
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    containerColor = onSurfaceVariant,
                    contentColor = onSurfaceColor
                )
            }
        }
    }
}

//@Composable
//fun DetailRow(label: String, value: String) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        CustomLabel(
//            header = "$label:",
//            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
//            headerColor = onSurfaceColor.copy(0.7f)
//        )
//        CustomLabel(
//            header = value,
//            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
//            headerColor = onSurfaceColor
//        )
//    }
//}

// Placeholder data class
data class MaintenanceRequest(
    val equipmentName: String,
    val equipmentId: String,
    val requestedBy: String,
    val requestDate: String,
    val priority: String,
    val description: String,
    val estimatedCost: String
)

@Preview(showBackground = true)
@Composable
fun MaintenanceApprovalScreenPreview() {
    val navController = rememberNavController()
    MaintenanceApprovalScreen(navController = navController)
}
