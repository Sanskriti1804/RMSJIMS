package com.example.rmsjims.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rmsjims.ui.screens.assistant.AssistantScreen
import com.example.rmsjims.ui.screens.assistant.MachineDetailScreen
import com.example.rmsjims.ui.screens.assistant.MachineStatusScreen
import com.example.rmsjims.ui.screens.assistant.MaintenanceApprovalScreen
import com.example.rmsjims.ui.screens.assistant.MaintenanceDetailScreen
import com.example.rmsjims.ui.screens.assistant.TicketManagementScreen
import com.example.rmsjims.ui.screens.assistant.UsageApprovalScreen
import com.example.rmsjims.ui.screens.staff.ProdDescScreen
import com.example.rmsjims.ui.screens.staff.ProjectInfoScreen
import com.example.rmsjims.ui.screens.staff.RaiseTicketScreen
import com.example.rmsjims.ui.screens.assistant.TicketScreen
import com.example.rmsjims.viewmodel.UserSessionViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun AssistantModuleApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.ResourceManagementScreen.route
    ) {
        // Assistant-specific screens
        composable(Screen.AssistantScreen.route) {
            AssistantScreen(navController = navController)
        }
        composable(Screen.MachineStatusScreen.route) {
            MachineStatusScreen(navController = navController)
        }
        composable(
            Screen.MachineDetailScreen.route,
            arguments = listOf(navArgument("machineId") { type = NavType.StringType })
        ) { backStackEntry ->
            val machineId = backStackEntry.arguments?.getString("machineId") ?: ""
            MachineDetailScreen(machineId = machineId, navController = navController)
        }
        composable(Screen.MaintenanceApprovalScreen.route) {
            MaintenanceApprovalScreen(navController = navController)
        }
        composable(
            Screen.MaintenanceDetailScreen.route,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId") ?: ""
            MaintenanceDetailScreen(requestId = requestId, navController = navController)
        }
        composable(Screen.UsageApprovalScreen.route) {
            UsageApprovalScreen(navController = navController)
        }
        composable(Screen.TicketManagementScreen.route) {
            TicketManagementScreen(navController = navController)
        }

        // Shared screens - merged directly into AssistantNavGraph
        composable(Screen.ProductDescriptionScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            ProdDescScreen(navController = navController, sessionViewModel = sessionViewModel)
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
}
