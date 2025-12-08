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
import com.example.rmsjims.data.schema.Room
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.RoomsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun RoomsScreen(
    navController: NavHostController,
    viewModel: RoomsViewModel = koinViewModel()
) {
    val roomsState by viewModel.roomsState.collectAsState()

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Rooms",
                onNavigationClick = { navController.popBackStack() },
                navController = navController
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        containerColor = whiteColor
    ) { paddingValues ->
        when (val state = roomsState) {
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
                    items(state.data) { room ->
                        RoomCard(room = room)
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
fun RoomCard(room: Room) {
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
                header = room.roomName,
                headerColor = onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 20.sp, 22.sp),
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )
            room.roomNumber?.let {
                CustomLabel(
                    header = "Room Number: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            room.location?.let {
                CustomLabel(
                    header = "Location: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            room.capacity?.let {
                CustomLabel(
                    header = "Capacity: $it people",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
            room.status?.let {
                CustomLabel(
                    header = "Status: $it",
                    headerColor = onSurfaceColor.copy(alpha = 0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            }
        }
    }
}

