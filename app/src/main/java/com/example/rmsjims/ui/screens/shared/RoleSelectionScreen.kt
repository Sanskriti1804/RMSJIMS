package com.example.rmsjims.ui.screens.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rmsjims.R
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.UserSessionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoleSelectionScreen(
    navController: NavHostController,
    sessionViewModel: UserSessionViewModel = koinViewModel()
) {
    // Always show role selection UI - do not check saved role or auto-navigate

    val options = remember {
        listOf(
            RoleOption(
                title = "Administrator",
                description = "Manage configurations, users, and system-wide approvals.",
                role = UserRole.ADMIN
            ),
            RoleOption(
                title = "Staff",
                description = "Handle maintenance, bookings, and resource approvals.",
                role = UserRole.STAFF
            ),
            RoleOption(
                title = "Assistant",
                description = "Monitor resources, track usage, and support daily operations.",
                role = UserRole.ASSISTANT
            )
        )
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Select Role",
                onNavigationClick = null,
                navController = navController
            )
        },
        containerColor = whiteColor
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = ResponsiveLayout.getHorizontalPadding()),
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding() + 6.dp))
            CustomLabel(
                header = "Choose your access level to continue",
                headerColor = onSurfaceColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 22.sp, 24.sp)
            )
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp)))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = ResponsiveLayout.getResponsivePadding(8.dp, 12.dp, 16.dp)),
                verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp))
            ) {
                items(options) { option ->
                    RoleCard(
                        option = option,
                        onRoleSelected = { selectedRole ->
                            // Save role to session manager and navigate to Login screen
                            sessionViewModel.updateRole(selectedRole)
                            navController.navigate(Screen.LoginScreen.route) {
                                popUpTo(Screen.RoleSelectionScreen.route) { inclusive = true }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun RoleCard(
    option: RoleOption,
    onRoleSelected: (UserRole) -> Unit
) {
    // Get the appropriate icon for each role
    val iconRes = when (option.role) {
        UserRole.ADMIN -> R.drawable.ic_role_admin
        UserRole.STAFF -> R.drawable.ic_role_staff
        UserRole.ASSISTANT -> R.drawable.ic_role_assistant
        else -> R.drawable.ic_role_admin
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onRoleSelected(option.role) },
        colors = CardDefaults.cardColors(containerColor = primaryColor),
        shape = RoundedCornerShape(ResponsiveLayout.getResponsivePadding(12.dp, 14.dp, 18.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ResponsiveLayout.getResponsivePadding(24.dp, 28.dp, 32.dp),
                    vertical = ResponsiveLayout.getResponsivePadding(28.dp, 32.dp, 36.dp)
                )
        ) {
            // Icon at top-left corner
            Image(
                painter = painterResource(iconRes),
                contentDescription = "${option.title} Icon",
                modifier = Modifier
                    .size(ResponsiveLayout.getResponsiveSize(48.dp, 56.dp, 64.dp))
                    .align(Alignment.TopStart),
                colorFilter = ColorFilter.tint(whiteColor)
            )
            
            // Role name and description
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = ResponsiveLayout.getResponsiveSize(64.dp, 72.dp, 80.dp)),
                verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
            ) {
                CustomLabel(
                    header = option.title,
                    headerColor = whiteColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp)
                )
                Text(
                    text = option.description,
                    color = whiteColor,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(13.sp, 14.sp, 15.sp)
                )
            }
        }
    }
}

private data class RoleOption(
    val title: String,
    val description: String,
    val role: UserRole
)

