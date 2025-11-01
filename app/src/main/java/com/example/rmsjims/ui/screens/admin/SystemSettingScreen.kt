package com.example.rmsjims.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.rmsjims.ui.theme.cardColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun SystemSettingScreen(
    navController: NavHostController
) {
    // Placeholder settings
    val settings = listOf(
        Setting(
            category = "General",
            items = listOf(
                SettingItem("System Name", "JIMS Resource Management", "String"),
                SettingItem("Auto-approval", "Enabled", "Boolean"),
                SettingItem("Notification", "Enabled", "Boolean"),
                SettingItem("Maintenance Mode", "Disabled", "Boolean")
            )
        ),
        Setting(
            category = "Booking",
            items = listOf(
                SettingItem("Max Booking Days", "30", "Number"),
                SettingItem("Advance Booking Days", "7", "Number"),
                SettingItem("Auto-cancel Days", "3", "Number")
            )
        ),
        Setting(
            category = "Notifications",
            items = listOf(
                SettingItem("Email Notifications", "Enabled", "Boolean"),
                SettingItem("SMS Notifications", "Disabled", "Boolean"),
                SettingItem("Push Notifications", "Enabled", "Boolean")
            )
        ),
        Setting(
            category = "Security",
            items = listOf(
                SettingItem("Session Timeout", "30 minutes", "String"),
                SettingItem("Password Policy", "Strong", "String"),
                SettingItem("Two-Factor Auth", "Disabled", "Boolean")
            )
        )
    )

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "System Settings",
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
            
            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = ResponsiveLayout.getHorizontalPadding(),
                    vertical = ResponsiveLayout.getVerticalPadding()
                ),
                verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getVerticalPadding())
            ) {
                items(settings) { setting ->
                    SettingCategoryCard(setting = setting)
                }
                
                item {
                    Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
                    
                    // Save Button
                    AppButton(
                        buttonText = "Save Changes",
                        onClick = { },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
fun SettingCategoryCard(setting: Setting) {
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
            )
        ) {
            CustomLabel(
                header = setting.category,
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                headerColor = primaryColor
            )
            
            Spacer(modifier = Modifier.height(pxToDp(12)))
            
            Column(
                verticalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                setting.items.forEachIndexed { index, item ->
                    SettingItemRow(settingItem = item)
                    if (index < setting.items.size - 1) {
                        Divider(
                            thickness = pxToDp(1),
                            color = cardColor
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SettingItemRow(settingItem: SettingItem) {
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
                header = settingItem.name,
                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                headerColor = onSurfaceColor
            )
            if (settingItem.description != null) {
                CustomLabel(
                    header = settingItem.description,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor.copy(0.6f)
                )
            }
        }
        
        when (settingItem.type) {
            "Boolean" -> {
                AppButton(
                    buttonText = settingItem.value,
                    onClick = { },
                    modifier = Modifier.padding(start = pxToDp(16))
                )
            }
            else -> {
                CustomLabel(
                    header = settingItem.value,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                    headerColor = primaryColor,
                    modifier = Modifier.padding(start = pxToDp(16))
                )
                AppButton(
                    buttonText = "Edit",
                    onClick = { },
                    modifier = Modifier.padding(start = pxToDp(8))
                )
            }
        }
    }
}

// Placeholder data classes
data class Setting(
    val category: String,
    val items: List<SettingItem>
)

data class SettingItem(
    val name: String,
    val value: String,
    val type: String,
    val description: String? = null
)

@Preview(showBackground = true)
@Composable
fun SystemSettingScreenPreview() {
    val navController = rememberNavController()
    SystemSettingScreen(navController = navController)
}
