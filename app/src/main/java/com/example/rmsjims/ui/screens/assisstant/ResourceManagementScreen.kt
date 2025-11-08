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
fun ResourceManagementScreen(
    navController: NavHostController
) {
    // Placeholder resources
    val resources = listOf(
        Resource(
            name = "Lab Space A-101",
            type = "Lab Space",
            capacity = "20 students",
            currentUsage = "12 students",
            status = "Available",
            location = "Building A, Floor 1"
        ),
        Resource(
            name = "Conference Room B",
            type = "Meeting Room",
            capacity = "15 people",
            currentUsage = "15 people",
            status = "Fully Booked",
            location = "Building B, Floor 2"
        ),
        Resource(
            name = "Projector System",
            type = "Equipment",
            capacity = "1 unit",
            currentUsage = "In use",
            status = "In Use",
            location = "Lab A-101"
        ),
        Resource(
            name = "Storage Room C",
            type = "Storage",
            capacity = "100 items",
            currentUsage = "65 items",
            status = "Available",
            location = "Building C, Basement"
        )
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Resource Management",
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
            
            // Summary Cards
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
                horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
            ) {
                SummaryCard("Total Resources", "${resources.size}", primaryColor)
                SummaryCard("Available", "${resources.count { it.status == "Available" }}", Color(0xFF26BB64C))
                SummaryCard("In Use", "${resources.count { it.status == "In Use" || it.status == "Fully Booked" }}", Color(0xFFE67824))
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Resources List
            CustomLabel(
                header = "All Resources",
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
                items(resources) { resource ->
                    ResourceCard(resource = resource)
                }
            }
        }
    }
}

//@Composable
//fun SummaryCard(label: String, value: String, color: Color) {
//    Card(
//        modifier = Modifier.weight(1f),
//        colors = CardDefaults.cardColors(
//            containerColor = onSurfaceVariant
//        ),
//        shape = RectangleShape
//    ) {
//        Column(
//            modifier = Modifier.padding(
//                horizontal = ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp),
//                vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
//            ),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.spacedBy(pxToDp(8))
//        ) {
//            CustomLabel(
//                header = value,
//                fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
//                headerColor = color
//            )
//            CustomLabel(
//                header = label,
//                fontSize = ResponsiveLayout.getResponsiveFontSize(10.sp, 12.sp, 14.sp),
//                headerColor = onSurfaceColor.copy(0.7f)
//            )
//        }
//    }
//}

@Composable
fun ResourceCard(resource: Resource) {
    val statusColor = when (resource.status) {
        "Available" -> Color(0xFF26BB64C)
        "In Use", "Fully Booked" -> Color(0xFFE67824)
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
                        header = resource.name,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                        headerColor = onSurfaceColor
                    )
                    CustomLabel(
                        header = resource.type,
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
                        header = resource.status,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = statusColor
                    )
                }
            }
            
            // Details
            Column(
                verticalArrangement = Arrangement.spacedBy(pxToDp(8))
            ) {
                DetailRow("Location", resource.location)
                DetailRow("Capacity", resource.capacity)
                DetailRow("Current Usage", resource.currentUsage)
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
                    buttonText = "Manage",
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// Placeholder data class
data class Resource(
    val name: String,
    val type: String,
    val capacity: String,
    val currentUsage: String,
    val status: String,
    val location: String
)

@Preview(showBackground = true)
@Composable
fun ResourceManagementScreenPreview() {
    val navController = rememberNavController()
    ResourceManagementScreen(navController = navController)
}
