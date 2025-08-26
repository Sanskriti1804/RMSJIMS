package com.example.labinventory.ui.screens

import androidx.annotation.Px
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.labinventory.R
import com.example.labinventory.data.model.FilterChip
import com.example.labinventory.data.model.FilterTab
import com.example.labinventory.data.model.SortOption
import com.example.labinventory.data.model.TabGroup
import com.example.labinventory.ui.components.AppButton
import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.theme.categoryIconColor
import com.example.labinventory.ui.theme.chipColor
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.selectedChipTextColor
import com.example.labinventory.ui.theme.selectedchipColor
import com.example.labinventory.ui.theme.sortDividerColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.ResponsiveLayout
import com.example.labinventory.util.pxToDp
import com.example.labinventory.viewmodel.FilterSortViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSortBottomSheet(
    viewModel: FilterSortViewModel = koinViewModel()
) {
    val tabs by viewModel.tabs.collectAsState()
    val filters by viewModel.filters.collectAsState()
    val sortOptions by viewModel.sort.collectAsState()

    val isSheetVisible by viewModel.isSheetVisible

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isSheetVisible) {
        if (isSheetVisible && !sheetState.isVisible) {
            sheetState.show()
        } else if (!isSheetVisible && sheetState.isVisible) {
            sheetState.hide()
        }
    }

    ModalBottomSheet(
        onDismissRequest = { viewModel.hideSheet() },
        sheetState = sheetState,
        shape = RoundedCornerShape(ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 16.dp)),
        containerColor = whiteColor,
        dragHandle = null,
        contentWindowInsets = { WindowInsets(0, 0, 0, 0) }
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(ResponsiveLayout.getHorizontalPadding())) {

            FilterSortTabs(tabs = tabs, onTabSelected = viewModel::selectTab)

            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(10.dp, 12.dp, 16.dp)))

            // Content based on selected tab
            val selectedTab = tabs.find { it.isSelected }?.tab ?: FilterTab.Filter
            when (selectedTab) {
                FilterTab.Filter -> {
                    filters.forEachIndexed { index, group ->
                        Text(
                            text = group.section.name.lowercase().replaceFirstChar(Char::titlecase),
                            color = selectedChipTextColor,
                            fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                            fontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
                            modifier = Modifier.padding(vertical = ResponsiveLayout.getResponsivePadding(14.dp, 16.dp, 20.dp))
                        )

                        ChipGroup(
                            chips = group.chips,
                            onChipToggle = { label -> viewModel.toggleFilterChip(group.section, label) }
                        )

                        // Divider after each section except last
                        if (index != filters.lastIndex) {
                            Divider(
                                modifier = Modifier.padding(top = 15.dp),
                                color = sortDividerColor
                            )
                        }
                    }
                }
                FilterTab.Sort_by -> {
                    SortList(
                        options = sortOptions,
                        onOptionSelected = viewModel::selectSortOption
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Bottom Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AppButton(
                    modifier = Modifier.weight(1f).padding(end = 8.dp),
                    buttonText = "Reset",
                    containerColor = Color.LightGray,
                    contentColor = Color.Black,
                    onClick = {
                        viewModel.resetFilters()
                        viewModel.selectSortOption("") // Deselect all
                    }
                )
                AppButton(
                    modifier = Modifier.weight(1f),
                    buttonText = "Apply Filter",
                    onClick = {
                        // TODO: trigger filtering logic or pop bottom sheet
                    }
                )
            }
        }
    }
}

@Composable
fun FilterSortTabs(
    tabs: List<TabGroup>,
    onTabSelected: (FilterTab) -> Unit
) {
    val selectedIndex = tabs.indexOfFirst { it.isSelected }

    ScrollableTabRow(
        selectedTabIndex = selectedIndex.coerceAtLeast(0),
        edgePadding = 0.dp, // so tabs start right at the left edge
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedIndex.coerceAtLeast(0)]),
                color = highlightColor
            )
        },
        containerColor = whiteColor,
        contentColor = categoryIconColor,
        divider = {},
        modifier = Modifier.padding(top = 25.dp)
    ) {
        tabs.forEach { tab ->
            Tab(
                selected = tab.isSelected,
                onClick = { onTabSelected(tab.tab) },
                selectedContentColor = highlightColor,
                unselectedContentColor = categoryIconColor,
                text = {
                    Text(
                        text = tab.tab.name.replace("_", " "),
                        fontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
                        fontSize = 20.sp
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipGroup(
    chips: List<FilterChip>,
    onChipToggle: (String) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(6.dp), // <-- updated
        verticalArrangement = Arrangement.spacedBy(12.dp)  // <-- updated
    ) {
        chips.forEach { chip ->
            FilterChipItem(chip = chip, onClick = { onChipToggle(chip.label) })
        }
    }
}

@Composable
fun FilterChipItem(
    chip: FilterChip,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(pxToDp(4)),
        color = if (chip.isSelected) selectedchipColor else Color.Transparent,
        border = BorderStroke(
            pxToDp(1),
            if (chip.isSelected) highlightColor else chipColor
        ),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = chip.label,
            color = selectedChipTextColor,
            modifier = Modifier.padding(
                horizontal = pxToDp(9),
                vertical = pxToDp(6)
            ),
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(R.font.font_alliance_regular_two))
        )
    }
}

@Composable
fun SortList(
    options: List<SortOption>,
    onOptionSelected: (String) -> Unit
) {
    Column {
        options.forEachIndexed { index, option ->
            Text(
                text = option.label,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOptionSelected(option.label) }
                    .padding(
                        vertical = pxToDp(26),
                        horizontal = pxToDp(19)
                    ),
                color = if (option.isSelected) highlightColor else selectedChipTextColor,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.font_alliance_regular_two))
            )
            if (index != options.lastIndex) {
                Divider(
                    thickness = 1.dp,
                    color = sortDividerColor
                )
            }
        }
    }
}
