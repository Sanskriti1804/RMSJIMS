package com.example.rmsjims.ui.screens.admin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import com.example.rmsjims.R
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.components.ProfileImage
import com.example.rmsjims.ui.theme.cardColor
import com.example.rmsjims.ui.theme.errorColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun UserDetailScreen(
    navController: NavHostController
) {
    val user = User(
        id = "hgkjhk",
        name = "Dr. Ravi Kumar",
        email = "ravik@edu.com",
        phone = "+91 9876543210",
        role = "Staff",
        building = "Building A",
        department = "Computer Science",
        class_ = null,
        status = UserStatus.ACTIVE,
        activeAssignmentsCount = 3,
        overdueItemsCount = 1,
        joinDate = "2023-01-15"
    )
    
    val currentlyAssignedEquipment = listOf(
        "Laptop Dell XPS 15",
        "Microscope Olympus BX53",
        "Oscilloscope Tektronix TBS1000"
    )
    
    val overdueItems = listOf(
        "Laptop Dell XPS 15 - Due: 2024-01-10"
    )
    
    val assignmentHistory = listOf(
        "Laptop Dell XPS 15 - Assigned: 2024-01-01",
        "Microscope Olympus BX53 - Assigned: 2024-01-05",
        "Oscilloscope Tektronix TBS1000 - Assigned: 2024-01-08"
    )
    
    val notes = listOf(
        "User requested additional equipment for research project",
        "Previous suspension: 2023-06-15 to 2023-07-01 - Reason: Late equipment return"
    )
    
    var showSuspendDialog by remember { mutableStateOf(false) }
    var showResetPasswordDialog by remember { mutableStateOf(false) }
    var showNotificationDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            CustomTopBar(
                title = user.name,
                onNavigationClick = { navController.popBackStack() }
            )
        },
        containerColor = whiteColor
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(
                horizontal = ResponsiveLayout.getHorizontalPadding(),
                vertical = ResponsiveLayout.getVerticalPadding()
            ),
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
        ) {
            // Basic Information Card
            item {
                BasicInfoCard(user = user)
            }
            
            // Assignment Overview Card
            item {
                AssignmentOverviewCard(
                    activeAssignments = currentlyAssignedEquipment,
                    overdueItems = overdueItems,
                    assignmentHistory = assignmentHistory
                )
            }
            
            // Admin Actions Card
            item {
                AdminActionsCard(
                    user = user,
                    onCallClick = {
                        val context = LocalContext.current
                        // Copy phone to clipboard
                        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Phone", user.phone)
                        clipboard.setPrimaryClip(clip)
                        
                        // Open dialer app with number on keypad
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:${user.phone}")
                        }
                        context.startActivity(intent)
                    },
                    onEmailClick = {
                        val context = LocalContext.current
                        // Copy email to clipboard
                        val clipboard = context.getSystemService(android.content.Context.CLIPBOARD_SERVICE) as ClipboardManager
                        val clip = ClipData.newPlainText("Email", user.email)
                        clipboard.setPrimaryClip(clip)
                        
                        // Open mail app with email in 'To' field
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:${user.email}")
                        }
                        context.startActivity(intent)
                    },
                    onNotificationClick = { showNotificationDialog = true },
                    onSuspendClick = { showSuspendDialog = true },
                    onReinstateClick = { /* Handle reinstate */ },
                    onTogglePermissionsClick = { /* Handle permissions */ },
                    onResetPasswordClick = { showResetPasswordDialog = true },
                    onSendPasswordResetLinkClick = { /* Handle send link */ }
                )
            }
            
            // Notes / Incident Log Card
            item {
                NotesIncidentCard(notes = notes)
            }
        }
    }
    
    // Confirmation Dialogs
    if (showSuspendDialog) {
        ConfirmationDialog(
            title = "Suspend User",
            message = "Are you sure you want to suspend ${user.name}?",
            confirmText = "Suspend",
            onConfirm = {
                showSuspendDialog = false
                // Handle suspend action
            },
            onDismiss = { showSuspendDialog = false }
        )
    }
    
    if (showResetPasswordDialog) {
        ConfirmationDialog(
            title = "Reset Password",
            message = "Are you sure you want to reset the password for ${user.name}?",
            confirmText = "Reset",
            onConfirm = {
                showResetPasswordDialog = false
                // Handle reset password action
            },
            onDismiss = { showResetPasswordDialog = false }
        )
    }
    
    if (showNotificationDialog) {
        NotificationDialog(
            userName = user.name,
            onSend = {
                showNotificationDialog = false
                // Handle send notification
            },
            onDismiss = { showNotificationDialog = false }
        )
    }
}

