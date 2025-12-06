package com.example.rmsjims.ui.screens.staff

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.size
import com.example.rmsjims.data.model.PropertyRequest
import com.example.rmsjims.data.model.PropertyRequestGroup
import com.example.rmsjims.data.model.PropertyRequestProvider
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.data.model.BookingGroup
import com.example.rmsjims.data.model.BookingGroupProvider
import com.example.rmsjims.data.model.EquipmentGroup
import com.example.rmsjims.data.model.EquipmentGroupProvider
import com.example.rmsjims.data.model.TicketGroupData
import com.example.rmsjims.data.model.TicketGroupDataProvider
import com.example.rmsjims.data.model.BookingItem
import com.example.rmsjims.data.model.TicketItem
import com.example.rmsjims.data.model.BookingPriority
import com.example.rmsjims.data.model.BookingStatus
import com.example.rmsjims.data.model.BookingDates
import com.example.rmsjims.data.model.ProductInfo
import com.example.rmsjims.data.model.Status
import com.example.rmsjims.data.model.InChargeInfo
import com.example.rmsjims.data.model.TicketPriority
import com.example.rmsjims.data.model.TicketStatus
import com.example.rmsjims.data.model.TicketCategory
import com.example.rmsjims.data.schema.Items
import coil.compose.AsyncImage
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import com.example.rmsjims.R
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppCircularIcon
import com.example.rmsjims.ui.components.AppFAB
import com.example.rmsjims.ui.components.AppSearchBar
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomSmallLabel
import com.example.rmsjims.ui.screens.assistant.PropertyRequestsContent
import com.example.rmsjims.ui.theme.categoryIconColor
import com.example.rmsjims.ui.theme.chipColor
import com.example.rmsjims.ui.theme.labelColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.titleColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp
import com.example.rmsjims.viewmodel.FilterSortViewModel
import com.example.rmsjims.viewmodel.SearchViewModel
import com.example.rmsjims.viewmodel.UserSessionViewModel
import com.example.rmsjims.viewmodel.ItemsViewModel
import com.example.rmsjims.viewmodel.BookingScreenViewmodel
import com.example.rmsjims.data.model.UiState
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    filterSortViewModel: FilterSortViewModel = koinViewModel(),
    sessionViewModel: UserSessionViewModel = koinViewModel(),
    searchViewModel: SearchViewModel = koinViewModel(),
    itemsViewModel: ItemsViewModel = koinViewModel(),
    bookingViewModel: BookingScreenViewmodel = koinViewModel()
) {
    val isFilterSheetVisible by filterSortViewModel.isSheetVisible
    val isAiChatSheetVisible by searchViewModel.isChatSheetVisible
    val userRole = sessionViewModel.userRole
    
    // Get real equipment data from ViewModel
    val itemsState = itemsViewModel.itemsState
    val realEquipmentItems = when (itemsState) {
        is UiState.Success -> itemsState.data
        else -> emptyList()
    }
    
    // Get recently added equipment (last 4 items sorted by createdAt)
    val recentlyAddedEquipment = remember(realEquipmentItems) {
        realEquipmentItems.sortedByDescending { it.createdAt }.take(4)
    }

    val resources = remember {
        listOf(
            Resource(
                name = "Lab Space A-101",
                type = "Lab Space",
                capacity = "20 students",
                currentUsage = "12 students",
                status = "Available",
                location = "Building A, Floor 1"
            ),
            Resource(
                name = "Conference Room B",
                type = "Meeting Room",
                capacity = "15 people",
                currentUsage = "15 people",
                status = "Fully Booked",
                location = "Building B, Floor 2"
            ),
            Resource(
                name = "Projector System",
                type = "Equipment",
                capacity = "1 unit",
                currentUsage = "In use",
                status = "In Use",
                location = "Lab A-101"
            ),
            Resource(
                name = "Storage Room C",
                type = "Storage",
                capacity = "100 items",
                currentUsage = "65 items",
                status = "Available",
                location = "Building C, Basement"
            ),
            Resource(
                name = "Computer Lab D-201",
                type = "Lab Space",
                capacity = "30 students",
                currentUsage = "25 students",
                status = "In Use",
                location = "Building D, Floor 2"
            ),
            Resource(
                name = "Library Study Room",
                type = "Study Space",
                capacity = "8 people",
                currentUsage = "8 people",
                status = "Fully Booked",
                location = "Building E, Floor 1"
            ),
            Resource(
                name = "Workshop Area",
                type = "Workshop",
                capacity = "15 people",
                currentUsage = "10 people",
                status = "Available",
                location = "Building F, Ground Floor"
            ),
            Resource(
                name = "Seminar Hall",
                type = "Hall",
                capacity = "100 people",
                currentUsage = "0 people",
                status = "Available",
                location = "Building A, Floor 3"
            )
        )
    }

    val savedFilters = remember { mutableStateListOf<FilterTag>() }
    var searchQuery by rememberSaveable { mutableStateOf("") }
    
    // Dynamic two-level filter system
    val mainPropertyCategories = remember {
        listOf(
            "All",
            "Booking",
            "Ticket",
            "Department",
            "Resource Type",
            "Status",
            "Urgency",
            "Recently Added",
            "Buildings"
        )
    }
    var selectedMainProperty by rememberSaveable { mutableStateOf("All") }
    var selectedSubProperty by rememberSaveable { mutableStateOf<String?>(null) }
    
    // Sub-properties for each main property
    val subProperties = remember(selectedMainProperty) {
        when (selectedMainProperty) {
            "Department" -> listOf("MCA", "BCA", "Engineering Labs", "Physics", "Chemistry", "Biology", "Mathematics", "Computer Science", "Management Studies", "Sciences")
            "Status" -> listOf("Available", "In Use", "Under Maintenance", "Offline", "Reserved", "Fully Booked")
            "Building" -> listOf("Building A", "Building B", "Building C", "Building D", "Building E", "Building F", "Main Campus", "North Wing")
            "Resource Type" -> listOf("Laboratories", "Equipment", "Spaces", "Meeting Rooms", "Storage", "Workshops", "Study Areas")
            "Urgency" -> listOf("High Priority", "Medium Priority", "Low Priority", "Critical")
            "Booking" -> listOf("Pending Approvals", "New Requests", "Priority Requests", "Flagged Requests", "Approved Usages", "Rejected Requests")
            "Ticket" -> listOf("All Tickets", "Pending", "Active", "Closed", "Unassigned")
            "Recently Added" -> listOf("New Buildings", "New Spaces", "New Categories")
            "Buildings" -> listOf("Building A", "Building B", "Building C", "Building D", "Building E", "Building F", "Main Campus", "North Wing")
            else -> emptyList()
        }
    }

    val propertyRequests = remember { PropertyRequestProvider.sampleRequests() }
    val bookingGroup = remember { BookingGroupProvider.sampleBookings() }
    val equipmentGroup = remember { EquipmentGroupProvider.sampleEquipments() }
    val ticketGroupData = remember { TicketGroupDataProvider.sampleTickets() }
    val sections = remember(resources, propertyRequests, bookingGroup, equipmentGroup, ticketGroupData, recentlyAddedEquipment) { 
        buildResourceSections(resources, propertyRequests, bookingGroup, equipmentGroup, ticketGroupData, recentlyAddedEquipment) 
    }
    val sectionStates = remember(sections) {
        mutableStateMapOf<String, ResourceSectionState>().apply {
            sections.forEach { section ->
                val initialTab = section.tabs.firstOrNull()
                val initialSub = initialTab?.subTabs?.firstOrNull()
                put(section.title, ResourceSectionState(selectedTab = initialTab, selectedSubTab = initialSub))
            }
        }
    }
    
    // Helper function to get count for a sub-property from sections
    val getSubPropertyCount: (String, String) -> Int = { mainProp, subProp ->
        sections.find { it.title == mainProp }?.tabs?.flatMap { it.subTabs }
            ?.find { it.label == subProp }?.count ?: 0
    }
    
    // Filter sections based on selected property/sub-property
    val filteredSections = remember(selectedMainProperty, selectedSubProperty, sections) {
        if (selectedMainProperty == "All") {
            sections
        } else if (selectedSubProperty != null) {
            // Show only sections that match the selected property and sub-property
            sections.filter { section ->
                section.title == selectedMainProperty && 
                section.tabs.any { tab -> 
                    tab.subTabs.any { it.label == selectedSubProperty }
                }
            }
        } else {
            // Show only sections that match the selected property
            sections.filter { it.title == selectedMainProperty }
        }
    }

    Scaffold(
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
        floatingActionButton = {
            if (userRole == UserRole.ASSISTANT){
                AppFAB(
                    onClick = {searchViewModel.showChatSheet()},
                    modifier = Modifier
                )
            }
        },
        containerColor = whiteColor
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(
                        start = ResponsiveLayout.getHorizontalPadding(),
                        end = ResponsiveLayout.getHorizontalPadding(),
                        top = ResponsiveLayout.getVerticalPadding(),
                        bottom = ResponsiveLayout.getVerticalPadding()
            ),
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(18.dp, 24.dp, 28.dp))
        ) {
            // Title: "Home"
            item {
                CustomLabel(
                    header = "Home",
                    fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
                    headerColor = titleColor,
                    fontWeight = FontWeight.SemiBold
                )
            }

            // Home screen's original search bar and filter bar
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
            ) {
                AppSearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                    modifier = Modifier
                        .height(ResponsiveLayout.getResponsiveSize(46.dp, 60.dp, 68.dp))
                            .weight(1f)
                  )

                AppCircularIcon(
                    onClick = { filterSortViewModel.showSheet() },
                )
                }
            }

            // Two dropdown filter system
            item {
                TwoDropdownFilterSection(
                    mainProperties = mainPropertyCategories,
                    selectedMainProperty = selectedMainProperty,
                    onMainPropertySelected = { 
                        selectedMainProperty = it
                        selectedSubProperty = null // Reset sub-property when main property changes
                    },
                    subProperties = subProperties,
                    selectedSubProperty = selectedSubProperty,
                    onSubPropertySelected = { selectedSubProperty = it },
                    savedFilters = savedFilters,
                    onRemoveSavedFilter = { filter -> savedFilters.remove(filter) },
                    getSubPropertyCount = getSubPropertyCount,
                    selectedMainPropertyForCount = selectedMainProperty
                )
            }
            
            // Divider between properties section and list
            item {
                Divider(
                    modifier = Modifier.padding(vertical = ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp)),
                    color = onSurfaceColor.copy(alpha = 0.1f),
                    thickness = pxToDp(1)
                )
            }

            // Show content based on selected property
            if (selectedMainProperty == "All") {
                // Default state: Show only "All Resources" list
                item {
                    SectionDivider(
                        title = "All Resources",
                        badgeCount = resources.size,
                        isHighlighted = searchQuery.isNotEmpty()
                    )
                }

                items(resources) { resource ->
                    ResourceCard(resource = resource)
                }
            } else {
                // When a property is selected: Show only filtered sections with their cards
                filteredSections.forEach { section ->
                    item(key = section.title) {
                        val state = sectionStates[section.title] ?: ResourceSectionState()
                        val selectedTab = state.selectedTab ?: section.tabs.firstOrNull()
                        
                        // If a top-level sub-property is selected, use it to filter content
                        val effectiveSubTab = if (selectedSubProperty != null && selectedMainProperty == section.title) {
                            selectedTab?.subTabs?.find { it.label == selectedSubProperty }
                        } else {
                            state.selectedSubTab
                        }
                        
                        // Show content only for the effective sub-tab (filtered by top-level selection)
                        when {
                            effectiveSubTab?.cardType == CardType.MIXED && 
                            (effectiveSubTab.equipmentItems.isNotEmpty() || effectiveSubTab.buildingItems.isNotEmpty()) -> {
                                MixedRecentlyAddedContent(
                                    equipment = effectiveSubTab.equipmentItems,
                                    buildings = effectiveSubTab.buildingItems,
                                    bookings = effectiveSubTab.bookingItems,
                                    tickets = effectiveSubTab.ticketItems,
                                    navController = navController
                                )
                            }
                            effectiveSubTab?.cardType == CardType.MIXED -> {
                                MixedItemsContent(
                                    bookings = effectiveSubTab.bookingItems,
                                    tickets = effectiveSubTab.ticketItems,
                                    navController = navController
                                )
                            }
                            effectiveSubTab?.bookingItems?.isNotEmpty() == true -> {
                                BookingItemsContent(
                                    bookings = effectiveSubTab.bookingItems,
                                    navController = navController
                                )
                            }
                            effectiveSubTab?.equipmentItems?.isNotEmpty() == true -> {
                                EquipmentItemsContent(
                                    equipment = effectiveSubTab.equipmentItems,
                                    navController = navController
                                )
                            }
                            effectiveSubTab?.ticketItems?.isNotEmpty() == true -> {
                                TicketItemsContent(
                                    tickets = effectiveSubTab.ticketItems,
                                    navController = navController
                                )
                            }
                            effectiveSubTab?.buildingItems?.isNotEmpty() == true -> {
                                BuildingItemsContent(
                                    buildings = effectiveSubTab.buildingItems,
                                    navController = navController
                                )
                            }
                            effectiveSubTab?.propertyRequests?.isNotEmpty() == true -> {
                                PropertyRequestsContent(
                                    requests = effectiveSubTab.propertyRequests,
                                    showPriorityTag = effectiveSubTab.showPriorityTag,
                                    showQuickToggle = effectiveSubTab.showQuickToggle,
                                    showVerificationAction = effectiveSubTab.showVerificationAction,
                                    navController = navController
                                )
                            }
                            else -> {
                                val contentItems = effectiveSubTab?.content?.takeIf { it.isNotEmpty() }
                                    ?: (if (selectedSubProperty == null) selectedTab?.content else emptyList())
                                ResourceSectionContent(
                                    items = contentItems.orEmpty()
                                )
                            }
                        }
                    }
                }
            }
        }

        if (isFilterSheetVisible) {
            FilterSortBottomSheet(viewModel = filterSortViewModel)
        }

        if (isAiChatSheetVisible){
            ChatBottomSheet(viewModel = searchViewModel)
        }
    }
}

