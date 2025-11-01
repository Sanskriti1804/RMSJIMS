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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
fun AdminDashboardScreen(
    navController: NavHostController
) {
    // Placeholder statistics
    val dashboardStats = listOf(
        DashboardStat("Total Users", "247", primaryColor),
        DashboardStat("Active Bookings", "43", Color(0xFF26BB64C)),
        DashboardStat("Equipment", "156", Color(0xFFE67824)),
        DashboardStat("Pending Requests", "12", Color(0xFFE64646))
    )
    
    val quickActions = listOf(
        QuickAction("User Management", "Manage users and permissions"),
        QuickAction("Equipment Assignment", "Assign equipment to users"),
        QuickAction("System Settings", "Configure system preferences"),
        QuickAction("Reports", "View analytics and reports")
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Admin Dashboard",
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
            
            // Statistics Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    horizontal = ResponsiveLayout.getHorizontalPadding(),
                    vertical = ResponsiveLayout.getVerticalPadding()
                ),
                verticalArrangement = ResponsiveLayout.getVerticalGridArrangement(),
                horizontalArrangement = ResponsiveLayout.getGridArrangement()
            ) {
                items(dashboardStats) { stat ->
                    StatCard(stat = stat)
                }
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            
            // Quick Actions Section
            CustomLabel(
                header = "Quick Actions",
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                modifier = Modifier.padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
                headerColor = onSurfaceColor
            )
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp)))
            
            // Quick Actions List
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
                verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
            ) {
                quickActions.forEach { action ->
                    QuickActionCard(action = action)
                }
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
        }
    }
}

@Composable
fun StatCard(stat: DashboardStat) {
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
                vertical = ResponsiveLayout.getResponsivePadding(20.dp, 24.dp, 28.dp)
            ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(8))
        ) {
            CustomLabel(
                header = stat.value,
                fontSize = ResponsiveLayout.getResponsiveFontSize(24.sp, 28.sp, 32.sp),
                headerColor = stat.color
            )
            CustomLabel(
                header = stat.label,
                fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                headerColor = onSurfaceColor.copy(0.7f)
            )
        }
    }
}

@Composable
fun QuickActionCard(action: QuickAction) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = onSurfaceVariant
        ),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                    vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(pxToDp(4))
            ) {
                CustomLabel(
                    header = action.title,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                    headerColor = onSurfaceColor
                )
                CustomLabel(
                    header = action.description,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor.copy(0.6f)
                )
            }
            
            AppButton(
                buttonText = "Go",
                onClick = { },
                modifier = Modifier.padding(start = pxToDp(16))
            )
        }
    }
}

// Placeholder data classes
data class DashboardStat(
    val label: String,
    val value: String,
    val color: Color
)

data class QuickAction(
    val title: String,
    val description: String
)

@Preview(showBackground = true)
@Composable
fun AdminDashboardScreenPreview() {
    val navController = rememberNavController()
    AdminDashboardScreen(navController = navController)
}
