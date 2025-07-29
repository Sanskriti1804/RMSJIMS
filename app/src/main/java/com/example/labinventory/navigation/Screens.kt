package com.example.labinventory.navigation

sealed class Screen(val route : String) {
    object HomeScreen : Screen("home")
    object EquipmentScreen : Screen("equipment")
    object BookingsScreen : Screen("bookings")
    object CalendarScreen : Screen("calendar")
    object ProjectInfoScreen : Screen("project_info")
    object FilterSortBottomSheet : Screen("filter")
    object ChatBottomSheet : Screen("chat")
    object ProductDescriptionScreen : Screen("profileDescription")
}