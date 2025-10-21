package com.example.rmsjims.navigation

import com.example.rmsjims.R

data class BottomNavScreens (
    val route: String,
    val iconResId: Int,
    val navIconDesc : String = "Navigation icon",
    val label: String,
    val hasNews : Boolean = false,
    val badgeCount : Int? = null,
    val selected : Boolean = false,
)

val bottomNavItems = listOf(
    BottomNavScreens(
        route = Screen.HomeScreen.route,
        iconResId = R.drawable.nav_ic_home,
        label = "Home"
    ),
    BottomNavScreens(
        route = Screen.EquipmentScreen.route,
        iconResId = R.drawable.nav_ic_equip,
        label = "Equipments"
    ),
    BottomNavScreens(
        route = Screen.BookingsScreen.route,
        iconResId = R.drawable.nav_ic_bookings,
        label = "Bookings"
    ),
    )
