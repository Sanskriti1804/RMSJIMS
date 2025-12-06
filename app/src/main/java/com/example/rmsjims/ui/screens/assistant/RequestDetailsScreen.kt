package com.example.rmsjims.ui.screens.assistant

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.data.model.PropertyRequest
import com.example.rmsjims.data.model.PropertyRequestProvider
import com.example.rmsjims.data.model.RequestPriority
import com.example.rmsjims.data.model.RequestStatus
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomSmallLabel
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun RequestDetailsScreen(
    requestId: String,
    navController: NavHostController
) {
    // In real app, fetch request by ID from ViewModel/Repository
    // For now, use sample data
    val allRequests = PropertyRequestProvider.sampleRequests()
    val request = (allRequests.pendingApprovals + allRequests.newRequests + 
                   allRequests.priorityRequests + allRequests.flaggedRequests)
        .firstOrNull { it.id == requestId }
        ?: PropertyRequestProvider.sampleRequests().pendingApprovals.first()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Request Details",
                onNavigationClick = {
                    navController.popBackStack()
                },
                navController = navController
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
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp))
        ) {
            item {
                Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            }

            // Image and Title Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
                    shape = RoundedCornerShape(pxToDp(12))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
                        horizontalArrangement = Arrangement.spacedBy(pxToDp(16)),
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
                                modifier = Modifier.size(ResponsiveLayout.getResponsiveSize(80.dp, 96.dp, 112.dp)),
                                contentScale = ContentScale.Crop
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(pxToDp(8))
                        ) {
                            CustomLabel(
                                header = request.machineName,
                                headerColor = onSurfaceColor,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp)
                            )
                            CustomSmallLabel(
                                header = request.department,
                                headerColor = onSurfaceColor.copy(alpha = 0.6f),
                                modifier = Modifier
                            )
                            PriorityBadge(priority = request.priority)
                        }
                    }
                }
            }

            // Request Information Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
                    shape = RoundedCornerShape(pxToDp(12))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
                        verticalArrangement = Arrangement.spacedBy(pxToDp(12))
                    ) {
                        CustomLabel(
                            header = "Request Information",
                            headerColor = onSurfaceColor,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp)
                        )
                        Divider(color = onSurfaceColor.copy(alpha = 0.2f), thickness = pxToDp(1))
                        
                        PropertyDetailRow(label = "Request ID", value = request.id)
                        PropertyDetailRow(label = "Requested Slot", value = request.requestedSlot)
                        PropertyDetailRow(label = "Requested By", value = "${request.requestedBy} • ${request.requestedDepartment}")
                        PropertyDetailRow(label = "Requested Date", value = request.requestedDate)
                        PropertyDetailRow(label = "Priority", value = request.priority.name.replace('_', ' '))
                        PropertyDetailRow(label = "Status", value = request.status.name.replace('_', ' '))
                        
                        if (request.description.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(pxToDp(8)))
                            CustomSmallLabel(
                                header = "Description",
                                headerColor = onSurfaceColor.copy(alpha = 0.7f),
                                modifier = Modifier
                            )
                            CustomLabel(
                                header = request.description,
                                headerColor = onSurfaceColor,
                                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                                maxLine = 10
                            )
                        }
                    }
                }
            }

            // Comments Section
            if (request.comments.isNotEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
                        shape = RoundedCornerShape(pxToDp(12))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
                            verticalArrangement = Arrangement.spacedBy(pxToDp(12))
                        ) {
                            CustomLabel(
                                header = "Comments (${request.comments.size})",
                                headerColor = onSurfaceColor,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp)
                            )
                            Divider(color = onSurfaceColor.copy(alpha = 0.2f), thickness = pxToDp(1))
                        }
                    }
                }

                items(request.comments) { comment ->
                    CommentCard(comment = comment)
                }
            }

            // Action Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
                ) {
                    AppButton(
                        buttonText = "Approve",
                        onClick = { 
                            // Handle approval
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f)
                    )
                    AppButton(
                        buttonText = "Reject",
                        onClick = { 
                            // Handle rejection
                            navController.popBackStack()
                        },
                        modifier = Modifier.weight(1f),
                        containerColor = Color(0xFFE64646),
                        contentColor = whiteColor
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(ResponsiveLayout.getVerticalPadding()))
            }
        }
    }
}

@Composable
private fun CommentCard(comment: com.example.rmsjims.data.model.RequestComment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = whiteColor),
        shape = RoundedCornerShape(pxToDp(12))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
            verticalArrangement = Arrangement.spacedBy(pxToDp(8))
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    CustomLabel(
                        header = comment.author,
                        headerColor = onSurfaceColor,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                    )
                    CustomSmallLabel(
                        header = "${comment.role} • ${comment.timestamp}",
                        headerColor = onSurfaceColor.copy(alpha = 0.6f),
                        modifier = Modifier
                    )
                }
            }
            CustomLabel(
                header = comment.message,
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(13.sp, 14.sp, 15.sp),
                maxLine = 10
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
            fontSize = ResponsiveLayout.getResponsiveFontSize(13.sp, 14.sp, 15.sp),
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
    androidx.compose.material3.Surface(
        color = colors.second,
        shape = RoundedCornerShape(pxToDp(10))
    ) {
        androidx.compose.material3.Text(
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

