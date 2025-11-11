package com.example.rmsjims.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.ui.screens.staff.ProdDescScreen
import com.example.rmsjims.ui.screens.staff.ProjectInfoScreen
import com.example.rmsjims.ui.screens.staff.RaiseTicketScreen
import com.example.rmsjims.ui.screens.staff.TicketScreen

enum class SharedRoute(val route: String) {
    ProductDetails(Screen.ProductDescriptionScreen.route),
    ProjectInfo(Screen.ProjectInfoScreen.route),
    RaiseTicket(Screen.RaiseTicketScreen.route),
    Tickets(Screen.TicketScreen.route)
}

fun NavGraphBuilder.addSharedScreens(navController: NavHostController) {
    composable(Screen.ProductDescriptionScreen.route) {
        ProdDescScreen(navController = navController)
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
}

@Composable
fun SharedModuleNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: SharedRoute = SharedRoute.ProductDetails
) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        addSharedScreens(navController)
    }
}
