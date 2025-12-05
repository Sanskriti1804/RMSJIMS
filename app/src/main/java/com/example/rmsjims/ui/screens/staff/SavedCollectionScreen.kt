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
import kotlinx.serialization.json.buildJsonObject
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
                val allItems = if (items.data.isNotEmpty()) {
                    items.data
                } else {
                    getDemoEquipmentItems()
                }
                savedEquipmentList = allItems.filter { savedIds.contains(it.id) }
            }
            is UiState.Error -> {
                val demoItems = getDemoEquipmentItems()
                savedEquipmentList = demoItems.filter { savedIds.contains(it.id) }
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
                                    image = item.image_url,
                                    equipName = item.name,
                                    available = if (item.is_available == true) "Available" else "Not Available",
                                    onClick = { navController.navigate(Screen.ProductDescriptionScreen.route) },
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
                    val facilities = (facilitiesState as? UiState.Success)?.data ?: emptyList()
                    val demoItems = getDemoEquipmentItems()
                    val savedIds = savedItemsManager.getSavedItemIds()
                    val filteredItems = demoItems.filter { savedIds.contains(it.id) }

                    if (filteredItems.isEmpty()) {
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
                            items(filteredItems) { item ->
                                EquipmentCard(
                                    image = item.image_url,
                                    equipName = item.name,
                                    available = if (item.is_available == true) "Available" else "Not Available",
                                    onClick = { navController.navigate(Screen.ProductDescriptionScreen.route) },
                                    isSaved = savedItems[item.id] ?: false,
                                    saveClick = {
                                        val newSavedState = savedItemsManager.toggleItem(item.id)
                                        savedItems[item.id] = newSavedState
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
            }
        }
    }
}

// Demo/fallback equipment items - used when Supabase returns empty or on error
private fun getDemoEquipmentItems(): List<Items> {
    return listOf(
            Items(
                id = 1,
                facility_id = 1,
                parent_categoy_id = 1,
                category_id = 1,
                name = "Canon EOS R50 V",
                specification = buildJsonObject { },
                description = "Professional camera for photography studio",
                image_url = "https://via.placeholder.com/300?text=Canon+EOS+R50",
                usage_instructions = "Handle with care",
                is_available = true,
                createdAt = "2024-01-01T00:00:00Z"
            ),
            Items(
                id = 2,
                facility_id = 2,
                parent_categoy_id = 1,
                category_id = 1,
                name = "Sony Alpha A7III",
                specification = buildJsonObject { },
                description = "High-resolution mirrorless camera",
                image_url = "https://via.placeholder.com/300?text=Sony+Alpha+A7III",
                usage_instructions = "Check battery before use",
                is_available = true,
                createdAt = "2024-01-02T00:00:00Z"
            ),
            Items(
                id = 3,
                facility_id = 1,
                parent_categoy_id = 1,
                category_id = 1,
                name = "Nikon D850",
                specification = buildJsonObject { },
                description = "DSLR camera with advanced features",
                image_url = "https://via.placeholder.com/300?text=Nikon+D850",
                usage_instructions = "Use provided lens cap",
                is_available = false,
                createdAt = "2024-01-03T00:00:00Z"
            ),
            Items(
                id = 4,
                facility_id = 1,
                parent_categoy_id = 1,
                category_id = 1,
                name = "Canon EOS 5D Mark IV",
                specification = buildJsonObject { },
                description = "Professional full-frame camera",
                image_url = "https://via.placeholder.com/300?text=Canon+5D+Mark+IV",
                usage_instructions = "Review manual before operation",
                is_available = true,
                createdAt = "2024-01-04T00:00:00Z"
            ),
            Items(
                id = 5,
                facility_id = 3,
                parent_categoy_id = 1,
                category_id = 1,
                name = "Fujifilm X-T4",
                specification = buildJsonObject { },
                description = "Compact mirrorless camera system",
                image_url = "https://via.placeholder.com/300?text=Fujifilm+X-T4",
                usage_instructions = "Ensure memory card is inserted",
                is_available = true,
                createdAt = "2024-01-05T00:00:00Z"
            ),
            Items(
                id = 6,
                facility_id = 2,
                parent_categoy_id = 1,
                category_id = 1,
                name = "Panasonic Lumix GH6",
                specification = buildJsonObject { },
                description = "Video-focused mirrorless camera",
                image_url = "https://via.placeholder.com/300?text=Panasonic+GH6",
                usage_instructions = "Check SD card compatibility",
                is_available = false,
                createdAt = "2024-01-06T00:00:00Z"
            )
        )
    }