@Composable
fun ResourceCard(resource: Resource) {
    val statusColor = when (resource.status) {
        "Available" -> Color(0xFF26BB64)
        "In Use", "Fully Booked" -> Color(0xFFE67824)
        else -> onSurfaceColor
    }
    
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
            ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(12))
        ) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(pxToDp(4))
                ) {
                    CustomLabel(
                        header = resource.name,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                        headerColor = onSurfaceColor
                    )
                    CustomLabel(
                        header = resource.type,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = onSurfaceColor.copy(0.6f)
                    )
                }
                
                Box(
                    modifier = Modifier
                        .background(
                            color = statusColor.copy(0.2f),
                            shape = RoundedCornerShape(pxToDp(12))
                        )
                        .padding(
                            horizontal = pxToDp(12),
                            vertical = pxToDp(6)
                        )
                ) {
                    CustomLabel(
                        header = resource.status,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                        headerColor = statusColor
                    )
                }
            }
            
            // Details
            Column(
                verticalArrangement = Arrangement.spacedBy(pxToDp(8))
            ) {
                DetailRow("Location", resource.location)
                DetailRow("Capacity", resource.capacity)
                DetailRow("Current Usage", resource.currentUsage)
            }
        }
    }
}

@Composable
private fun ResourceSummaryRow(resources: List<Resource>) {
    val totalResources = resources.size
    val availableResources = resources.count { it.status == "Available" }
    val engagedResources = resources.count { it.status == "In Use" || it.status == "Fully Booked" }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
    ) {
        ResourceSummaryCard(
            label = "Total Resources",
            value = "$totalResources",
            accentColor = primaryColor
        )
        ResourceSummaryCard(
            label = "Available",
            value = "$availableResources",
            accentColor = Color(0xFF26BB64)
        )
        ResourceSummaryCard(
            label = "In Use",
            value = "$engagedResources",
            accentColor = Color(0xFFE67824)
        )
    }
}

