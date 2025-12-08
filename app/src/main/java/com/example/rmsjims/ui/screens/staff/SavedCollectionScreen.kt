package com.example.rmsjims.ui.screens.staff

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rmsjims.data.local.SavedItemsManager
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Items
import com.example.rmsjims.R
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.FacilitiesViewModel
import com.example.rmsjims.viewmodel.ItemsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedCollectionScreen(
    navController: NavHostController,
    itemViewModel: ItemsViewModel = koinViewModel(),
    facilitiesViewModel: FacilitiesViewModel = koinViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val savedItemsManager = remember { SavedItemsManager(context) }
    val savedItems = remember { mutableStateMapOf<Int, Boolean>() }
    var savedEquipmentList by remember { mutableStateOf<List<Items>>(emptyList()) }

    val items = itemViewModel.itemsState
    val facilitiesState = facilitiesViewModel.facilitiesState

    // Load saved items and filter equipment
    LaunchedEffect(items) {
        val savedIds = savedItemsManager.getSavedItemIds()
        savedIds.forEach { id ->
            savedItems[id] = true
        }

        when (items) {
            is UiState.Success -> {
                savedEquipmentList = items.data.filter { savedIds.contains(it.id) }
            }
            is UiState.Error -> {
                savedEquipmentList = emptyList()
            }
            else -> {}
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Saved Collection",
                onNavigationClick = {
                    navController.popBackStack()
                },
                navController = navController
            )
        },
        containerColor = whiteColor,
        bottomBar = {
            CustomNavigationBar(
                navController = navController
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (items) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize()) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }

                is UiState.Success -> {
                    val facilities = (facilitiesState as? UiState.Success)?.data ?: emptyList()

                    if (savedEquipmentList.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(ResponsiveLayout.getHorizontalPadding()),
                            contentAlignment = Alignment.Center
                        ) {
                            CustomLabel(
                                header = "No saved equipment yet",
                                headerColor = onSurfaceColor.copy(alpha = 0.6f),
                                fontSize = 16.sp
                            )
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = ResponsiveLayout.getGridColumns(),
                            contentPadding = ResponsiveLayout.getContentPadding(),
                            verticalArrangement = ResponsiveLayout.getVerticalGridArrangement(),
                            horizontalArrangement = ResponsiveLayout.getGridArrangement(),
                        ) {
                            items(savedEquipmentList) { item ->
                                EquipmentCard(
                                    image = if (item.image_url.isNotEmpty()) item.image_url else R.drawable.temp,
                                    equipName = item.name,
                                    available = if (item.is_available == true) "Available" else "Not Available",
                                    onClick = { navController.navigate(Screen.ProductDescriptionScreen.createRoute(item.id)) },
                                    isSaved = savedItems[item.id] ?: false,
                                    saveClick = {
                                        val newSavedState = savedItemsManager.toggleItem(item.id)
                                        savedItems[item.id] = newSavedState
                                        if (!newSavedState) {
                                            savedEquipmentList = savedEquipmentList.filter { it.id != item.id }
                                        }
                                        coroutineScope.launch {
                                            snackbarHostState.showSnackbar(
                                                message = if (newSavedState) "Equipment saved to your collection" else "Equipment removed from collection",
                                                duration = androidx.compose.material3.SnackbarDuration.Short
                                            )
                                        }
                                    },
                                    facilityName = itemViewModel.getFacilityNameForEquipment(item, facilities)
                                )
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(ResponsiveLayout.getHorizontalPadding()),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CustomLabel(
                                header = "Failed to load equipment",
                                headerColor = onSurfaceColor.copy(alpha = 0.8f),
                                fontSize = 16.sp
                            )
                            CustomLabel(
                                header = items.exception.localizedMessage ?: "Please try again later",
                                headerColor = onSurfaceColor.copy(alpha = 0.6f),
                                fontSize = 14.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


