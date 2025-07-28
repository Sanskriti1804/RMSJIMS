package com.example.labinventory.data.model

data class FilterChip(
    val label : String,
    val isSelected : Boolean
)

enum class FilterSection{
    LABS, STUDIOS, BRANCHES
}

data class FilterGroup(
    val section: FilterSection,
    val chips : List<FilterChip>
)
data class SortOption(
    val label : String,
    val isSelected : Boolean
)
enum class FilterTab{
    Filter, Sort_by
}
data class TabGroup(
    val tab : FilterTab,
    val isSelected: Boolean
)

