package com.example.labinventory.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.labinventory.data.model.FilterChip
import com.example.labinventory.data.model.FilterGroup
import com.example.labinventory.data.model.FilterSection
import com.example.labinventory.data.model.FilterTab
import com.example.labinventory.data.model.SortOption
import com.example.labinventory.data.model.TabGroup
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.collections.listOf

class FilterSortViewModel : ViewModel(){

    private val _isSheetVisible = mutableStateOf(false)
    val isSheetVisible : MutableState<Boolean> = _isSheetVisible


    fun showSheet(){
        _isSheetVisible.value = true
    }

    fun hideSheet(){
        _isSheetVisible.value = false
    }

    private val _tabs = MutableStateFlow(
        listOf(
            TabGroup(FilterTab.Filter, true),
            TabGroup(FilterTab.Sort_by, false)
        )
    )

    val tabs = _tabs.asStateFlow()

    fun selectTab(tab: FilterTab) {
        _tabs.update { tabList ->
            tabList.map {
                if (it.tab == tab) it.copy(isSelected = true)
                else it.copy(isSelected = false)
            }
        }
    }

    private val _filters = MutableStateFlow(
        listOf(
            FilterGroup(
                FilterSection.LABS,
                listOf("All", "Ixd lab", "Design for future lab", "Ergonimics lab", "Immersive lab").map {
                FilterChip(it, false)
            }),
            FilterGroup(
                FilterSection.STUDIOS,
                listOf("All", "Metal Studios", "Wood Studio", "Plastic Studio", "Ceramic Studio", "Bamboo Studio", "Painting Studio", "Weaving Studio", "Clay Studio", "Metal Studio").map {
                    FilterChip(it, false)
                }
            ),
            FilterGroup(
                FilterSection.BRANCHES,
                listOf("All", "IXD", "ID", "Film $ Animation", "Mobilty", "CD").map {
                    FilterChip(it, false)
                }
            )
        )
    )

    val filters = _filters.asStateFlow()

    fun toggleFilterChip(section: FilterSection, label: String) {
        _filters.update { groupList ->
            groupList.map { group ->
                if (group.section == section) {
                    group.copy(chips = group.chips.map { chip ->
                        if (chip.label == label) chip.copy(isSelected = !chip.isSelected)
                        else chip
                    })
                } else group
            }
        }
    }

    fun resetFilters() {
        _filters.update { groupList ->
            groupList.map { group ->
                group.copy(chips = group.chips.map { it.copy(isSelected = false) })
            }
        }
    }

    val _sort = MutableStateFlow(
        listOf(
            SortOption("RELEVANCE", false),
            SortOption("ALPHABETICAL A-Z", false),
            SortOption("ALPHABETICAL Z-A", false),
            SortOption("SHARED OR RESERVED", false),
            SortOption("LONGEST AVAILABLE", false),
            SortOption("NEW TO OLD", false),
            SortOption("OLD TO NEW", false),
            SortOption("POPULARITY", false),
        )
    )

    val sort = _sort.asStateFlow()

    fun selectSortOption(label: String) {
        _sort.update { options ->
            options.map { it.copy(isSelected = it.label == label) }
        }
    }


}