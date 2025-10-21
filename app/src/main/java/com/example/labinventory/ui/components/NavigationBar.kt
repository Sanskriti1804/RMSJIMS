package com.example.labinventory.ui.components

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
import com.example.labinventory.navigation.bottomNavItems
import com.example.labinventory.ui.theme.primaryColor
import com.example.labinventory.ui.theme.navLabelColor
import com.example.labinventory.ui.theme.someOtherGrayColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.ResponsiveLayout

@Composable
fun CustomNavigationBar(
    bottomBarColor: Color = whiteColor,
    badgeColor: Color = primaryColor,
    selectedColor: Color = primaryColor,
    contentColor: Color = navLabelColor,
    dividerColor: Color = someOtherGrayColor,
    navController: NavHostController
) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

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
                val isSelected = currentRoute == item.route
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
