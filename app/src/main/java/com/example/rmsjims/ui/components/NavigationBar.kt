package com.example.rmsjims.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.navigation.getBottomNavItems
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.navLabelColor
import com.example.rmsjims.ui.theme.someOtherGrayColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.UserSessionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun CustomNavigationBar(
    bottomBarColor: Color = whiteColor,
    badgeColor: Color = primaryColor,
    selectedColor: Color = primaryColor,
    contentColor: Color = navLabelColor,
    dividerColor: Color = someOtherGrayColor,
    navController: NavHostController,
    userRole: UserRole? = null,
    sessionViewModel: UserSessionViewModel = koinViewModel()
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route
    
    // Determine user role - use provided role, then ViewModel, then detect from route
    val effectiveUserRole = userRole ?: run {
        // First try ViewModel role (should be set when navigating from RoleSelectionScreen)
        val vmRole = sessionViewModel.userRole
        if (vmRole != UserRole.UNASSIGNED) {
            vmRole
        } else {
            // Fallback: Detect role from current route
            val routeString = currentRoute ?: ""
            when {
                routeString == Screen.AdminDashboardScreen.route -> UserRole.ADMIN
                routeString == Screen.BookingsScreen.route -> UserRole.STAFF
                routeString == Screen.AssistantScreen.route -> UserRole.ASSISTANT
                else -> UserRole.UNASSIGNED
            }
        }
    }
    val bottomNavItems = getBottomNavItems(effectiveUserRole)

    Column(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(ResponsiveLayout.getResponsivePadding(0.5.dp, 1.dp, 1.5.dp))
                .background(dividerColor)
        )
        
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(ResponsiveLayout.getResponsiveSize(101.dp, 120.dp, 140.dp))
                .padding(horizontal = 0.dp)
                .background(bottomBarColor),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            bottomNavItems.forEach { item ->
                // Handle route matching for routes with parameters (e.g., equipment/{categoryName})
                val isSelected = when {
                    item.route.startsWith("equipment/") -> currentRoute?.startsWith("equipment/") == true
                    else -> currentRoute == item.route
                }
                Column(
                    modifier = Modifier
                        .clickable{
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                        .padding(
                            top = ResponsiveLayout.getResponsivePadding(23.dp, 28.dp, 32.dp),
                            bottom = ResponsiveLayout.getResponsivePadding(29.dp, 34.dp, 38.dp),
                            start = ResponsiveLayout.getResponsivePadding(10.dp, 15.dp, 20.dp),
                            end = ResponsiveLayout.getResponsivePadding(10.dp, 15.dp, 20.dp)
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(modifier = Modifier) {
                        AppNavIcon(
                            painter = painterResource(id = item.iconResId),
                            iconSize = ResponsiveLayout.getResponsiveSize(24.dp, 28.dp, 32.dp),
                            tint = if (isSelected) selectedColor else contentColor
                        )

                        if (item.badgeCount != null || item.hasNews) {
                            Box(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .offset(
                                        x = ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 14.dp), 
                                        y = ResponsiveLayout.getResponsivePadding(-6.dp, -7.dp, -8.dp)
                                    )
                                    .background(badgeColor, shape = CircleShape)
                                    .padding(
                                        horizontal = ResponsiveLayout.getResponsivePadding(4.dp, 6.dp, 8.dp),
                                        vertical = ResponsiveLayout.getResponsivePadding(2.dp, 3.dp, 4.dp)
                                    )
                            ) {
                                Text(
                                    text = item.badgeCount?.toString() ?: "",
                                    fontSize = ResponsiveLayout.getResponsiveFontSize(10.sp, 12.sp, 14.sp),
                                    color = whiteColor
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(4.dp, 6.dp, 8.dp)))

                    Text(
                        text = item.label,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(10.sp, 12.sp, 14.sp),
                        color = if (isSelected) selectedColor else contentColor
                    )
                }
            }
        }
    }
}
