package com.example.labinventory.navigation

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.example.labinventory.R

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
