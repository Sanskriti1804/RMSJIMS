package com.example.rmsjims.assistant.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.screens.assisstant.AssistantScreen
import com.example.rmsjims.ui.screens.assisstant.MachineDetailScreen
import com.example.rmsjims.ui.screens.assisstant.MachineStatusScreen
import com.example.rmsjims.ui.screens.assisstant.MaintenanceApprovalScreen
import com.example.rmsjims.ui.screens.assisstant.MaintenanceDetailScreen
import com.example.rmsjims.ui.screens.assisstant.ResourceManagementScreen
import com.example.rmsjims.ui.screens.assisstant.TicketManagementScreen
import com.example.rmsjims.ui.screens.assisstant.UsageApprovalScreen

@Composable
fun AssistantModuleApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.AssistantScreen.route
    ) {
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
        composable(Screen.ResourceManagementScreen.route) {
            ResourceManagementScreen(navController = navController)
        }
        composable(Screen.TicketManagementScreen.route) {
            TicketManagementScreen(navController = navController)
        }
    }
}
