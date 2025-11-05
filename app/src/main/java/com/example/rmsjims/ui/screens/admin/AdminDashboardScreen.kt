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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppNavIcon
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp
import com.example.rmsjims.R
import com.example.rmsjims.navigation.Screen

@Composable
fun AdminDashboardScreen(
    navController: NavHostController
) {
    // Stats carousel data (horizontal)
    val resourceStats = listOf(
        StatItem(name = "Total Resources", value = "156", color = primaryColor, iconRes = R.drawable.ic_storage),
        StatItem(name = "Available", value = "94", color = Color(0xFF26BB64), iconRes = R.drawable.ic_vector),
        StatItem(name = "In Use", value = "48", color = Color(0xFFE67824), iconRes = R.drawable.ic_tripod),
        StatItem(name = "Maintenance", value = "6", color = Color(0xFFE64646), iconRes = R.drawable.ic_assigned_time),
        StatItem(name = "Pending", value = "12", color = Color(0xFF024CA1), iconRes = R.drawable.ic_ticket_thread)
    )

    // Alerts panel data
    val alerts = listOf(
        AlertItem(type = AlertType.CRITICAL, title = "Cooling failure in Lab A-101", message = "HPC server temperature exceeded safe threshold. Immediate action required."),
        AlertItem(type = AlertType.WARNING, title = "Consumables low for 3D Printer", message = "Filament levels below 20%. Refill recommended this week."),
        AlertItem(type = AlertType.INFO, title = "New policy update", message = "Booking window extended to 10 days for faculty users.")
    )

    // Feature grid items
    val features = listOf(
        FeatureItem("View Requests", R.drawable.ic_ticket_thread, Screen.TicketManagementScreen.route),
        FeatureItem("Verify Resource", R.drawable.ic_edit, Screen.UsageApprovalScreen.route),
        FeatureItem("Update Status", R.drawable.ic_assigned_time, Screen.MachineStatusScreen.route),
        FeatureItem("Maintenance Log", R.drawable.ic_edit, Screen.MaintenanceApprovalScreen.route),
        FeatureItem("View Reports", R.drawable.ic_vector, Screen.ResourceManagementScreen.route),
        FeatureItem("Messages", R.drawable.ic_chat, Screen.TicketManagementScreen.route)
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
            
            // Horizontal Stats Carousel
            StatsCarousel(items = resourceStats)
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))

            // System Alerts
            CustomLabel(
                header = "System Alerts",
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                modifier = Modifier.padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
                headerColor = onSurfaceColor
            )
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp)))
            AlertsPanel(items = alerts)
            
            // Quick Actions Section
            CustomLabel(
                header = "Quick Actions",
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                modifier = Modifier.padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
                headerColor = onSurfaceColor
            )
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp)))
            
            // Feature Grid (2 columns)
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(
                    horizontal = ResponsiveLayout.getHorizontalPadding(),
                    vertical = ResponsiveLayout.getVerticalPadding()
                ),
                verticalArrangement = ResponsiveLayout.getVerticalGridArrangement(),
                horizontalArrangement = ResponsiveLayout.getGridArrangement()
            ) {
                items(features) { feature ->
                    FeatureCard(feature = feature) { navController.navigate(feature.route) }
                }
            }
            
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
        }
    }
}

@Composable
fun StatsCarousel(items: List<StatItem>) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = ResponsiveLayout.getHorizontalPadding()),
        horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
    ) {
        items(items) { stat ->
            Card(
                modifier = Modifier,
                colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
                shape = RectangleShape,
                elevation = CardDefaults.cardElevation(defaultElevation = pxToDp(2))
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                        vertical = ResponsiveLayout.getResponsivePadding(14.dp, 18.dp, 22.dp)
                    ),
                    horizontalArrangement = Arrangement.spacedBy(pxToDp(12)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Circular colored icon
                    Surface(
                        color = stat.color.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(pxToDp(24))
                    ) {
                        Box(modifier = Modifier.padding(pxToDp(10))) {
                            AppNavIcon(
                                painter = painterResource(id = stat.iconRes),
                                iconDescription = stat.name,
                                tint = stat.color
                            )
                        }
                    }
                    Column(verticalArrangement = Arrangement.spacedBy(pxToDp(4))) {
                        CustomLabel(
                            header = stat.name,
                            headerColor = onSurfaceColor
                        )
                        CustomLabel(
                            header = stat.value,
                            fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
                            headerColor = stat.color
                        )
                    }
                }
            }
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

@Composable
fun AlertsPanel(items: List<AlertItem>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
    ) {
        items.forEach { alert ->
            val (expanded, setExpanded) = remember { mutableStateOf(false) }
            val (bgColor, tagColor) = when (alert.type) {
                AlertType.CRITICAL -> Color(0xFFFFE5E5) to Color(0xFFE64646)
                AlertType.WARNING -> Color(0xFFFFF0E1) to Color(0xFFE67824)
                AlertType.INFO -> Color(0xFFE8F0FF) to primaryColor
            }
            Card(
                colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
                shape = RectangleShape,
                onClick = { setExpanded(!expanded) },
                elevation = CardDefaults.cardElevation(defaultElevation = pxToDp(1))
            ) {
                Column(
                    modifier = Modifier.padding(
                        horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                        vertical = ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp)
                    ),
                    verticalArrangement = Arrangement.spacedBy(pxToDp(8))
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(pxToDp(10)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(color = tagColor, shape = RoundedCornerShape(pxToDp(8))) {
                            Box(modifier = Modifier.padding(horizontal = pxToDp(8), vertical = pxToDp(2))) {
                                CustomLabel(
                                    header = when (alert.type) {
                                        AlertType.CRITICAL -> "Critical"
                                        AlertType.WARNING -> "Warning"
                                        AlertType.INFO -> "Info"
                                    },
                                    headerColor = whiteColor
                                )
                            }
                        }
                        CustomLabel(header = alert.title, headerColor = onSurfaceColor)
                    }
                    if (expanded) {
                        CustomLabel(
                            header = alert.message,
                            headerColor = onSurfaceColor.copy(0.8f)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun FeatureCard(feature: FeatureItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RectangleShape,
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = pxToDp(1))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                    vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
                ),
            horizontalArrangement = Arrangement.spacedBy(pxToDp(12)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(color = primaryColor.copy(0.1f), shape = RoundedCornerShape(pxToDp(12))) {
                Box(modifier = Modifier.padding(pxToDp(10))) {
                    AppNavIcon(
                        painter = painterResource(id = feature.iconRes),
                        iconDescription = feature.name,
                        tint = primaryColor
                    )
                }
            }
            CustomLabel(
                header = feature.name,
                headerColor = onSurfaceColor
            )
        }
    }
}

// Placeholder data classes
data class StatItem(
    val name: String,
    val value: String,
    val color: Color,
    val iconRes: Int
)

data class QuickAction(
    val title: String,
    val description: String
)

data class AlertItem(
    val type: AlertType,
    val title: String,
    val message: String
)

enum class AlertType { CRITICAL, WARNING, INFO }

data class FeatureItem(
    val name: String,
    val iconRes: Int,
    val route: String
)

@Preview(showBackground = true)
@Composable
fun AdminDashboardScreenPreview() {
    val navController = rememberNavController()
    AdminDashboardScreen(navController = navController)
}
