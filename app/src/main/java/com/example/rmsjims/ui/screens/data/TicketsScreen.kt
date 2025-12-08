package com.example.rmsjims.ui.screens.data

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Ticket
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.TicketsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun TicketsScreen(
    navController: NavHostController,
    viewModel: TicketsViewModel = koinViewModel()
) {
    val ticketsState by viewModel.ticketsState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Tickets",
                onNavigationClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        containerColor = whiteColor
    ) { paddingValues ->
        when (val state = ticketsState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = primaryColor)
                }
            }
            is UiState.Success -> {
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
                    items(state.data) { ticket ->
                        TicketCard(ticket = ticket)
                    }
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CustomLabel(
                        header = "Error: ${state.exception.message}",
                        headerColor = Color.Red,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                    )
                }
            }
        }
    }
}

@Composable
fun TicketCard(ticket: Ticket) {
    val statusColor = when (ticket.status?.lowercase()) {
        "pending" -> Color(0xFFE67824)
        "active" -> Color(0xFF024CA1)
        "closed" -> Color(0xFF26BB64)
        else -> onSurfaceColor
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ResponsiveLayout.getResponsiveSize(2.dp, 3.dp, 4.dp)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
        ) {
            CustomLabel(
                header = ticket.name,
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
            ticket.description?.let {
                CustomLabel(
                    header = it,
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
            ) {
                ticket.status?.let {
                    CustomLabel(
                        header = "Status: $it",
                        headerColor = statusColor,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                    )
                }
                ticket.urgency?.let {
                    CustomLabel(
                        header = "Urgency: $it",
                        headerColor = onSurfaceColor.copy(alpha = 0.7f),
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                    )
                }
            }
            ticket.requesterName?.let {
                CustomLabel(
                    header = "Requester: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            ticket.assignedTo?.let {
                CustomLabel(
                    header = "Assigned to: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
        }
    }
}

