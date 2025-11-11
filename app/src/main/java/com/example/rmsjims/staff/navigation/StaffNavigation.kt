package com.example.rmsjims.staff.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.screens.BookingScreen
import com.example.rmsjims.ui.screens.CalendarScreen
import com.example.rmsjims.ui.screens.HomeScreen
import com.example.rmsjims.ui.screens.ProdDescScreen
import com.example.rmsjims.ui.screens.ProfileScreen
import com.example.rmsjims.ui.screens.ProjectInfoScreen
import com.example.rmsjims.ui.screens.RaiseTicketScreen
import com.example.rmsjims.ui.screens.TicketScreen
import com.example.rmsjims.ui.screens.EquipmentScreen
import com.example.rmsjims.viewmodel.BookingScreenViewmodel
import com.example.rmsjims.viewmodel.CalendarViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun StaffModuleApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        composable(Screen.HomeScreen.route) {
            HomeScreen(navController = navController)
        }
        composable(
            Screen.EquipmentScreen.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Equipments"
            EquipmentScreen(
                navController = navController,
                categoryName = categoryName
            )
        }
        composable(Screen.BookingsScreen.route) {
            BookingScreen(navController = navController)
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.ProjectInfoScreen.route) {
            ProjectInfoScreen(navController = navController)
        }
        composable(Screen.RaiseTicketScreen.route) {
            RaiseTicketScreen(navController = navController)
        }
        composable(Screen.TicketScreen.route) {
            TicketScreen()
        }
        composable(Screen.ProductDescriptionScreen.route) {
            ProdDescScreen(navController = navController)
        }
        composable(Screen.CalendarScreen.route) {
            val calendarViewModel: CalendarViewModel = koinViewModel()
            val bookingViewModel: BookingScreenViewmodel = koinViewModel()
            CalendarScreen(
                navController = navController,
                viewModel = calendarViewModel,
                bookingViewmodel = bookingViewModel
            )
        }
    }
}
