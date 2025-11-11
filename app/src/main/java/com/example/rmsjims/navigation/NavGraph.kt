package com.example.rmsjims.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rmsjims.ui.screens.staff.BookingScreen
import com.example.rmsjims.ui.screens.staff.CalendarScreen
import com.example.rmsjims.ui.screens.staff.EquipmentScreen
import com.example.rmsjims.ui.screens.staff.HomeScreen
import com.example.rmsjims.ui.screens.shared.AboutAppScreen
import com.example.rmsjims.ui.screens.shared.LoginScreen
import com.example.rmsjims.ui.screens.staff.ProdDescScreen
import com.example.rmsjims.ui.screens.staff.ProjectInfoScreen
import com.example.rmsjims.ui.screens.shared.PermissionsOverviewScreen
import com.example.rmsjims.ui.screens.shared.RoleOverviewScreen
import com.example.rmsjims.ui.screens.shared.RoleSelectionScreen
import com.example.rmsjims.ui.screens.staff.ProfileScreen
import com.example.rmsjims.ui.screens.staff.RaiseTicketScreen
import com.example.rmsjims.ui.screens.staff.TicketScreen
import com.example.rmsjims.ui.screens.admin.AdminDashboardScreen
import com.example.rmsjims.ui.screens.admin.EquipmentAssignmentScreen
import com.example.rmsjims.ui.screens.admin.SystemSettingScreen
import com.example.rmsjims.ui.screens.admin.UserManagementScreen
import com.example.rmsjims.ui.screens.assistant.AssistantScreen
import com.example.rmsjims.ui.screens.assistant.MachineDetailScreen
import com.example.rmsjims.ui.screens.assistant.MachineStatusScreen
import com.example.rmsjims.ui.screens.assistant.MaintenanceApprovalScreen
import com.example.rmsjims.ui.screens.assistant.MaintenanceDetailScreen
import com.example.rmsjims.ui.screens.assistant.ResourceManagementScreen
import com.example.rmsjims.ui.screens.assistant.TicketManagementScreen
import com.example.rmsjims.ui.screens.assistant.UsageApprovalScreen
import com.example.rmsjims.ui.screens.assistant.RequestDetailsScreen
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.viewmodel.BookingScreenViewmodel
import com.example.rmsjims.viewmodel.CalendarViewModel
import com.example.rmsjims.viewmodel.FacilitiesViewModel
import com.example.rmsjims.viewmodel.FilterSortViewModel
import com.example.rmsjims.viewmodel.ItemsViewModel
import com.example.rmsjims.viewmodel.UserSessionViewModel
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(
    startDestination: String = Screen.AboutAppScreen.route
){
    val navController = rememberNavController()
    AppNavGraph(
        navController = navController,
        startDestination = startDestination
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(
    navController: NavHostController,
    startDestination: String = Screen.LoginScreen.route
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(Screen.AboutAppScreen.route) {
            AboutAppScreen(navController)
        }
        composable(Screen.RoleOverviewScreen.route) {
            RoleOverviewScreen(navController)
        }
        composable(
            Screen.PermissionsOverviewScreen.route,
            arguments = listOf(navArgument("selectedRole") {
                type = NavType.StringType
                defaultValue = UserRole.ASSISTANT.name
            })
        ) { backStackEntry ->
            val roleName = backStackEntry.arguments?.getString("selectedRole").orEmpty()
            val selectedRole = runCatching { UserRole.valueOf(roleName) }.getOrNull()
            PermissionsOverviewScreen(navController, selectedRole)
        }
        composable(Screen.RoleSelectionScreen.route) {
            RoleSelectionScreen(navController)
        }
        composable(Screen.HomeScreen.route) {
           HomeScreen(navController)
        }
        composable(
            Screen.EquipmentScreen.route,
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) {backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName") ?: "Equipments"
            val filterSortViewModel : FilterSortViewModel = koinViewModel()
            val itemViewModel: ItemsViewModel = koinViewModel()
            val facilitiesViewModel: FacilitiesViewModel = koinViewModel()

            EquipmentScreen(navController, filterSortViewModel, itemViewModel, facilitiesViewModel, categoryName)
        }
        composable(Screen.BookingsScreen.route) {
            val bookingViewModel : BookingScreenViewmodel = koinViewModel()
            BookingScreen( navController, bookingViewModel)
        }
        composable(Screen.CalendarScreen.route) {
            val calendarViewModel : CalendarViewModel = koinViewModel()
            val bookingViewModel : BookingScreenViewmodel = koinViewModel()
            CalendarScreen( navController,calendarViewModel, bookingViewModel)
        }
        composable(Screen.ProductDescriptionScreen.route) {
            val sessionViewModel : UserSessionViewModel = koinViewModel()
            ProdDescScreen(navController = navController, sessionViewModel = sessionViewModel)
        }
        composable(Screen.ProjectInfoScreen.route) {
            ProjectInfoScreen(navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(Screen.RaiseTicketScreen.route) {
            RaiseTicketScreen(navController)
        }
        composable(Screen.TicketScreen.route) {
            TicketScreen()
        }
        composable(Screen.ProfileScreen.route) {
            ProfileScreen(navController)
        }
        composable(Screen.AdminDashboardScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.ADMIN)
            ) {
                AdminDashboardScreen(navController)
            }
        }
        composable(Screen.EquipmentAssignmentScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.ADMIN)
            ) {
                EquipmentAssignmentScreen(navController)
            }
        }
        composable(Screen.SystemSettingScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.ADMIN)
            ) {
                SystemSettingScreen(navController)
            }
        }
        composable(Screen.UserManagementScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.ADMIN)
            ) {
                UserManagementScreen(navController)
            }
        }
        composable(Screen.AssistantScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.ASSISTANT)
            ) {
                AssistantScreen(navController)
            }
        }
        composable(Screen.MachineStatusScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.ASSISTANT, UserRole.STAFF)
            ) {
                MachineStatusScreen(navController)
            }
        }
        composable(
            Screen.MachineDetailScreen.route,
            arguments = listOf(navArgument("machineId") { type = NavType.StringType })
        ) { backStackEntry ->
            val machineId = backStackEntry.arguments?.getString("machineId") ?: ""
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.ASSISTANT, UserRole.STAFF)
            ) {
                MachineDetailScreen(machineId = machineId, navController = navController)
            }
        }
        composable(Screen.MaintenanceApprovalScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.STAFF)
            ) {
                MaintenanceApprovalScreen(navController)
            }
        }
        composable(
            Screen.MaintenanceDetailScreen.route,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId") ?: ""
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.STAFF)
            ) {
                MaintenanceDetailScreen(requestId = requestId, navController = navController)
            }
        }
        composable(Screen.ResourceManagementScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.ASSISTANT)
            ) {
                ResourceManagementScreen(navController)
            }
        }
        composable(Screen.TicketManagementScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.ASSISTANT, UserRole.STAFF)
            ) {
                TicketManagementScreen(navController)
            }
        }
        composable(Screen.UsageApprovalScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.STAFF)
            ) {
                UsageApprovalScreen(navController)
            }
        }
        composable(
            Screen.RequestDetailsScreen.route,
            arguments = listOf(navArgument("requestId") { type = NavType.StringType })
        ) { backStackEntry ->
            val requestId = backStackEntry.arguments?.getString("requestId") ?: ""
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleGuard(
                navController = navController,
                sessionRole = sessionViewModel.userRole,
                allowedRoles = setOf(UserRole.ASSISTANT, UserRole.STAFF, UserRole.ADMIN)
            ) {
                RequestDetailsScreen(
                    navController = navController,
                    requestId = requestId
                )
            }
        }

    }
}

@Composable
private fun RoleGuard(
    navController: NavHostController,
    sessionRole: UserRole,
    allowedRoles: Set<UserRole>,
    content: @Composable () -> Unit
) {
    LaunchedEffect(sessionRole) {
        if (sessionRole == UserRole.UNASSIGNED) {
            navController.navigate(Screen.RoleSelectionScreen.route) {
                popUpTo(Screen.RoleSelectionScreen.route) { inclusive = true }
            }
        } else if (!allowedRoles.contains(sessionRole)) {
            navController.navigate(Screen.RoleSelectionScreen.route) {
                popUpTo(Screen.RoleSelectionScreen.route) { inclusive = true }
            }
        }
    }

    if (allowedRoles.contains(sessionRole)) {
        content()
    }
}