@Composable
fun BasicInfoCard(user: User) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                    vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
                ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(16))
        ) {
            CustomLabel(
                header = "Basic Information",
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                headerColor = onSurfaceColor
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(16)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(
                    size = ResponsiveLayout.getResponsiveSize(80.dp, 100.dp, 120.dp)
                )
                
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(pxToDp(8))
                ) {
                    CustomLabel(
                        header = user.name,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
                        headerColor = onSurfaceColor
                    )
                    StatusTag(status = user.status)
                }
            }
            
            Divider(thickness = pxToDp(1), color = cardColor)
            
            InfoRow(label = "Email", value = user.email)
            InfoRow(label = "Phone", value = user.phone)
            
            val locationText = when {
                user.class_ != null -> "${user.building} - ${user.department} - ${user.class_}"
                user.department.isNotEmpty() -> "${user.building} - ${user.department}"
                else -> user.building
            }
            InfoRow(label = "Location", value = locationText)
            InfoRow(label = "Role", value = user.role)
            InfoRow(label = "Join Date", value = user.joinDate)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(pxToDp(12)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        CustomLabel(
            header = label,
            modifier = Modifier.weight(0.3f),
            headerColor = onSurfaceColor.copy(0.6f),
            fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
        )
        CustomLabel(
            header = value,
            modifier = Modifier.weight(0.7f),
            headerColor = onSurfaceColor,
            fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
        )
    }
}

@Composable
fun AssignmentOverviewCard(
    activeAssignments: List<String>,
    overdueItems: List<String>,
    assignmentHistory: List<String>
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                    vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
                ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(16))
        ) {
            CustomLabel(
                header = "Assignment Overview",
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                headerColor = onSurfaceColor
            )
            
            // Stats Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                StatChip(
                    label = "Active",
                    count = activeAssignments.size,
                    color = primaryColor
                )
                if (overdueItems.isNotEmpty()) {
                    StatChip(
                        label = "Overdue",
                        count = overdueItems.size,
                        color = errorColor
                    )
                }
            }
            
            Divider(thickness = pxToDp(1), color = cardColor)
            
            // Currently Assigned Equipment
            if (activeAssignments.isNotEmpty()) {
                CustomLabel(
                    header = "Currently Assigned Equipment",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                    headerColor = onSurfaceColor.copy(0.8f)
                )
                activeAssignments.forEach { equipment ->
                    CustomLabel(
                        header = "• $equipment",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = onSurfaceColor.copy(0.7f),
                        modifier = Modifier.padding(start = pxToDp(8))
                    )
                }
            }
            
            // Overdue Items
            if (overdueItems.isNotEmpty()) {
                Spacer(modifier = Modifier.height(pxToDp(12)))
                CustomLabel(
                    header = "Overdue Items",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                    headerColor = errorColor
                )
                overdueItems.forEach { item ->
                    CustomLabel(
                        header = "• $item",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = errorColor.copy(0.8f),
                        modifier = Modifier.padding(start = pxToDp(8))
                    )
                }
            }
            
            // Assignment History Preview
            if (assignmentHistory.isNotEmpty()) {
                Spacer(modifier = Modifier.height(pxToDp(12)))
                CustomLabel(
                    header = "Assignment History (Preview)",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                    headerColor = onSurfaceColor.copy(0.8f)
                )
                assignmentHistory.take(3).forEach { history ->
                    CustomLabel(
                        header = "• $history",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = onSurfaceColor.copy(0.7f),
                        modifier = Modifier.padding(start = pxToDp(8))
                    )
                }
            }
        }
    }
}

@Composable
fun StatChip(label: String, count: Int, color: Color) {
    Box(
        modifier = Modifier
            .background(
                color = color.copy(0.2f),
                shape = RoundedCornerShape(pxToDp(12))
            )
            .padding(
                horizontal = ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp),
                vertical = ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp)
            )
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(pxToDp(4))
        ) {
            CustomLabel(
                header = label,
                fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                headerColor = onSurfaceColor.copy(0.7f)
            )
            CustomLabel(
                header = count.toString(),
                fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
                headerColor = color
            )
        }
    }
}

