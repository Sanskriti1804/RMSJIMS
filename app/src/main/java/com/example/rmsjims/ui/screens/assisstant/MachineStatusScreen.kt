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
fun MachineStatusScreen(
    navController: NavHostController
) {
    // Placeholder machine data
    val machines = listOf(
        Machine(
            name = "High-Performance Server",
            id = "EQ-2024-001",
            status = "Available",
            location = "Lab A-101",
            lastMaintenance = "2024-01-10",
            nextMaintenance = "2024-02-10"
        ),
        Machine(
            name = "Oscilloscope Pro",
            id = "EQ-2024-045",
            status = "In Use",
            location = "Lab B-205",
            lastMaintenance = "2024-01-05",
            nextMaintenance = "2024-02-05"
        ),
        Machine(
            name = "3D Printer XL",
            id = "EQ-2024-089",
            status = "Maintenance",
            location = "Lab C-301",
            lastMaintenance = "2024-01-15",
            nextMaintenance = "2024-01-25"
        ),
        Machine(
            name = "Spectrometer",
            id = "EQ-2024-123",
            status = "Available",
            location = "Lab D-405",
            lastMaintenance = "2024-01-12",
            nextMaintenance = "2024-02-12"
        ),
        Machine(
            name = "Microscope Advanced",
            id = "EQ-2024-156",
            status = "In Use",
            location = "Lab E-502",
            lastMaintenance = "2024-01-08",
            nextMaintenance = "2024-02-08"
        )
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Machine Status",
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
            
            // Status Summary
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
                horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
            ) {
                StatusSummaryCard("Available", machines.count { it.status == "Available" }, Color(0xFF26BB64C))
                StatusSummaryCard("In Use", machines.count { it.status == "In Use" }, primaryColor)
                StatusSummaryCard("Maintenance", machines.count { it.status == "Maintenance" }, Color(0xFFE64646))
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Machines List
            CustomLabel(
                header = "All Machines",
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                modifier = Modifier.padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
                headerColor = onSurfaceColor
            )
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp)))
            
            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = ResponsiveLayout.getHorizontalPadding(),
                    vertical = ResponsiveLayout.getVerticalPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
            ) {
                items(machines) { machine ->
                    MachineCard(machine = machine)
                }
            }
        }
    }
}

@Composable
fun StatusSummaryCard(label: String, count: Int, color: Color) {
    Card(
        modifier = Modifier.weight(1f),
        colors = CardDefaults.cardColors(
            containerColor = onSurfaceVariant
        ),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp),
                vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(pxToDp(8))
        ) {
            CustomLabel(
                header = "$count",
                fontSize = ResponsiveLayout.getResponsiveFontSize(24.sp, 28.sp, 32.sp),
                headerColor = color
            )
            CustomLabel(
                header = label,
                fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                headerColor = onSurfaceColor.copy(0.7f)
            )
        }
    }
}

@Composable
fun MachineCard(machine: Machine) {
    val statusColor = when (machine.status) {
        "Available" -> Color(0xFF26BB64C)
        "In Use" -> primaryColor
        "Maintenance" -> Color(0xFFE64646)
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
                        header = machine.name,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                        headerColor = onSurfaceColor
                    )
                    CustomLabel(
                        header = "ID: ${machine.id}",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = onSurfaceColor.copy(0.6f)
                    )
                }
                
                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor.copy(0.2f),
                            shape = RoundedCornerShape(pxToDp(12))
                        )
                        .padding(
                            horizontal = pxToDp(12),
                            vertical = pxToDp(6)
                        )
                ) {
                    CustomLabel(
                        header = machine.status,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = statusColor
                    )
                }
            }
            
            // Details
            Column(
                verticalArrangement = Arrangement.spacedBy(pxToDp(8))
            ) {
                DetailRow("Location", machine.location)
                DetailRow("Last Maintenance", machine.lastMaintenance)
                DetailRow("Next Maintenance", machine.nextMaintenance)
            }
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                AppButton(
                    buttonText = "View Details",
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )
                AppButton(
                    buttonText = "Update Status",
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomLabel(
            header = "$label:",
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
            headerColor = onSurfaceColor.copy(0.7f)
        )
        CustomLabel(
            header = value,
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
            headerColor = onSurfaceColor
        )
    }
}

// Placeholder data class
data class Machine(
    val name: String,
    val id: String,
    val status: String,
    val location: String,
    val lastMaintenance: String,
    val nextMaintenance: String
)

@Preview(showBackground = true)
@Composable
fun MachineStatusScreenPreview() {
    val navController = rememberNavController()
    MachineStatusScreen(navController = navController)
}