@Composable
private fun ResourceSummaryCard(
    label: String,
    value: String,
    accentColor: Color
) {
    Card(
        modifier = Modifier,
        colors = CardDefaults.cardColors(
            containerColor = onSurfaceVariant
        ),
        shape = RoundedCornerShape(pxToDp(12))
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp),
                vertical = ResponsiveLayout.getResponsivePadding(18.dp, 22.dp, 26.dp)
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(pxToDp(6))
        ) {
            CustomLabel(
                header = value,
                headerColor = accentColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp)
            )
            CustomSmallLabel(
                header = label,
                headerColor = labelColor,
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun TwoDropdownFilterSection(
    mainProperties: List<String>,
    selectedMainProperty: String,
    onMainPropertySelected: (String) -> Unit,
    subProperties: List<String>,
    selectedSubProperty: String?,
    onSubPropertySelected: (String?) -> Unit,
    savedFilters: List<FilterTag>,
    onRemoveSavedFilter: (FilterTag) -> Unit,
    getSubPropertyCount: (String, String) -> Int,
    selectedMainPropertyForCount: String
) {
    var propertiesExpanded by remember { mutableStateOf(false) }
    val chipSpacing = ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 14.dp)
    
    Column(
        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp))
    ) {
        // Properties Dropdown Chip
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(chipSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                SelectableChip(
                    label = "Properties: $selectedMainProperty",
                    selected = true,
                    onClick = { propertiesExpanded = true },
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Filled.ArrowDropDown,
                            contentDescription = "Select property",
                            tint = primaryColor
                        )
                    }
                )
                
                DropdownMenu(
                    expanded = propertiesExpanded,
                    onDismissRequest = { propertiesExpanded = false },
                    modifier = Modifier.background(color = whiteColor)
                ) {
                    mainProperties.forEach { property ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = property,
                                    color = if (property == selectedMainProperty) primaryColor else onSurfaceColor
                                )
                            },
                            onClick = {
                                onMainPropertySelected(property)
                                propertiesExpanded = false
                            }
                        )
                    }
                }
            }
        }
        
        // Sub-Properties Tabs (tab-style, like BookingScreen)
        if (selectedMainProperty != "All" && subProperties.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                subProperties.forEach { subProperty ->
                    val count = getSubPropertyCount(selectedMainPropertyForCount, subProperty)
                    SubPropertyTab(
                        label = subProperty,
                        count = count,
                        selected = selectedSubProperty == subProperty,
                        onClick = { 
                            onSubPropertySelected(
                                if (selectedSubProperty == subProperty) null else subProperty
                            )
                        }
                    )
                }
            }
        }
        
        // Saved Filters Row
        if (savedFilters.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(chipSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                savedFilters.forEach { filter ->
                    SavedFilterChip(
                        filter = filter,
                        onRemove = onRemoveSavedFilter
                    )
                }
            }
        }
    }
}


@Composable
private fun SavedFilterChip(
    filter: FilterTag,
    onRemove: (FilterTag) -> Unit
) {
    SelectableChip(
        label = "${filter.label} (${filter.count})",
        selected = true,
        onClick = { onRemove(filter) },
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Close,
                contentDescription = "Remove ${filter.label}",
                tint = primaryColor
            )
        }
    )
}

@Composable
private fun SubPropertyTab(
    label: String,
    count: Int,
    selected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(pxToDp(10))
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(pxToDp(8))
            ) {
                CustomLabel(
                    header = label,
                    fontSize = 12.sp,
                    headerColor = if (selected) primaryColor else categoryIconColor
                )
                // Badge showing count
                Box(
                    modifier = Modifier
                        .background(
                            color = if (selected) primaryColor else categoryIconColor.copy(alpha = 0.2f),
                            shape = CircleShape
                        )
                        .padding(
                            horizontal = ResponsiveLayout.getResponsivePadding(6.dp, 8.dp, 10.dp),
                            vertical = ResponsiveLayout.getResponsivePadding(2.dp, 3.dp, 4.dp)
                        )
                ) {
                    Text(
                        text = count.toString(),
                        fontSize = ResponsiveLayout.getResponsiveFontSize(10.sp, 11.sp, 12.sp),
                        color = if (selected) whiteColor else categoryIconColor,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectableChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null
) {
    val backgroundColor = if (selected) primaryColor.copy(alpha = 0.12f) else Color.Transparent
    val borderColor = if (selected) primaryColor else chipColor
    Surface(
        shape = RoundedCornerShape(pxToDp(18)),
        color = backgroundColor,
        border = BorderStroke(width = pxToDp(1), color = borderColor),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(
                horizontal = ResponsiveLayout.getResponsivePadding(14.dp, 16.dp, 18.dp),
                vertical = ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp)
            ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(pxToDp(8))
        ) {
            leadingIcon?.invoke()
            CustomLabel(
                header = label,
                headerColor = if (selected) primaryColor else onSurfaceColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(13.sp, 14.sp, 15.sp),
                fontWeight = FontWeight.Medium,
                maxLine = 1
            )
            trailingIcon?.invoke()
        }
    }
}

@Composable
private fun SectionDivider(
    title: String,
    badgeCount: Int,
    isHighlighted: Boolean
) {
    val badgeBackground = if (isHighlighted) primaryColor else onSurfaceColor.copy(alpha = 0.08f)
    val badgeContentColor = if (isHighlighted) whiteColor else onSurfaceColor

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(pxToDp(2)))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
        modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = ResponsiveLayout.getResponsivePadding(6.dp, 8.dp, 10.dp)
                    ),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
    ) {
        Box(
            modifier = Modifier
                        .weight(1f)
                        .height(pxToDp(1))
                        .background(onSurfaceColor.copy(alpha = 0.1f))
                )
                CustomLabel(
                    header = title,
                    headerColor = onSurfaceColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp)
                )
                Surface(
                    color = badgeBackground,
                    shape = RoundedCornerShape(pxToDp(12))
                ) {
                    Text(
                        text = badgeCount.toString(),
                        color = badgeContentColor,
                        modifier = Modifier.padding(
                            horizontal = ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 14.dp),
                            vertical = ResponsiveLayout.getResponsivePadding(4.dp, 6.dp, 8.dp)
                        ),
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 13.sp, 14.sp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SectionTabRow(
    tabs: List<ResourceSectionTab>,
    selectedTab: ResourceSectionTab?,
    onTabSelected: (ResourceSectionTab) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(12.dp, 14.dp, 16.dp)),
        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
    ) {
        tabs.forEach { tab ->
            val isSelected = selectedTab?.label == tab.label
            SelectableChip(
                label = tab.label,
                selected = isSelected,
                onClick = { onTabSelected(tab) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SectionSubTabRow(
    subTabs: List<ResourceSectionSubTab>,
    selectedSubTab: ResourceSectionSubTab?,
    onSubTabSelected: (ResourceSectionSubTab) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 14.dp)),
        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
    ) {
        subTabs.forEach { subTab ->
            val isSelected = selectedSubTab?.label == subTab.label
            SelectableChip(
                label = "${subTab.label} (${subTab.count})",
                selected = isSelected,
                onClick = { onSubTabSelected(subTab) }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ResourceSectionContent(
    items: List<ResourceContentItem>
) {
    if (items.isEmpty()) {
        EmptySectionState()
        return
    }

    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing()),
        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(12.dp, 14.dp, 16.dp))
    ) {
        items.forEach { item ->
            ResourceInfoCard(item = item)
        }
    }
}

@Composable
private fun EmptySectionState() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RoundedCornerShape(pxToDp(12))
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = ResponsiveLayout.getResponsivePadding(18.dp, 22.dp, 26.dp),
                vertical = ResponsiveLayout.getResponsivePadding(20.dp, 24.dp, 28.dp)
            ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(6))
        ) {
            CustomLabel(
                header = "Nothing to show yet",
                headerColor = onSurfaceColor,
                fontWeight = FontWeight.SemiBold
            )
            CustomSmallLabel(
                header = "Adjust the filters to view resources or logs for this section.",
                headerColor = labelColor.copy(alpha = 0.8f),
                modifier = Modifier
            )
        }
    }
}

