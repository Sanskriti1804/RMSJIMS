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
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp
import com.example.labinventory.viewmodel.FilterSortViewModel


@Composable
fun FilterSortBottomSheet(viewModel: FilterSortViewModel) {
    val tabs by viewModel.tabs.collectAsState()
    val filters by viewModel.filters.collectAsState()
    val sortOptions by viewModel.sort.collectAsState()

    Column(modifier = Modifier.fillMaxWidth().padding(pxToDp(16))) {
        Spacer(modifier = Modifier.height(pxToDp(18)))

        FilterSortTabs(tabs = tabs, onTabSelected = viewModel::selectTab)

        Spacer(modifier = Modifier.height(pxToDp(14)))

        // Content based on selected tab
        val selectedTab = tabs.find { it.isSelected }?.tab ?: FilterTab.Filter
        when (selectedTab) {
            FilterTab.Filter -> {
                filters.forEach { group ->
                    Text(
                        text = group.section.name.lowercase().replaceFirstChar(Char::titlecase),
                        color = selectedChipTextColor,
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
                        modifier = Modifier.padding(vertical = pxToDp(14))
                    )
                    ChipGroup(
                        chips = group.chips,
                        onChipToggle = { label -> viewModel.toggleFilterChip(group.section, label) }
                    )
                    Spacer(Modifier.height(pxToDp(16)))
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

@Composable
fun FilterSortTabs(
    tabs: List<TabGroup>,
    onTabSelected: (FilterTab) -> Unit
) {
    val selectedIndex = tabs.indexOfFirst { it.isSelected }

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        val transition = updateTransition(targetState = selectedIndex, label = "Tab indicator transition")
        val currentTabPosition by transition.animateDp(
            transitionSpec = { tween(durationMillis = 250) },
            label = "Indicator offset"
        ) { index ->
            tabPositions.getOrNull(index)?.left ?: 0.dp
        }

        Box(
            Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.BottomStart)
                .padding(start = pxToDp(16) , end = pxToDp(16))
                .offset(x = currentTabPosition)
                .width(tabPositions.getOrNull(selectedIndex)?.width ?: 0.dp)
                .height(pxToDp(1))
                .background(darkTextColor, shape = RoundedCornerShape(pxToDp(2)))
        )
    }

    TabRow(
        selectedTabIndex = selectedIndex.coerceAtLeast(0),
        indicator = indicator,
        containerColor = whiteColor,
        contentColor = categoryIconColor,
        divider = {}
    ) {
        tabs.forEachIndexed { index, tab ->
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
        horizontalArrangement = Arrangement.spacedBy(pxToDp(12)),
        verticalArrangement = Arrangement.spacedBy(pxToDp(12))
    ) {
        chips.forEach { chip ->
            FilterChipItem(chip = chip, onClick = { onChipToggle(chip.label) })
        }
    }
}

@Composable
fun FilterChipItem(chip: FilterChip, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(pxToDp(4)),
        color = if (chip.isSelected) selectedchipColor else Color.Transparent,
        border = BorderStroke(pxToDp(1), if (chip.isSelected) highlightColor else chipColor),
        modifier = Modifier.clickable { onClick() }
    ) {
        Text(
            text = chip.label,
            color = selectedChipTextColor,
            modifier = Modifier.padding(horizontal = pxToDp(9) , vertical = pxToDp(6) ),
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
    Column(
//        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        options.forEachIndexed { index, option ->
            Text(
                text = option.label,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onOptionSelected(option.label) }
                    .padding(vertical = pxToDp(26), horizontal = pxToDp(19)),
                color = if (option.isSelected) highlightColor else selectedChipTextColor,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.font_alliance_regular_two))
            )

            if (index != options.lastIndex) {
                Divider(modifier = Modifier.padding(horizontal = pxToDp(16)))
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FilterSortTabsPreview() {
//    val tabs = listOf(
//        TabGroup(FilterTab.Filter, true),
//        TabGroup(FilterTab.Sort_by, false)
//    )
//    var selectedTab by remember { mutableStateOf(FilterTab.Filter) }
//    FilterSortTabs(
//        tabs = tabs.map { it.copy(isSelected = it.tab == selectedTab) },
//        onTabSelected = { tab -> selectedTab = tab }
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ChipGroupPreview() {
//    val chips = remember {
//        mutableStateListOf(
//            FilterChip("Location A", true),
//            FilterChip("Location B", false),
//            FilterChip("Location C", false),
//            FilterChip("Type X", true),
//            FilterChip("Type Y", false)
//        )
//    }
//    ChipGroup(
//        chips = chips,
//        onChipToggle = { label ->
//            val index = chips.indexOfFirst { it.label == label }
//            if (index != -1) chips[index] = chips[index].copy(isSelected = !chips[index].isSelected)
//        })
//}
//
//@Preview(showBackground = true)
//@Composable
//fun FilterChipItemPreview() {
//    FilterChipItem(chip = FilterChip("Sample Chip", true), onClick = {})
//    FilterChipItem(chip = FilterChip("Another Chip", false), onClick = {})
//}
//
@Preview(showBackground = true)
@Composable
fun SortListPreview() {
    val options = remember {
        mutableStateListOf(
            SortOption("Name (A-Z)", true),
            SortOption("Name (Z-A)", false),
            SortOption("Date Added (Newest)", false)
        )
    }
    SortList(
        options = options,
        onOptionSelected = { label ->
            options.replaceAll { it.copy(isSelected = it.label == label) }
        }
    )
}
