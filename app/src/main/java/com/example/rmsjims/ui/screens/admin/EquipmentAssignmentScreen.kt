package com.example.rmsjims.ui.screens.admin

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import com.example.rmsjims.R
import com.example.rmsjims.data.model.BookingTab
import com.example.rmsjims.data.model.TabItem
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.components.ProfileImage
import com.example.rmsjims.ui.screens.staff.TabSelector
import com.example.rmsjims.ui.theme.cardColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun EquipmentAssignmentScreen(
    navController: NavHostController
) {
    // Placeholder data
    val assignedUsers = listOf(
        AssignedUser(
            name = "Dr. Ravi Kumar",
            email = "ravik@edu.com",
            department = "Computer Science",
            equipment = "High-Performance Server",
            equipmentId = "EQ-2024-001",
            assignedDate = "2024-01-15"
        ),
        AssignedUser(
            name = "Prof. Meera Sharma",
            email = "meeras@edu.com",
            department = "Electronics",
            equipment = "Oscilloscope Pro",
            equipmentId = "EQ-2024-045",
            assignedDate = "2024-01-10"
        ),
        AssignedUser(
            name = "Dr. Amit Patel",
            email = "amitp@edu.com",
            department = "Mechanical",
            equipment = "3D Printer XL",
            equipmentId = "EQ-2024-089",
            assignedDate = "2024-01-05"
        ),
        AssignedUser(
            name = "Prof. Sunita Reddy",
            email = "sunitar@edu.com",
            department = "Physics",
            equipment = "Spectrometer",
            equipmentId = "EQ-2024-123",
            assignedDate = "2024-01-20"
        )
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Equipment Assignment",
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

            TabSelector(
                tabs = listOf(
                    TabItem(BookingTab.Booking_Requests, "Booking Requests", R.drawable.ic_booking_pending, isSelected = true),
                    TabItem(BookingTab.Verified_Bookings, "Approved Bookings", R.drawable.ic_booking_verified, isSelected = false),
                    TabItem(BookingTab.Canceled_Bookings, "Rejected Bookings", R.drawable.ic_booking_canceled, isSelected = false),
                ),
                onTabSelected = { selectedTab -> }
            )
            Column(
                    modifier = Modifier.padding(
                        horizontal = ResponsiveLayout.getHorizontalPadding(),
                        vertical = ResponsiveLayout.getVerticalPadding()
                    )
                ) {
                    CustomLabel(
                        header = "Total Assignments",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                        headerColor = onSurfaceColor.copy(0.7f)
                    )
                    Spacer(modifier = Modifier.height(pxToDp(8)))
                    CustomLabel(
                        header = "${assignedUsers.size} Active",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
                        headerColor = primaryColor
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Assigned Users List
            CustomLabel(
                header = "Assigned Equipment",
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
                items(assignedUsers) { user ->
                    AssignedUserCard(user = user)
                }
            }
    }
}

@Composable
fun AssignedUserCard(user: AssignedUser) {
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
            // User Info Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(16)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(
                    size = ResponsiveLayout.getResponsiveSize(50.dp, 60.dp, 70.dp)
                )
                
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(pxToDp(4))
                ) {
                    CustomLabel(
                        header = user.name,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                        headerColor = onSurfaceColor
                    )
                    CustomLabel(
                        header = user.email,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = onSurfaceColor.copy(0.6f)
                    )
                    CustomLabel(
                        header = user.department,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = primaryColor
                    )
                }
            }
            
            Divider(
                thickness = pxToDp(1),
                color = cardColor
            )
            
            // Equipment Info
            Column(
                verticalArrangement = Arrangement.spacedBy(pxToDp(8))
            ) {
                CustomLabel(
                    header = "Equipment",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor.copy(0.7f)
                )
                CustomLabel(
                    header = user.equipment,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                    headerColor = onSurfaceColor
                )
                CustomLabel(
                    header = "ID: ${user.equipmentId}",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor.copy(0.6f)
                )
                CustomLabel(
                    header = "Assigned: ${user.assignedDate}",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor.copy(0.6f)
                )
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
                    buttonText = "Unassign",
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    containerColor = cardColor,
                    contentColor = onSurfaceColor
                )
            }
        }
    }
}

// Placeholder data class
data class AssignedUser(
    val name: String,
    val email: String,
    val department: String,
    val equipment: String,
    val equipmentId: String,
    val assignedDate: String
)

@Preview(showBackground = true)
@Composable
fun EquipmentAssignmentScreenPreview() {
    val navController = rememberNavController()
    EquipmentAssignmentScreen(navController = navController)
}