@Composable
private fun MixedItemsContent(
    bookings: List<BookingItem>,
    tickets: List<TicketItem>,
    navController: NavHostController
) {
    if (bookings.isEmpty() && tickets.isEmpty()) {
        EmptySectionState()
        return
    }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
    ) {
        bookings.forEach { booking ->
            InfoCard(
                bookingItem = booking,
                onEditBooking = { navController.navigate(Screen.CalendarScreen.route) },
                onExtendBooking = { /* Handle extend booking */ },
                onReRequestBooking = { /* Handle re-request booking */ }
            )
        }
        tickets.forEach { ticket ->
            SimpleTicketCard(
                ticket = ticket,
                onClick = { /* Navigate to ticket details */ }
            )
        }
    }
}

@Composable
private fun MixedRecentlyAddedContent(
    equipment: List<Items>,
    buildings: List<BuildingItem>,
    bookings: List<BookingItem> = emptyList(),
    tickets: List<TicketItem> = emptyList(),
    navController: NavHostController
) {
    if (equipment.isEmpty() && buildings.isEmpty() && bookings.isEmpty() && tickets.isEmpty()) {
        EmptySectionState()
        return
    }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
    ) {
        // Display equipment cards in grid
        if (equipment.isNotEmpty()) {
            LazyVerticalGrid(
                columns = ResponsiveLayout.getGridColumns(),
                contentPadding = PaddingValues(horizontal = ResponsiveLayout.getHorizontalPadding()),
                verticalArrangement = ResponsiveLayout.getVerticalGridArrangement(),
                horizontalArrangement = ResponsiveLayout.getGridArrangement(),
            ) {
                items(equipment) { item ->
                    EquipmentCard(
                        image = if (item.image_url.isNotEmpty()) item.image_url else R.drawable.temp,
                        equipName = item.name,
                        available = if (item.is_available == true) "Available" else "Not Available",
                        onClick = { navController.navigate(Screen.ProductDescriptionScreen.route) },
                        isSaved = false,
                        saveClick = { /* Handle save */ },
                        facilityName = "Lab Facility"
                    )
                }
            }
        }
        
        // Display building cards in grid
        if (buildings.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(horizontal = ResponsiveLayout.getHorizontalPadding()),
                verticalArrangement = ResponsiveLayout.getVerticalGridArrangement(),
                horizontalArrangement = ResponsiveLayout.getGridArrangement(),
            ) {
                items(buildings) { building ->
                    BuildingCard(
                        building = building,
                        onClick = { /* Navigate to building details */ }
                    )
                }
            }
        }
        
        // Display booking cards
        bookings.forEach { booking ->
            InfoCard(
                bookingItem = booking,
                onEditBooking = { navController.navigate(Screen.CalendarScreen.route) },
                onExtendBooking = { /* Handle extend booking */ },
                onReRequestBooking = { /* Handle re-request booking */ }
            )
        }
        
        // Display ticket cards
        tickets.forEach { ticket ->
            SimpleTicketCard(
                ticket = ticket,
                onClick = { /* Navigate to ticket details */ }
            )
        }
    }
}

@Composable
private fun BookingItemsContent(
    bookings: List<BookingItem>,
    navController: NavHostController
) {
    if (bookings.isEmpty()) {
        EmptySectionState()
        return
    }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
    ) {
        bookings.forEach { booking ->
            InfoCard(
                bookingItem = booking,
                onEditBooking = { navController.navigate(Screen.CalendarScreen.route) },
                onExtendBooking = { /* Handle extend booking */ },
                onReRequestBooking = { /* Handle re-request booking */ }
            )
        }
    }
}

@Composable
private fun EquipmentItemsContent(
    equipment: List<Items>,
    navController: NavHostController
) {
    if (equipment.isEmpty()) {
        EmptySectionState()
        return
    }
    
    LazyVerticalGrid(
        columns = ResponsiveLayout.getGridColumns(),
        contentPadding = PaddingValues(horizontal = ResponsiveLayout.getHorizontalPadding()),
        verticalArrangement = ResponsiveLayout.getVerticalGridArrangement(),
        horizontalArrangement = ResponsiveLayout.getGridArrangement(),
    ) {
        items(equipment) { item ->
            EquipmentCard(
                image = if (item.image_url.isNotEmpty()) item.image_url else R.drawable.temp,
                equipName = item.name,
                available = if (item.is_available == true) "Available" else "Not Available",
                onClick = { navController.navigate(Screen.ProductDescriptionScreen.route) },
                isSaved = false,
                saveClick = { /* Handle save */ },
                facilityName = "Lab Facility"
            )
        }
    }
}

@Composable
private fun TicketItemsContent(
    tickets: List<TicketItem>,
    navController: NavHostController
) {
    if (tickets.isEmpty()) {
        EmptySectionState()
        return
    }
    
    Column(
        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getCardSpacing())
    ) {
        tickets.forEach { ticket ->
            SimpleTicketCard(
                ticket = ticket,
                onClick = { /* Navigate to ticket details */ }
            )
        }
    }
}

@Composable
private fun BuildingItemsContent(
    buildings: List<BuildingItem>,
    navController: NavHostController
) {
    if (buildings.isEmpty()) {
        EmptySectionState()
        return
    }
    
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = ResponsiveLayout.getHorizontalPadding()),
        verticalArrangement = ResponsiveLayout.getVerticalGridArrangement(),
        horizontalArrangement = ResponsiveLayout.getGridArrangement(),
    ) {
        items(buildings) { building ->
            BuildingCard(
                building = building,
                onClick = { /* Navigate to building details */ }
            )
        }
    }
}