@Composable
fun AdminActionsCard(
    user: User,
    onCallClick: @Composable () -> Unit,
    onEmailClick: @Composable () -> Unit,
    onNotificationClick: () -> Unit,
    onSuspendClick: () -> Unit,
    onReinstateClick: () -> Unit,
    onTogglePermissionsClick: () -> Unit,
    onResetPasswordClick: () -> Unit,
    onSendPasswordResetLinkClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                    vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
                ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(12))
        ) {
            CustomLabel(
                header = "Admin Actions",
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                headerColor = onSurfaceColor
            )
            
            Divider(thickness = pxToDp(1), color = cardColor)
            
            // Communication Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                AppButton(
                    buttonText = "Call User",
                    onClick = onCallClick as () -> Unit,
                    modifier = Modifier.weight(1f)
                )
                AppButton(
                    buttonText = "Email User",
                    onClick = onEmailClick as () -> Unit,
                    modifier = Modifier.weight(1f)
                )
            }
            
            AppButton(
                buttonText = "Send Notification",
                onClick = onNotificationClick,
                modifier = Modifier.fillMaxWidth()
            )
            
            Divider(thickness = pxToDp(1), color = cardColor)
            
            // User Management Actions
            if (user.status == UserStatus.SUSPENDED) {
                AppButton(
                    buttonText = "Reinstate User",
                    onClick = onReinstateClick,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                AppButton(
                    buttonText = "Suspend User",
                    onClick = onSuspendClick,
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = errorColor
                )
            }
            
            AppButton(
                buttonText = "Toggle Permissions",
                onClick = onTogglePermissionsClick,
                modifier = Modifier.fillMaxWidth(),
                containerColor = cardColor,
                contentColor = onSurfaceColor
            )
            
            Divider(thickness = pxToDp(1), color = cardColor)
            
            // Password Actions
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                AppButton(
                    buttonText = "Reset Password",
                    onClick = onResetPasswordClick,
                    modifier = Modifier.weight(1f),
                    containerColor = cardColor,
                    contentColor = onSurfaceColor
                )
                AppButton(
                    buttonText = "Send Reset Link",
                    onClick = onSendPasswordResetLinkClick,
                    modifier = Modifier.weight(1f),
                    containerColor = cardColor,
                    contentColor = onSurfaceColor
                )
            }
        }
    }
}

@Composable
fun NotesIncidentCard(notes: List<String>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RectangleShape
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                    vertical = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)
                ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(12))
        ) {
            CustomLabel(
                header = "Notes / Incident Log",
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                headerColor = onSurfaceColor
            )
            
            Divider(thickness = pxToDp(1), color = cardColor)
            
            if (notes.isEmpty()) {
                CustomLabel(
                    header = "No notes available",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor.copy(0.6f)
                )
            } else {
                notes.forEach { note ->
                    CustomLabel(
                        header = "• $note",
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = onSurfaceColor.copy(0.7f),
                        modifier = Modifier.padding(start = pxToDp(8))
                    )
                }
            }
        }
    }
}

@Composable
fun ConfirmationDialog(
    title: String,
    message: String,
    confirmText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            CustomLabel(
                header = title,
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                headerColor = onSurfaceColor
            )
        },
        text = {
            CustomLabel(
                header = message,
                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                headerColor = onSurfaceColor.copy(0.8f)
            )
        },
        confirmButton = {
            AppButton(
                buttonText = confirmText,
                onClick = onConfirm,
                containerColor = errorColor
            )
        },
        dismissButton = {
            AppButton(
                buttonText = "Cancel",
                onClick = onDismiss,
                containerColor = cardColor,
                contentColor = onSurfaceColor
            )
        },
        containerColor = whiteColor
    )
}

@Composable
fun NotificationDialog(
    userName: String,
    onSend: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            CustomLabel(
                header = "Send Notification to $userName",
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                headerColor = onSurfaceColor
            )
        },
        text = {
            Column {
                CustomLabel(
                    header = "Message:",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                    headerColor = onSurfaceColor.copy(0.8f)
                )
                Spacer(modifier = Modifier.height(pxToDp(8)))
                // In a real app, use a TextField here
                CustomLabel(
                    header = "[Notification message input field]",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    headerColor = onSurfaceColor.copy(0.6f)
                )
            }
        },
        confirmButton = {
            AppButton(
                buttonText = "Send",
                onClick = onSend
            )
        },
        dismissButton = {
            AppButton(
                buttonText = "Cancel",
                onClick = onDismiss,
                containerColor = cardColor,
                contentColor = onSurfaceColor
            )
        },
        containerColor = whiteColor
    )
}


