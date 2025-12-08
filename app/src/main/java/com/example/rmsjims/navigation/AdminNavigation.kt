package com.example.rmsjims.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rmsjims.ui.screens.staff.ProdDescScreen
import com.example.rmsjims.ui.screens.staff.ProjectInfoScreen
import com.example.rmsjims.ui.screens.staff.RaiseTicketScreen
import com.example.rmsjims.ui.screens.assistant.TicketScreen
import com.example.rmsjims.viewmodel.UserSessionViewModel
import org.koin.androidx.compose.koinViewModel
import com.example.rmsjims.ui.screens.admin.AdminDashboardScreen
import com.example.rmsjims.ui.screens.admin.AdminBookingManagementScreen
import com.example.rmsjims.ui.screens.admin.EquipmentManagementScreen
import com.example.rmsjims.ui.screens.admin.SystemSettingScreen
import com.example.rmsjims.ui.screens.admin.ResourceManagementScreen
import com.example.rmsjims.ui.screens.admin.UserDetailScreen
import com.example.rmsjims.ui.screens.admin.UserManagementScreen
import com.example.rmsjims.ui.screens.assistant.MaintenanceDetailScreen
import com.example.rmsjims.ui.screens.assistant.MachineStatusScreen
import com.example.rmsjims.ui.screens.assistant.TicketManagementScreen
import com.example.rmsjims.ui.screens.staff.HomeScreen
import com.example.rmsjims.ui.screens.staff.EquipmentScreen
import com.example.rmsjims.ui.screens.staff.ProfileScreen
import com.example.rmsjims.ui.screens.staff.SavedCollectionScreen
import com.example.rmsjims.ui.screens.assistant.NewEquipmentScreen
import com.example.rmsjims.ui.screens.assistant.RequestDetailsScreen
import com.example.rmsjims.ui.screens.shared.RoleSelectionScreen
import com.example.rmsjims.ui.screens.data.DepartmentDetailsScreen

@Composable
fun AdminModuleApp(parentNavController: NavHostController? = null) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.HomeScreen.route
    ) {
        // Bottom navigation screens
        composable(Screen.HomeScreen.route) {
            HomeScreen(
                navController = navController,
                parentNavController = parentNavController
            )
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
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController = navController)
        }
        composable(Screen.SavedCollectionScreen.route) {
            SavedCollectionScreen(navController = navController)
        }
        
        // Admin-specific screens
        composable(Screen.AdminDashboardScreen.route) {
            AdminDashboardScreen(navController = navController)
        }
        composable(Screen.AdminBookingsScreen.route) {
            AdminBookingManagementScreen(navController = navController)
        }
        composable(Screen.EquipmentManagementScreen.route) {
            EquipmentManagementScreen(navController = navController)
        }
        composable(Screen.SystemSettingScreen.route) {
            SystemSettingScreen(navController = navController)
        }
        composable(Screen.ResourceManagementScreen.route) {
            ResourceManagementScreen(navController = navController)
        }
        composable(Screen.UserManagementScreen.route) {
            UserManagementScreen(navController = navController)
        }
        composable(
            Screen.UserDetailScreen.route,
            arguments = listOf(navArgument("userId") { type = NavType.StringType })
        ) { backStackEntry ->
            val userId = backStackEntry.arguments?.getString("userId") ?: ""
            UserDetailScreen(navController = navController)
        }
        composable(Screen.TicketManagementScreen.route) {
            TicketManagementScreen(navController = navController)
        }
        composable(Screen.MachineStatusScreen.route) {
            MachineStatusScreen(navController = navController)
        }
        composable(
            Screen.MaintenanceDetailScreen.route,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId") ?: ""
            MaintenanceDetailScreen(requestId = requestId, navController = navController)
        }
        // Shared screens - merged directly into AdminNavGraph
        composable(
            Screen.ProductDescriptionScreen.route,
            arguments = listOf(navArgument("itemId") { type = NavType.IntType; defaultValue = 0 })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getInt("itemId") ?: 0
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            ProdDescScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                itemId = if (itemId == 0) null else itemId
            )
        }
        composable(Screen.ProductDescriptionEditScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            ProdDescScreen(navController = navController, sessionViewModel = sessionViewModel)
        }
        composable(
            Screen.ProjectInfoScreen.route,
            arguments = listOf(navArgument("equipmentId") { type = NavType.IntType; defaultValue = 0 })
        ) { backStackEntry ->
            val equipmentId = backStackEntry.arguments?.getInt("equipmentId") ?: 0
            ProjectInfoScreen(
                navController = navController,
                equipmentId = if (equipmentId == 0) null else equipmentId
            )
        }
        composable(Screen.RaiseTicketScreen.route) {
            RaiseTicketScreen(navController = navController)
        }
        composable(Screen.TicketScreen.route) {
            TicketScreen()
        }
        composable(Screen.NewEquipmentScreen.route) {
            NewEquipmentScreen(navController = navController)
        }
        composable(
            Screen.RequestDetailsScreen.route,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId") ?: ""
            RequestDetailsScreen(requestId = requestId, navController = navController)
        }
        composable(Screen.RoleSelectionScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleSelectionScreen(
                navController = navController,
                sessionViewModel = sessionViewModel,
                parentNavController = parentNavController
            )
        }
        composable(
            Screen.DepartmentDetailsScreen.route,
            arguments = listOf(navArgument("departmentId") { type = NavType.IntType })
        ) { backStackEntry ->
            val departmentId = backStackEntry.arguments?.getInt("departmentId") ?: 0
            DepartmentDetailsScreen(
                departmentId = departmentId,
                navController = navController
            )
        }
    }
}
