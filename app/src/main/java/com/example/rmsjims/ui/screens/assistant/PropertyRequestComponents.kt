package com.example.rmsjims.ui.screens.assistant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import com.example.rmsjims.data.model.PropertyRequest
import com.example.rmsjims.data.model.RequestPriority
import com.example.rmsjims.data.model.RequestStatus
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomSmallLabel
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun PropertyRequestsContent(
    requests: List<PropertyRequest>,
    showPriorityTag: Boolean,
    showQuickToggle: Boolean,
    showVerificationAction: Boolean,
    navController: NavHostController
) {
    if (requests.isEmpty()) {
        PropertyEmptyState()
        return
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
    ) {
        requests.forEach { request ->
            PropertyRequestCard(
                request = request,
                showPriorityTag = showPriorityTag,
                showQuickToggle = showQuickToggle,
                showVerificationAction = showVerificationAction,
                navController = navController
            )
        }
    }
}

@Composable
private fun PropertyRequestCard(
    request: PropertyRequest,
    showPriorityTag: Boolean,
    showQuickToggle: Boolean,
    showVerificationAction: Boolean,
    navController: NavHostController
) {
    var quickToggle by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = { navController.navigate(Screen.RequestDetailsScreen.createRoute(request.id)) },
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RoundedCornerShape(pxToDp(12))
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = RoundedCornerShape(pxToDp(12)),
                    colors = CardDefaults.cardColors(containerColor = whiteColor),
                    elevation = CardDefaults.cardElevation(defaultElevation = pxToDp(2))
                ) {
                    Image(
                        painter = painterResource(id = request.imageRes),
                        contentDescription = request.machineName,
                        modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(64.dp, 72.dp, 84.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(pxToDp(6))
                ) {
                    CustomLabel(
                        header = request.machineName,
                        headerColor = onSurfaceColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp)
                    )
                    CustomSmallLabel(
                        header = request.department,
                        headerColor = onSurfaceColor.copy(alpha = 0.6f),
                        modifier = Modifier
                    )
                    CustomSmallLabel(
                        header = "Requested Slot: ${request.requestedSlot}",
                        headerColor = primaryColor,
                        modifier = Modifier
                    )
                }
                if (showPriorityTag) {
                    PriorityBadge(priority = request.priority)
                } else {
                    StatusBadge(status = request.status)
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(pxToDp(6))
            ) {
                PropertyDetailRow(
                    label = "Requested By",
                    value = "${request.requestedBy} • ${request.requestedDepartment}"
                )
                PropertyDetailRow(
                    label = "Requested Date",
                    value = request.requestedDate
                )
                PropertyDetailRow(
                    label = "Priority",
                    value = request.priority.name.replace('_', ' ')
                )
                if (request.description.isNotEmpty()) {
                    CustomSmallLabel(
                        header = "Description",
                        headerColor = onSurfaceColor.copy(alpha = 0.7f),
                        modifier = Modifier
                    )
                    CustomLabel(
                        header = request.description,
                        headerColor = onSurfaceColor,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 13.sp, 14.sp),
                        maxLine = 3
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                AppButton(
                    buttonText = "Approve",
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )
                AppButton(
                    buttonText = "Reject",
                    onClick = { },
                    modifier = Modifier.weight(1f),
                    containerColor = Color(0xFFE64646),
                    contentColor = whiteColor
                )
                AppButton(
                    buttonText = "View Details",
                    onClick = {
                        navController.navigate(Screen.RequestDetailsScreen.createRoute(request.id))
                    },
                    modifier = Modifier.weight(1f),
                    containerColor = onSurfaceVariant,
                    contentColor = onSurfaceColor
                )
            }

            if (showVerificationAction) {
                AppButton(
                    buttonText = "Request Verification / Extra Info",
                    onClick = { },
                    modifier = Modifier.fillMaxWidth(),
                    containerColor = onSurfaceVariant,
                    contentColor = primaryColor
                )
            }

            if (showQuickToggle) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomSmallLabel(
                        header = "Quick Toggle",
                        headerColor = onSurfaceColor,
                        modifier = Modifier
                    )
                    Switch(
                        checked = quickToggle,
                        onCheckedChange = { quickToggle = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = primaryColor,
                            checkedTrackColor = primaryColor.copy(alpha = 0.4f),
                            uncheckedThumbColor = onSurfaceColor.copy(alpha = 0.3f),
                            uncheckedTrackColor = onSurfaceColor.copy(alpha = 0.1f)
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun PropertyEmptyState() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RoundedCornerShape(pxToDp(12))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = ResponsiveLayout.getResponsivePadding(18.dp, 22.dp, 26.dp),
                    vertical = ResponsiveLayout.getResponsivePadding(20.dp, 24.dp, 28.dp)
                ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(6)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CustomLabel(
                header = "No requests yet",
                headerColor = onSurfaceColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp)
            )
            CustomSmallLabel(
                header = "New requests will appear here once submitted.",
                headerColor = onSurfaceColor.copy(alpha = 0.6f),
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun PropertyDetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomSmallLabel(
            header = label,
            headerColor = onSurfaceColor.copy(alpha = 0.7f),
            modifier = Modifier
        )
        CustomLabel(
            header = value,
            headerColor = onSurfaceColor,
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 13.sp, 14.sp),
            maxLine = 2
        )
    }
}

@Composable
private fun PriorityBadge(priority: RequestPriority) {
    val colors = when (priority) {
        RequestPriority.HIGH -> Color(0xFFE67824) to Color(0xFFE67824).copy(alpha = 0.2f)
        RequestPriority.MEDIUM -> Color(0xFF026AA2) to Color(0xFF026AA2).copy(alpha = 0.2f)
        RequestPriority.LOW -> Color(0xFF26BB64) to Color(0xFF26BB64).copy(alpha = 0.2f)
        RequestPriority.CRITICAL -> Color(0xFFE64646) to Color(0xFFE64646).copy(alpha = 0.2f)
    }
    Surface(
        color = colors.second,
        shape = RoundedCornerShape(pxToDp(10))
    ) {
        Text(
            text = priority.name.replace('_', ' '),
            color = colors.first,
            modifier = Modifier.padding(
                horizontal = ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 14.dp),
                vertical = ResponsiveLayout.getResponsivePadding(4.dp, 6.dp, 8.dp)
            ),
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 13.sp, 14.sp)
        )
    }
}

@Composable
private fun StatusBadge(status: RequestStatus) {
    Surface(
        color = onSurfaceColor.copy(alpha = 0.08f),
        shape = RoundedCornerShape(pxToDp(10))
    ) {
        Text(
            text = status.name.replace('_', ' '),
            color = onSurfaceColor,
            modifier = Modifier.padding(
                horizontal = ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 14.dp),
                vertical = ResponsiveLayout.getResponsivePadding(4.dp, 6.dp, 8.dp)
            ),
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 13.sp, 14.sp)
        )
    }
}