@Composable
private fun SimpleTicketCard(
    ticket: TicketItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RoundedCornerShape(pxToDp(12))
    ) {
        Column(
            modifier = Modifier.padding(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)),
            verticalArrangement = Arrangement.spacedBy(pxToDp(12))
        ) {
            CustomLabel(
                header = ticket.title,
                headerColor = onSurfaceColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp)
            )
            CustomSmallLabel(
                header = ticket.description,
                headerColor = onSurfaceColor.copy(alpha = 0.6f),
                modifier = Modifier
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(pxToDp(8)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            color = ticket.priority.dispColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(pxToDp(8))
                        )
                        .padding(horizontal = pxToDp(8), vertical = pxToDp(4))
                ) {
                    CustomLabel(
                        header = ticket.priority.dispName,
                        headerColor = ticket.priority.dispColor,
                        fontSize = 12.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            color = ticket.status.dispColor.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(pxToDp(8))
                        )
                        .padding(horizontal = pxToDp(8), vertical = pxToDp(4))
                ) {
                    CustomLabel(
                        header = ticket.status.dispName,
                        headerColor = ticket.status.dispColor,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun BuildingCard(
    building: BuildingItem,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RoundedCornerShape(pxToDp(12))
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ResponsiveLayout.getResponsiveSize(120.dp, 140.dp, 160.dp))
                    .background(onSurfaceVariant)
            ) {
                if (building.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = building.imageUrl,
                        contentDescription = building.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Building Image",
                            tint = primaryColor,
                            modifier = Modifier.size(pxToDp(48))
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp)),
                verticalArrangement = Arrangement.spacedBy(pxToDp(6))
            ) {
                CustomLabel(
                    header = building.name,
                    headerColor = onSurfaceColor,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
                CustomSmallLabel(
                    header = building.department,
                    headerColor = onSurfaceColor.copy(alpha = 0.6f),
                    modifier = Modifier
                )
                if (building.location.isNotEmpty()) {
                    CustomSmallLabel(
                        header = building.location,
                        headerColor = onSurfaceColor.copy(alpha = 0.5f),
                        modifier = Modifier
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(pxToDp(12)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CustomSmallLabel(
                        header = "${building.equipmentCount} Equipment",
                        headerColor = primaryColor,
                        modifier = Modifier
                    )
                    if (building.floorCount > 0) {
                        CustomSmallLabel(
                            header = "${building.floorCount} Floors",
                            headerColor = categoryIconColor,
                            modifier = Modifier
                        )
                    }
                    if (building.capacity.isNotEmpty()) {
                        CustomSmallLabel(
                            header = building.capacity,
                            headerColor = categoryIconColor,
                            modifier = Modifier
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ResourceInfoCard(item: ResourceContentItem) {
    Card(
        modifier = Modifier.widthIn(
            min = ResponsiveLayout.getResponsiveSize(180.dp, 220.dp, 260.dp)
        ),
        colors = CardDefaults.cardColors(containerColor = onSurfaceVariant),
        shape = RoundedCornerShape(pxToDp(12))
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = ResponsiveLayout.getResponsivePadding(18.dp, 22.dp, 26.dp),
                vertical = ResponsiveLayout.getResponsivePadding(18.dp, 22.dp, 26.dp)
            ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(6))
        ) {
            CustomLabel(
                header = item.title,
                headerColor = onSurfaceColor,
                fontWeight = FontWeight.SemiBold,
                maxLine = 2
            )
            Text(
                text = item.subtitle,
                color = labelColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 13.sp, 14.sp),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            CustomSmallLabel(
                header = item.meta,
                headerColor = primaryColor,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
            )
        }
    }
}

private fun MutableList<FilterTag>.updateFilter(section: String, subTab: ResourceSectionSubTab) {
    val tag = FilterTag(section, subTab.label, subTab.count)
    val existingIndex = indexOfFirst { it.section == section }
    if (existingIndex >= 0) {
        this[existingIndex] = tag
    } else {
        add(tag)
    }
}

private fun MutableList<FilterTag>.removeWhere(predicate: (FilterTag) -> Boolean) {
    val iterator = iterator()
    while (iterator.hasNext()) {
        if (predicate(iterator.next())) {
            iterator.remove()
        }
    }
}


private fun getDummyBookingItems(): List<BookingItem> {
    return listOf(
        BookingItem(
            id = "1",
            productInfo = ProductInfo(
                R.drawable.temp,
                "Canon EOS R50 V", "IDC", "Photo Studio", Status.PENDING
            ),
            inChargeInfo = InChargeInfo(
                profName = "Sumant Rao",
                asstName = "Akash Kumar Swami",
                asstIcons = listOf(R.drawable.ic_mail, R.drawable.ic_call)
            ),
            bookingDates = BookingDates("2025-08-01", "2025-08-10"),
            bookingStatus = BookingStatus.VERIFICATION_PENDING,
            priority = BookingPriority.HIGH
        ),
        BookingItem(
            id = "2",
            productInfo = ProductInfo(
                R.drawable.temp,
                "Sony Alpha A7III", "LAB-A", "Electronics Lab", Status.BOOKED
            ),
            inChargeInfo = InChargeInfo(
                profName = "Dr. Smith",
                asstName = "Jane Doe",
                asstIcons = listOf(R.drawable.ic_mail, R.drawable.ic_call)
            ),
            bookingDates = BookingDates("2025-07-15", "2025-08-15"),
            bookingStatus = BookingStatus.APPROVED,
            priority = BookingPriority.MEDIUM
        ),
        BookingItem(
            id = "3",
            productInfo = ProductInfo(
                R.drawable.temp,
                "Conference Room B-201", "CONF-B", "Building B, Floor 2", Status.PENDING
            ),
            inChargeInfo = InChargeInfo(
                profName = "Dr. Johnson",
                asstName = "Robert Brown",
                asstIcons = listOf(R.drawable.ic_mail, R.drawable.ic_call)
            ),
            bookingDates = BookingDates("2025-08-20", "2025-08-25"),
            bookingStatus = BookingStatus.VERIFICATION_PENDING,
            priority = BookingPriority.HIGH
        ),
        BookingItem(
            id = "4",
            productInfo = ProductInfo(
                R.drawable.temp,
                "Lab Space A-101", "LAB-A", "Building A, Floor 1", Status.BOOKED
            ),
            inChargeInfo = InChargeInfo(
                profName = "Dr. Williams",
                asstName = "Emily Davis",
                asstIcons = listOf(R.drawable.ic_mail, R.drawable.ic_call)
            ),
            bookingDates = BookingDates("2025-09-01", "2025-09-10"),
            bookingStatus = BookingStatus.APPROVED,
            priority = BookingPriority.LOW
        ),
        BookingItem(
            id = "5",
            productInfo = ProductInfo(
                R.drawable.temp,
                "Projector System", "PROJ-01", "Seminar Hall", Status.PENDING
            ),
            inChargeInfo = InChargeInfo(
                profName = "Dr. Anderson",
                asstName = "Michael Wilson",
                asstIcons = listOf(R.drawable.ic_mail, R.drawable.ic_call)
            ),
            bookingDates = BookingDates("2025-08-05", "2025-08-12"),
            bookingStatus = BookingStatus.VERIFICATION_PENDING,
            priority = BookingPriority.MEDIUM
        ),
        BookingItem(
            id = "6",
            productInfo = ProductInfo(
                R.drawable.temp,
                "Lab Space D-301", "LAB-D", "Building D, Floor 3", Status.CANCELLED
            ),
            inChargeInfo = InChargeInfo(
                profName = "Dr. Taylor",
                asstName = "Sarah Martinez",
                asstIcons = listOf(R.drawable.ic_mail, R.drawable.ic_call)
            ),
            bookingDates = BookingDates("2025-07-20", "2025-07-25"),
            bookingStatus = BookingStatus.REJECTED,
            priority = BookingPriority.LOW,
            rejectionReason = "Space already booked"
        )
    )
}

private fun getDummyTicketItems(): List<TicketItem> {
    return listOf(
        TicketItem(
            id = "TICK-001",
            title = "Equipment Not Working",
            description = "Oscilloscope in Lab B-205 is not displaying correctly. Need immediate attention.",
            status = TicketStatus.PENDING,
            priority = TicketPriority.HIGH,
            category = TicketCategory.TECHNICAL
        ),
        TicketItem(
            id = "TICK-002",
            title = "Maintenance Request",
            description = "3D Printer needs regular maintenance and calibration.",
            status = TicketStatus.ACTIVE,
            priority = TicketPriority.MEDIUM,
            category = TicketCategory.TECHNICAL
        ),
        TicketItem(
            id = "TICK-003",
            title = "Equipment Setup Help",
            description = "Need assistance setting up new DNA Sequencer in Lab C-301.",
            status = TicketStatus.PENDING,
            priority = TicketPriority.LOW,
            category = TicketCategory.NON_TECHNICAL
        ),
        TicketItem(
            id = "TICK-004",
            title = "Network Connectivity Issue",
            description = "Wi-Fi connection unstable in Computer Lab D-201. Affecting multiple workstations.",
            status = TicketStatus.ACTIVE,
            priority = TicketPriority.HIGH,
            category = TicketCategory.TECHNICAL
        ),
        TicketItem(
            id = "TICK-005",
            title = "Software Installation Request",
            description = "Need MATLAB installed on Lab A-101 computers for upcoming semester.",
            status = TicketStatus.PENDING,
            priority = TicketPriority.MEDIUM,
            category = TicketCategory.NON_TECHNICAL
        ),
        TicketItem(
            id = "TICK-006",
            title = "Air Conditioning Problem",
            description = "AC unit in Conference Room B-201 not cooling properly. Temperature too high.",
            status = TicketStatus.PENDING,
            priority = TicketPriority.HIGH,
            category = TicketCategory.NON_TECHNICAL
        ),
        TicketItem(
            id = "TICK-007",
            title = "Server Maintenance Complete",
            description = "Main server room maintenance completed successfully. All systems operational.",
            status = TicketStatus.CLOSED,
            priority = TicketPriority.MEDIUM,
            category = TicketCategory.TECHNICAL
        ),
        TicketItem(
            id = "TICK-008",
            title = "Security Camera Installation",
            description = "New security cameras need to be installed in Building C corridors.",
            status = TicketStatus.UNASSIGNED,
            priority = TicketPriority.HIGH,
            category = TicketCategory.NON_TECHNICAL
        ),
        TicketItem(
            id = "TICK-009",
            title = "Database Migration",
            description = "Database migration to new server completed. Verification needed.",
            status = TicketStatus.CLOSED,
            priority = TicketPriority.HIGH,
            category = TicketCategory.TECHNICAL
        ),
        TicketItem(
            id = "TICK-010",
            title = "Fire Alarm System Check",
            description = "Annual fire alarm system inspection and testing required.",
            status = TicketStatus.UNASSIGNED,
            priority = TicketPriority.MEDIUM,
            category = TicketCategory.NON_TECHNICAL
        )
    )
}

private fun getDummyBuildingItems(): List<BuildingItem> {
    return listOf(
        BuildingItem(
            id = "B1",
            name = "Building A",
            department = "Engineering",
            equipmentCount = 25,
            location = "North Campus",
            floorCount = 5,
            capacity = "500 students"
        ),
        BuildingItem(
            id = "B2",
            name = "Building B",
            department = "Sciences",
            equipmentCount = 18,
            location = "South Campus",
            floorCount = 4,
            capacity = "350 students"
        ),
        BuildingItem(
            id = "B3",
            name = "Building C",
            department = "Computer Science",
            equipmentCount = 32,
            location = "East Wing",
            floorCount = 6,
            capacity = "600 students"
        ),
        BuildingItem(
            id = "B4",
            name = "Building D",
            department = "Management",
            equipmentCount = 12,
            location = "West Wing",
            floorCount = 3,
            capacity = "250 students"
        ),
        BuildingItem(
            id = "B5",
            name = "Building E",
            department = "Library",
            equipmentCount = 8,
            location = "Central Campus",
            floorCount = 2,
            capacity = "200 students"
        ),
        BuildingItem(
            id = "B6",
            name = "Building F",
            department = "Workshop",
            equipmentCount = 15,
            location = "Industrial Zone",
            floorCount = 2,
            capacity = "150 students"
        )
    )
}

private fun getRecentlyAddedBuildings(): List<BuildingItem> {
    return listOf(
        BuildingItem(
            id = "B7",
            name = "Innovation Center",
            department = "Research & Development",
            equipmentCount = 45,
            location = "Main Campus, Floor 1-3",
            floorCount = 3,
            capacity = "400 researchers"
        ),
        BuildingItem(
            id = "B8",
            name = "Technology Hub",
            department = "Computer Science & Engineering",
            equipmentCount = 38,
            location = "East Wing Extension",
            floorCount = 4,
            capacity = "550 students"
        ),
        BuildingItem(
            id = "B9",
            name = "Science Complex",
            department = "Life Sciences & Chemistry",
            equipmentCount = 52,
            location = "South Campus, Building 2",
            floorCount = 5,
            capacity = "450 students"
        )
    )
}


private fun buildResourceSections(
    resources: List<Resource>,
    propertyRequests: PropertyRequestGroup,
    bookingGroup: BookingGroup,
    equipmentGroup: EquipmentGroup,
    ticketGroupData: TicketGroupData,
    recentlyAddedEquipment: List<Items> = emptyList()
): List<ResourceSection> {
    val recentlyAddedItems = resources.map {
        ResourceContentItem(
            title = it.name,
            subtitle = "${it.type} - ${it.location}",
            meta = "${it.status} - ${it.capacity}"
        )
    }

    return listOf(
        ResourceSection(
            title = "All",
            tabs = listOf(
                ResourceSectionTab(
                    label = "All Resources",
                    count = resources.size,
                    content = recentlyAddedItems.take(4)
                )
            )
        ),
        ResourceSection(
            title = "Booking",
            tabs = listOf(
                ResourceSectionTab(
                    label = "All Bookings",
                    count = getDummyBookingItems().size,
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "Pending Approvals",
                            count = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.VERIFICATION_PENDING }.size,
                            bookingItems = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.VERIFICATION_PENDING },
                            cardType = CardType.BOOKING
                        ),
                        ResourceSectionSubTab(
                            label = "New Requests",
                            count = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.VERIFICATION_PENDING }.size,
                            bookingItems = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.VERIFICATION_PENDING },
                            cardType = CardType.BOOKING
                        ),
                        ResourceSectionSubTab(
                            label = "Priority Requests",
                            count = getDummyBookingItems().filter { it.priority == BookingPriority.HIGH }.size,
                            bookingItems = getDummyBookingItems().filter { it.priority == BookingPriority.HIGH },
                            showPriorityTag = true,
                            showQuickToggle = true,
                            cardType = CardType.BOOKING
                        ),
                        ResourceSectionSubTab(
                            label = "Flagged Requests",
                            count = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.VERIFICATION_PENDING }.size,
                            bookingItems = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.VERIFICATION_PENDING },
                            showVerificationAction = true,
                            cardType = CardType.BOOKING
                        ),
                        ResourceSectionSubTab(
                            label = "Approved Usages",
                            count = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.APPROVED }.size,
                            bookingItems = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.APPROVED },
                            cardType = CardType.BOOKING
                        ),
                        ResourceSectionSubTab(
                            label = "Rejected Requests",
                            count = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.REJECTED }.size,
                            bookingItems = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.REJECTED },
                            cardType = CardType.BOOKING
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Ticket",
            tabs = listOf(
                ResourceSectionTab(
                    label = "All Tickets",
                    count = getDummyTicketItems().size,
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "All Tickets",
                            count = getDummyTicketItems().size,
                            ticketItems = getDummyTicketItems(),
                            cardType = CardType.TICKET
                        ),
                        ResourceSectionSubTab(
                            label = "Pending",
                            count = getDummyTicketItems().filter { it.status == TicketStatus.PENDING }.size,
                            ticketItems = getDummyTicketItems().filter { it.status == TicketStatus.PENDING },
                            cardType = CardType.TICKET
                        ),
                        ResourceSectionSubTab(
                            label = "Active",
                            count = getDummyTicketItems().filter { it.status == TicketStatus.ACTIVE }.size,
                            ticketItems = getDummyTicketItems().filter { it.status == TicketStatus.ACTIVE },
                            cardType = CardType.TICKET
                        ),
                        ResourceSectionSubTab(
                            label = "Closed",
                            count = getDummyTicketItems().filter { it.status == TicketStatus.CLOSED }.size,
                            ticketItems = getDummyTicketItems().filter { it.status == TicketStatus.CLOSED },
                            cardType = CardType.TICKET
                        ),
                        ResourceSectionSubTab(
                            label = "Unassigned",
                            count = getDummyTicketItems().filter { it.status == TicketStatus.UNASSIGNED }.size,
                            ticketItems = getDummyTicketItems().filter { it.status == TicketStatus.UNASSIGNED },
                            cardType = CardType.TICKET
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Department",
            tabs = listOf(
                ResourceSectionTab(
                    label = "Computer Applications",
                    count = 9,
                    content = listOf(
                        ResourceContentItem(
                            title = "Development Cluster",
                            subtitle = "Full-stack lab with cloud ready nodes",
                            meta = "6 collaborative pods"
                        ),
                        ResourceContentItem(
                            title = "AI & Analytics Lab",
                            subtitle = "High performance systems for ML workloads",
                            meta = "4 GPU rigs available"
                        ),
                        ResourceContentItem(
                            title = "Network Security Lab",
                            subtitle = "Cybersecurity testing and research facility",
                            meta = "8 workstations configured"
                        )
                    ),
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "MCA",
                            count = 4,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Advanced Programming Lab",
                                    subtitle = "Hands-on sessions for distributed systems",
                                    meta = "18/24 seats reserved"
                                ),
                                ResourceContentItem(
                                    title = "Database Systems Lab",
                                    subtitle = "SQL and NoSQL database training",
                                    meta = "12/16 systems available"
                                ),
                                ResourceContentItem(
                                    title = "Web Development Studio",
                                    subtitle = "Full-stack development environment",
                                    meta = "20 seats available"
                                )
                            )
                        ),
                        ResourceSectionSubTab(
                            label = "BCA",
                            count = 3,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Design Thinking Studio",
                                    subtitle = "Collaborative space for solution sprints",
                                    meta = "Toolkit refreshed this week"
                                ),
                                ResourceContentItem(
                                    title = "Programming Fundamentals Lab",
                                    subtitle = "Basic programming and algorithms",
                                    meta = "15/20 systems in use"
                                ),
                                ResourceContentItem(
                                    title = "Multimedia Lab",
                                    subtitle = "Graphics and video editing workspace",
                                    meta = "10 stations available"
                                )
                            )
                        ),
                        ResourceSectionSubTab(
                            label = "Engineering Labs",
                            count = 5,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Embedded Systems Lab",
                                    subtitle = "Microcontroller and IoT development",
                                    meta = "8 kits available"
                                ),
                                ResourceContentItem(
                                    title = "Digital Circuits Lab",
                                    subtitle = "Logic gates and circuit design",
                                    meta = "12 breadboards ready"
                                ),
                                ResourceContentItem(
                                    title = "Communication Systems Lab",
                                    subtitle = "Signal processing and networks",
                                    meta = "6 workstations configured"
                                )
                            )
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Management Studies",
                    count = 6,
                    content = listOf(
                        ResourceContentItem(
                            title = "Strategy Lab",
                            subtitle = "Simulation tools for business cases",
                            meta = "Upcoming module: Market Analytics"
                        ),
                        ResourceContentItem(
                            title = "Finance Lab",
                            subtitle = "Financial modeling and analysis tools",
                            meta = "15 Bloomberg terminals"
                        ),
                        ResourceContentItem(
                            title = "Marketing Studio",
                            subtitle = "Digital marketing and analytics workspace",
                            meta = "10 collaborative pods"
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Resource Type",
            tabs = listOf(
                ResourceSectionTab(
                    label = "Laboratories",
                    count = 8,
                    content = listOf(
                        ResourceContentItem(
                            title = "Embedded Systems Lab",
                            subtitle = "Microcontroller kits, oscilloscopes, logic analyzers",
                            meta = "5 kits reserved"
                        ),
                        ResourceContentItem(
                            title = "Computer Networks Lab",
                            subtitle = "Router configuration and network testing",
                            meta = "12 workstations active"
                        ),
                        ResourceContentItem(
                            title = "Digital Signal Processing Lab",
                            subtitle = "MATLAB and signal analysis tools",
                            meta = "8 systems available"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Equipment",
                    count = 10,
                    content = listOf(
                        ResourceContentItem(
                            title = "3D Printers",
                            subtitle = "FDM and SLA printers with material inventory",
                            meta = "7 jobs queued"
                        ),
                        ResourceContentItem(
                            title = "Oscilloscopes",
                            subtitle = "Digital oscilloscopes for signal analysis",
                            meta = "5 units available"
                        ),
                        ResourceContentItem(
                            title = "Function Generators",
                            subtitle = "Signal generation equipment",
                            meta = "3 units in use"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Spaces",
                    count = 6,
                    content = listOf(
                        ResourceContentItem(
                            title = "Collaboration Pods",
                            subtitle = "Smart pods for hybrid collaboration",
                            meta = "Auto-release after 15 min idle"
                        ),
                        ResourceContentItem(
                            title = "Meeting Rooms",
                            subtitle = "Conference rooms with AV equipment",
                            meta = "6 rooms available"
                        ),
                        ResourceContentItem(
                            title = "Study Areas",
                            subtitle = "Quiet study spaces for students",
                            meta = "20 seats available"
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Status",
            tabs = listOf(
                ResourceSectionTab(
                    label = "All Status",
                    count = getDummyBookingItems().size + getDummyTicketItems().size,
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "Available",
                            count = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.APPROVED }.size,
                            bookingItems = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.APPROVED },
                            cardType = CardType.BOOKING
                        ),
                        ResourceSectionSubTab(
                            label = "In Use",
                            count = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.VERIFICATION_PENDING }.size,
                            bookingItems = getDummyBookingItems().filter { it.bookingStatus == BookingStatus.VERIFICATION_PENDING },
                            cardType = CardType.BOOKING
                        ),
                        ResourceSectionSubTab(
                            label = "Under Maintenance",
                            count = getDummyTicketItems().filter { it.status == TicketStatus.ACTIVE && it.category == TicketCategory.TECHNICAL }.size,
                            ticketItems = getDummyTicketItems().filter { it.status == TicketStatus.ACTIVE && it.category == TicketCategory.TECHNICAL },
                            cardType = CardType.TICKET
                        ),
                        ResourceSectionSubTab(
                            label = "Offline",
                            count = getDummyTicketItems().filter { it.status == TicketStatus.CLOSED }.size,
                            ticketItems = getDummyTicketItems().filter { it.status == TicketStatus.CLOSED },
                            cardType = CardType.TICKET
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Urgency",
            tabs = listOf(
                ResourceSectionTab(
                    label = "All Urgency",
                    count = getDummyBookingItems().size + getDummyTicketItems().size,
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "High Priority",
                            count = getDummyBookingItems().filter { it.priority == BookingPriority.HIGH }.size + 
                                   getDummyTicketItems().filter { it.priority == TicketPriority.HIGH }.size,
                            bookingItems = getDummyBookingItems().filter { it.priority == BookingPriority.HIGH },
                            ticketItems = getDummyTicketItems().filter { it.priority == TicketPriority.HIGH },
                            cardType = CardType.MIXED
                        ),
                        ResourceSectionSubTab(
                            label = "Medium Priority",
                            count = getDummyBookingItems().filter { it.priority == BookingPriority.MEDIUM }.size + 
                                   getDummyTicketItems().filter { it.priority == TicketPriority.MEDIUM }.size,
                            bookingItems = getDummyBookingItems().filter { it.priority == BookingPriority.MEDIUM },
                            ticketItems = getDummyTicketItems().filter { it.priority == TicketPriority.MEDIUM },
                            cardType = CardType.MIXED
                        ),
                        ResourceSectionSubTab(
                            label = "Low Priority",
                            count = getDummyBookingItems().filter { it.priority == BookingPriority.LOW }.size + 
                                   getDummyTicketItems().filter { it.priority == TicketPriority.LOW }.size,
                            bookingItems = getDummyBookingItems().filter { it.priority == BookingPriority.LOW },
                            ticketItems = getDummyTicketItems().filter { it.priority == TicketPriority.LOW },
                            cardType = CardType.MIXED
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Buildings",
            tabs = listOf(
                ResourceSectionTab(
                    label = "All Buildings",
                    count = getDummyBuildingItems().size,
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "All Buildings",
                            count = getDummyBuildingItems().size,
                            buildingItems = getDummyBuildingItems(),
                            cardType = CardType.BUILDING
                        ),
                        ResourceSectionSubTab(
                            label = "Building A",
                            count = getDummyBuildingItems().filter { it.name.contains("Building A") || it.id == "B1" }.size,
                            buildingItems = getDummyBuildingItems().filter { it.name.contains("Building A") || it.id == "B1" },
                            cardType = CardType.BUILDING
                        ),
                        ResourceSectionSubTab(
                            label = "Building B",
                            count = getDummyBuildingItems().filter { it.name.contains("Building B") || it.id == "B2" }.size,
                            buildingItems = getDummyBuildingItems().filter { it.name.contains("Building B") || it.id == "B2" },
                            cardType = CardType.BUILDING
                        ),
                        ResourceSectionSubTab(
                            label = "Building C",
                            count = getDummyBuildingItems().filter { it.name.contains("Building C") || it.id == "B3" }.size,
                            buildingItems = getDummyBuildingItems().filter { it.name.contains("Building C") || it.id == "B3" },
                            cardType = CardType.BUILDING
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Recently Added",
            tabs = listOf(
                ResourceSectionTab(
                    label = "All Recently Added",
                    count = recentlyAddedEquipment.size + getRecentlyAddedBuildings().size + recentlyAddedItems.size,
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "New Buildings",
                            count = getRecentlyAddedBuildings().size,
                            buildingItems = getRecentlyAddedBuildings(),
                            equipmentItems = emptyList(),
                            cardType = CardType.MIXED
                        ),
                        ResourceSectionSubTab(
                            label = "New Equipment",
                            count = recentlyAddedEquipment.size,
                            buildingItems = emptyList(),
                            equipmentItems = recentlyAddedEquipment,
                            cardType = CardType.MIXED
                        ),
                        ResourceSectionSubTab(
                            label = "Mixed Recent",
                            count = recentlyAddedEquipment.take(2).size + getRecentlyAddedBuildings().take(2).size,
                            buildingItems = getRecentlyAddedBuildings().take(2),
                            equipmentItems = recentlyAddedEquipment.take(2),
                            cardType = CardType.MIXED
                        )
                    )
                )
            )
        )
    )
}

data class FilterTag(
    val section: String,
    val label: String,
    val count: Int
)

data class ResourceSection(
    val title: String,
    val tabs: List<ResourceSectionTab>
) {
    val totalCount: Int = tabs.sumOf { it.count }
}

data class ResourceSectionTab(
    val label: String,
    val count: Int,
    val content: List<ResourceContentItem> = emptyList(),
    val subTabs: List<ResourceSectionSubTab> = emptyList()
)

data class ResourceSectionSubTab(
    val label: String,
    val count: Int,
    val content: List<ResourceContentItem> = emptyList(),
    val propertyRequests: List<PropertyRequest> = emptyList(),
    val bookingItems: List<BookingItem> = emptyList(),
    val equipmentItems: List<com.example.rmsjims.data.schema.Items> = emptyList(),
    val ticketItems: List<TicketItem> = emptyList(),
    val buildingItems: List<BuildingItem> = emptyList(),
    val showPriorityTag: Boolean = false,
    val showQuickToggle: Boolean = false,
    val showVerificationAction: Boolean = false,
    val cardType: CardType = CardType.CONTENT
)

enum class CardType {
    CONTENT,
    PROPERTY_REQUEST,
    BOOKING,
    EQUIPMENT,
    TICKET,
    BUILDING,
    MIXED
}

data class BuildingItem(
    val id: String,
    val name: String,
    val department: String,
    val equipmentCount: Int,
    val imageUrl: String = "",
    val location: String = "",
    val floorCount: Int = 0,
    val capacity: String = ""
)

data class ResourceSectionState(
    val selectedTab: ResourceSectionTab? = null,
    val selectedSubTab: ResourceSectionSubTab? = null
)

data class ResourceContentItem(
    val title: String,
    val subtitle: String,
    val meta: String
)

// Resource data class
data class Resource(
    val name: String,
    val type: String,
    val capacity: String,
    val currentUsage: String,
    val status: String,
    val location: String
)

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomLabel(
            header = "$label:",
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
            headerColor = onSurfaceColor.copy(0.7f)
        )
        CustomLabel(
            header = value,
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
            headerColor = onSurfaceColor
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController : NavHostController = rememberNavController()
    HomeScreen(navController = navController)
}
