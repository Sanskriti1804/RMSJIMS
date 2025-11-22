package com.example.rmsjims.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rmsjims.ui.screens.staff.ProdDescScreen
import com.example.rmsjims.ui.screens.staff.ProjectInfoScreen
import com.example.rmsjims.ui.screens.staff.RaiseTicketScreen
import com.example.rmsjims.ui.screens.assistant.TicketScreen
import com.example.rmsjims.ui.screens.admin.AdminDashboardScreen
import com.example.rmsjims.ui.screens.admin.EquipmentAssignmentScreen
import com.example.rmsjims.ui.screens.admin.SystemSettingScreen
import com.example.rmsjims.ui.screens.admin.UserDetailScreen
import com.example.rmsjims.ui.screens.admin.UserManagementScreen
import com.example.rmsjims.ui.screens.assistant.MaintenanceApprovalScreen
import com.example.rmsjims.ui.screens.assistant.MaintenanceDetailScreen
import com.example.rmsjims.ui.screens.assistant.MachineDetailScreen
import com.example.rmsjims.ui.screens.assistant.MachineStatusScreen
import com.example.rmsjims.ui.screens.assistant.ResourceManagementScreen
import com.example.rmsjims.ui.screens.assistant.TicketManagementScreen
import com.example.rmsjims.ui.screens.assistant.UsageApprovalScreen

@Composable
fun AdminModuleApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(Screen.AdminDashboardScreen.route) {
            AdminDashboardScreen(navController = navController)
        }
        composable(Screen.EquipmentAssignmentScreen.route) {
            EquipmentAssignmentScreen(navController = navController)
        }
        composable(Screen.SystemSettingScreen.route) {
            SystemSettingScreen(navController = navController)
        }
        composable(Screen.UserManagementScreen.route) {
            UserManagementScreen(navController = navController)
        }
        composable(Screen.UserDetailScreen.route) {
            UserDetailScreen(navController = navController)
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
        composable(Screen.RaiseTicketScreen.route) {
            RaiseTicketScreen(navController = navController)
        }
        composable(Screen.TicketScreen.route) {
            TicketScreen()
        }
        composable(Screen.ProjectInfoScreen.route) {
            ProjectInfoScreen(navController = navController)
        }
        composable(Screen.ProductDescriptionScreen.route) {
            ProdDescScreen(navController = navController)
        }
    }
}
