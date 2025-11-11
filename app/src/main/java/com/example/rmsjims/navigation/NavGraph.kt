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
import com.example.rmsjims.ui.screens.BookingScreen
import com.example.rmsjims.ui.screens.CalendarScreen
import com.example.rmsjims.ui.screens.EmailVerificationScreen
import com.example.rmsjims.ui.screens.EquipmentScreen
import com.example.rmsjims.ui.screens.HomeScreen
import com.example.rmsjims.ui.screens.LoginScreen
import com.example.rmsjims.ui.screens.ProdDescScreen
import com.example.rmsjims.ui.screens.ProjectInfoScreen
import com.example.rmsjims.ui.screens.RoleSelectionScreen
import com.example.rmsjims.ui.screens.ProfileScreen
import com.example.rmsjims.ui.screens.RaiseTicketScreen
import com.example.rmsjims.ui.screens.TicketScreen
import com.example.rmsjims.ui.screens.admin.AdminDashboardScreen
import com.example.rmsjims.ui.screens.admin.EquipmentAssignmentScreen
import com.example.rmsjims.ui.screens.admin.SystemSettingScreen
import com.example.rmsjims.ui.screens.admin.UserManagementScreen
import com.example.rmsjims.ui.screens.assisstant.AssistantScreen
import com.example.rmsjims.ui.screens.assisstant.MachineDetailScreen
import com.example.rmsjims.ui.screens.assisstant.MachineStatusScreen
import com.example.rmsjims.ui.screens.assisstant.MaintenanceApprovalScreen
import com.example.rmsjims.ui.screens.assisstant.MaintenanceDetailScreen
import com.example.rmsjims.ui.screens.assisstant.ResourceManagementScreen
import com.example.rmsjims.ui.screens.assisstant.TicketManagementScreen
import com.example.rmsjims.ui.screens.assisstant.UsageApprovalScreen
import com.example.rmsjims.ui.screens.assisstant.RequestDetailsScreen
import com.example.rmsjims.data.model.UserRole
import com.example.rmsjims.viewmodel.BookingScreenViewmodel
import com.example.rmsjims.viewmodel.CalendarViewModel
import com.example.rmsjims.viewmodel.FacilitiesViewModel
import com.example.rmsjims.viewmodel.FilterSortViewModel
import com.example.rmsjims.viewmodel.ItemsViewModel
import com.example.rmsjims.viewmodel.UserSessionViewModel
import com.example.shopping.startup.screen.ForgotPasswordScreen
import com.example.shopping.startup.screen.NewPasswordScreen
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(){
    val navController = rememberNavController()
    AppNavGraph(navController)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.RoleSelectionScreen.route
    ) {

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
        composable(Screen.EmailVerificationScreen.route) {
            EmailVerificationScreen(navController)
        }
        composable(Screen.ForgotPasswordScreen.route) {
            ForgotPasswordScreen(navController)
        }
        composable(Screen.NewPasswordScreen.route) {
            NewPasswordScreen(navController)
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