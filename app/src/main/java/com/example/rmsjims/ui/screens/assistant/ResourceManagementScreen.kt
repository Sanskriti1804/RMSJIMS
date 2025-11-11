package com.example.rmsjims.ui.screens.assistant

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.example.rmsjims.data.model.PropertyRequest
import com.example.rmsjims.data.model.PropertyRequestGroup
import com.example.rmsjims.data.model.PropertyRequestProvider
import com.example.rmsjims.data.model.RequestPriority
import com.example.rmsjims.data.model.RequestStatus
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppSearchBar
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomSmallLabel
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.chipColor
import com.example.rmsjims.ui.theme.labelColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun ResourceManagementScreen(
    navController: NavHostController
) {
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
            )
        )
    }

    val propertyOptions = remember {
        listOf(
            "All Properties",
            "Main Campus",
            "North Wing",
            "South Wing",
            "Annex Facility",
            "Innovation Hub"
        )
    }
    var propertyExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedProperty by rememberSaveable { mutableStateOf(propertyOptions.first()) }
    val savedFilters = remember { mutableStateListOf<FilterTag>() }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val propertyRequests = remember { PropertyRequestProvider.sampleRequests() }
    val sections = remember(resources, propertyRequests) { buildResourceSections(resources, propertyRequests) }
    val sectionStates = remember(sections) {
        mutableStateMapOf<String, ResourceSectionState>().apply {
            sections.forEach { section ->
                val initialTab = section.tabs.firstOrNull()
                val initialSub = initialTab?.subTabs?.firstOrNull()
                put(section.title, ResourceSectionState(selectedTab = initialTab, selectedSubTab = initialSub))
            }
        }
    }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Resource Management",
                onNavigationClick = { navController.popBackStack() }
            )
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
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
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp))
                ) {
                    FilterHeaderSection(
                        propertyOptions = propertyOptions,
                        selectedProperty = selectedProperty,
                        propertyExpanded = propertyExpanded,
                        savedFilters = savedFilters,
                        onPropertyExpanded = { propertyExpanded = it },
                        onPropertySelected = { selectedProperty = it },
                        onRemoveSavedFilter = { filter -> savedFilters.remove(filter) }
                    )
                    AppSearchBar(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        placeholder = "Search departments, resources, or logs..."
                    )
                }
            }

            item {
                ResourceSummaryRow(resources = resources)
            }

            sections.forEach { section ->
                item(key = section.title) {
                    val state = sectionStates[section.title] ?: ResourceSectionState()
                    val isSectionPinned = state.selectedSubTab != null || savedFilters.any { it.section == section.title }
                    val selectedTab = state.selectedTab ?: section.tabs.firstOrNull()
                    val badgeCount = state.selectedSubTab?.count ?: selectedTab?.count ?: section.totalCount

                    Column(
                        verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp))
                    ) {
                        SectionDivider(
                            title = section.title,
                            badgeCount = badgeCount,
                            isHighlighted = isSectionPinned
                        )
                        SectionTabRow(
                            tabs = section.tabs,
                            selectedTab = selectedTab,
                            onTabSelected = { tab ->
                                val currentState = sectionStates[section.title] ?: ResourceSectionState()
                                val retainedSub = currentState.selectedSubTab?.takeIf { sub ->
                                    tab.subTabs.any { it.label == sub.label }
                                }
                                val resolvedSub = retainedSub ?: tab.subTabs.firstOrNull()
                                if (retainedSub == null) {
                                    savedFilters.removeWhere { it.section == section.title }
                                }
                                sectionStates[section.title] = currentState.copy(
                                    selectedTab = tab,
                                    selectedSubTab = resolvedSub
                                )
                            }
                        )
                        selectedTab?.subTabs?.takeIf { it.isNotEmpty() }?.let { subTabs ->
                            SectionSubTabRow(
                                subTabs = subTabs,
                                selectedSubTab = state.selectedSubTab,
                                onSubTabSelected = { subTab ->
                                    val currentState = sectionStates[section.title] ?: ResourceSectionState()
                                    sectionStates[section.title] = currentState.copy(selectedSubTab = subTab)
                                    savedFilters.updateFilter(section.title, subTab)
                                }
                            )
                        }
                        val propertyRequestItems = state.selectedSubTab?.propertyRequests?.takeIf { it.isNotEmpty() }
                        if (propertyRequestItems != null) {
                            PropertyRequestsContent(
                                requests = propertyRequestItems,
                                showPriorityTag = state.selectedSubTab?.showPriorityTag == true,
                                showQuickToggle = state.selectedSubTab?.showQuickToggle == true,
                                showVerificationAction = state.selectedSubTab?.showVerificationAction == true,
                                navController = navController
                            )
                        } else {
                            val contentItems = state.selectedSubTab?.content?.takeIf { it.isNotEmpty() }
                                ?: selectedTab?.content
                            ResourceSectionContent(
                                items = contentItems.orEmpty()
                            )
                        }
                    }
                }
            }

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
            ) {
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
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
            ) {
                AppButton(
                    buttonText = "View Details",
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )
                AppButton(
                    buttonText = "Manage",
                    onClick = { },
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

// Placeholder data class
data class Resource(
    val name: String,
    val type: String,
    val capacity: String,
    val currentUsage: String,
    val status: String,
    val location: String
)

@Preview(showBackground = true)
@Composable
fun ResourceManagementScreenPreview() {
    val navController = rememberNavController()
    ResourceManagementScreen(navController = navController)
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
private fun FilterHeaderSection(
    propertyOptions: List<String>,
    selectedProperty: String,
    propertyExpanded: Boolean,
    savedFilters: List<FilterTag>,
    onPropertyExpanded: (Boolean) -> Unit,
    onPropertySelected: (String) -> Unit,
    onRemoveSavedFilter: (FilterTag) -> Unit
) {
    val chipSpacing = ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 14.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(chipSpacing),
        verticalAlignment = Alignment.CenterVertically
    ) {
        PropertySelectorChip(
            selectedProperty = selectedProperty,
            expanded = propertyExpanded,
            propertyOptions = propertyOptions,
            onExpandedChange = onPropertyExpanded,
            onPropertySelected = onPropertySelected
        )

        savedFilters.forEach { filter ->
            SavedFilterChip(
                filter = filter,
                onRemove = onRemoveSavedFilter
            )
        }
    }
}

@Composable
private fun PropertySelectorChip(
    selectedProperty: String,
    expanded: Boolean,
    propertyOptions: List<String>,
    onExpandedChange: (Boolean) -> Unit,
    onPropertySelected: (String) -> Unit
) {
    Box {
        SelectableChip(
            label = selectedProperty,
            selected = true,
            onClick = { onExpandedChange(true) },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Select property",
                    tint = primaryColor
                )
            }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
            modifier = Modifier.background(color = whiteColor)
        ) {
            propertyOptions.forEach { property ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = property,
                            color = if (property == selectedProperty) primaryColor else onSurfaceColor
                        )
                    },
                    onClick = {
                        onPropertySelected(property)
                        onExpandedChange(false)
                    }
                )
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

private fun buildResourceSections(
    resources: List<Resource>,
    propertyRequests: PropertyRequestGroup
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
            title = "Properties",
            tabs = listOf(
                ResourceSectionTab(
                    label = "Approvals",
                    count = propertyRequests.pendingApprovals.size + propertyRequests.newRequests.size + propertyRequests.priorityRequests.size + propertyRequests.flaggedRequests.size,
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "Pending Approvals",
                            count = propertyRequests.pendingApprovals.size,
                            propertyRequests = propertyRequests.pendingApprovals
                        ),
                        ResourceSectionSubTab(
                            label = "New Requests",
                            count = propertyRequests.newRequests.size,
                            propertyRequests = propertyRequests.newRequests
                        ),
                        ResourceSectionSubTab(
                            label = "Priority Requests",
                            count = propertyRequests.priorityRequests.size,
                            propertyRequests = propertyRequests.priorityRequests,
                            showPriorityTag = true,
                            showQuickToggle = true
                        ),
                        ResourceSectionSubTab(
                            label = "Flagged Requests",
                            count = propertyRequests.flaggedRequests.size,
                            propertyRequests = propertyRequests.flaggedRequests,
                            showVerificationAction = true
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Department View",
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
                            title = "Project Studio",
                            subtitle = "Capstone planning and design hub",
                            meta = "Extended booking hours"
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
                                    title = "Seminar Hall",
                                    subtitle = "Weekly industry interaction series",
                                    meta = "Next session: Friday 3 PM"
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
                                    title = "Cloud Sandbox",
                                    subtitle = "Sandbox accounts for student experiments",
                                    meta = "12 active deployments"
                                )
                            )
                        ),
                        ResourceSectionSubTab(
                            label = "Integrated",
                            count = 2,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Innovation Garage",
                                    subtitle = "Interdisciplinary build space",
                                    meta = "3 prototypes under review"
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
                            title = "Case Library",
                            subtitle = "Digitized repository of case studies",
                            meta = "42 curated references"
                        )
                    ),
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "MBA",
                            count = 3,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Leadership Forum",
                                    subtitle = "Experiential workshops for cohort leads",
                                    meta = "Seat limit: 20"
                                )
                            )
                        ),
                        ResourceSectionSubTab(
                            label = "BBA",
                            count = 2,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Analytics Sandbox",
                                    subtitle = "Power BI and Tableau workbench",
                                    meta = "Updated dashboards shared"
                                )
                            )
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Sciences",
                    count = 7,
                    content = listOf(
                        ResourceContentItem(
                            title = "Bio Lab Suite",
                            subtitle = "Molecular analysis and wet lab resources",
                            meta = "Sterilization scheduled nightly"
                        ),
                        ResourceContentItem(
                            title = "Physics Workshop",
                            subtitle = "Precision tools for instrumentation",
                            meta = "4 work orders pending"
                        )
                    ),
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "Biotech",
                            count = 3,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Culture Room",
                                    subtitle = "Controlled environment for experiments",
                                    meta = "2 incubators free"
                                )
                            )
                        ),
                        ResourceSectionSubTab(
                            label = "Physics",
                            count = 2,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Optics Bench",
                                    subtitle = "Laser alignment & calibration kit",
                                    meta = "Calibration due in 2 days"
                                )
                            )
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
                            title = "Robotics Arena",
                            subtitle = "Modular rigs for automation projects",
                            meta = "2 rigs under maintenance"
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
                            title = "VR Headsets",
                            subtitle = "Immersive training kits",
                            meta = "4 checked out"
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
                            title = "Conference Rooms",
                            subtitle = "AV-enabled meeting spaces",
                            meta = "Peak usage: 2-4 PM"
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Status Overview",
            tabs = listOf(
                ResourceSectionTab(
                    label = "Available",
                    count = 14,
                    content = listOf(
                        ResourceContentItem(
                            title = "Innovation Pod 02",
                            subtitle = "Configured for quick huddles",
                            meta = "Open until 8 PM"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "In Use",
                    count = 9,
                    content = listOf(
                        ResourceContentItem(
                            title = "Composite Lab",
                            subtitle = "Live booking by Mechanical Engineering",
                            meta = "Ends at 16:30"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Under Maintenance",
                    count = 3,
                    content = listOf(
                        ResourceContentItem(
                            title = "Spectrometer",
                            subtitle = "Awaiting calibration vendor",
                            meta = "ETA: Thursday"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Reserved",
                    count = 6,
                    content = listOf(
                        ResourceContentItem(
                            title = "Board Room",
                            subtitle = "Reserved for placement briefing",
                            meta = "Tomorrow 10 AM"
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Approvals",
            tabs = listOf(
                ResourceSectionTab(
                    label = "Pending Approvals",
                    count = 7,
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "New Requests",
                            count = 3,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Data Lab Extension",
                                    subtitle = "Requested by MCA Batch 2024",
                                    meta = "Priority: Medium"
                                )
                            )
                        ),
                        ResourceSectionSubTab(
                            label = "Priority Requests",
                            count = 2,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Server Upgrade",
                                    subtitle = "High load forecast for exams",
                                    meta = "Approval needed today"
                                )
                            )
                        ),
                        ResourceSectionSubTab(
                            label = "Flagged Requests",
                            count = 2,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Chemical Disposal",
                                    subtitle = "Requires compliance checklist",
                                    meta = "Pending safety clearance"
                                )
                            )
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Approved Usages",
                    count = 9,
                    subTabs = listOf(
                        ResourceSectionSubTab(
                            label = "Upcoming Usage",
                            count = 4,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Annual Tech Fair",
                                    subtitle = "Logistics plan approved",
                                    meta = "Setup starts Monday"
                                )
                            )
                        ),
                        ResourceSectionSubTab(
                            label = "In Progress",
                            count = 3,
                            content = listOf(
                                ResourceContentItem(
                                    title = "AI Hackathon",
                                    subtitle = "Labs occupied round-the-clock",
                                    meta = "Support: 2 technicians"
                                )
                            )
                        ),
                        ResourceSectionSubTab(
                            label = "Completed",
                            count = 2,
                            content = listOf(
                                ResourceContentItem(
                                    title = "Faculty Workshop",
                                    subtitle = "Post-event audit ready",
                                    meta = "Feedback: 4.6 / 5"
                                )
                            )
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Rejected Requests",
                    count = 3,
                    content = listOf(
                        ResourceContentItem(
                            title = "Overnight Auditorium Use",
                            subtitle = "Rejected due to maintenance window",
                            meta = "Alternate slot suggested"
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Urgency / Priority",
            tabs = listOf(
                ResourceSectionTab(
                    label = "High Priority",
                    count = 5,
                    content = listOf(
                        ResourceContentItem(
                            title = "Critical Server Patch",
                            subtitle = "Security fix rollout pending",
                            meta = "Action in next 4 hours"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Medium / Low",
                    count = 11,
                    content = listOf(
                        ResourceContentItem(
                            title = "Furniture Refresh",
                            subtitle = "Ergonomic chairs for labs",
                            meta = "Scheduled next week"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Critical",
                    count = 2,
                    content = listOf(
                        ResourceContentItem(
                            title = "Power Back-up Audit",
                            subtitle = "Generator load tests pending",
                            meta = "Block access until cleared"
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "History & Logs",
            tabs = listOf(
                ResourceSectionTab(
                    label = "Usage Log",
                    count = 24,
                    content = listOf(
                        ResourceContentItem(
                            title = "Lab Space A-101",
                            subtitle = "Booked by CSE Dept",
                            meta = "Logged: 11:30 AM"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "User Activity",
                    count = 16,
                    content = listOf(
                        ResourceContentItem(
                            title = "Facility Manager",
                            subtitle = "Approved 3 requests",
                            meta = "Last login 09:12 AM"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "Machine History",
                    count = 12,
                    content = listOf(
                        ResourceContentItem(
                            title = "CNC Router",
                            subtitle = "Runtime stats updated",
                            meta = "Last service 28 days ago"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "System Logs",
                    count = 8,
                    content = listOf(
                        ResourceContentItem(
                            title = "Integration Sync",
                            subtitle = "Third-party inventory updated",
                            meta = "Status: Success"
                        )
                    )
                )
            )
        ),
        ResourceSection(
            title = "Recently Added",
            tabs = listOf(
                ResourceSectionTab(
                    label = "New Machines",
                    count = recentlyAddedItems.count { it.title.contains("Lab", ignoreCase = true) || it.subtitle.contains("Equipment", ignoreCase = true) },
                    content = recentlyAddedItems.take(2)
                ),
                ResourceSectionTab(
                    label = "New Users",
                    count = 4,
                    content = listOf(
                        ResourceContentItem(
                            title = "Workshop Technicians",
                            subtitle = "4 technicians onboarded",
                            meta = "Clearance complete"
                        )
                    )
                ),
                ResourceSectionTab(
                    label = "New Departments",
                    count = 1,
                    content = listOf(
                        ResourceContentItem(
                            title = "Sustainability Cell",
                            subtitle = "Green initiatives task force",
                            meta = "Workspace allocated"
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
    val showPriorityTag: Boolean = false,
    val showQuickToggle: Boolean = false,
    val showVerificationAction: Boolean = false
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
