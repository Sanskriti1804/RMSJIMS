package com.example.rmsjims.navigation

sealed class Screen(val route : String) {
    object HomeScreen : Screen("home")
    object LoginScreen : Screen("login")
    object EmailVerificationScreen : Screen("email_verification")
    object ForgotPasswordScreen : Screen("forgot_password")
    object NewPasswordScreen : Screen("new_password")
    object EquipmentScreen : Screen("equipment/{categoryName}"){
        fun createRoute(categoryName: String) = "equipment/$categoryName"
    }
    object BookingsScreen : Screen("bookings")
    object CalendarScreen : Screen("calendar")
    object ProjectInfoScreen : Screen("project_info")
    object RaiseTicketScreen : Screen("ticket_raise")
    object TicketScreen : Screen("tickets")
    object ProductDescriptionScreen : Screen("profileDescription")
    object ProfileScreen : Screen("profile")
    object AdminDashboardScreen : Screen("admin_dashboard")
    object EquipmentAssignmentScreen : Screen("equipment_assignment")
    object SystemSettingScreen : Screen("system_setting")
    object UserManagementScreen : Screen("user_management")
    object AssistantScreen : Screen("assistant")
    object MachineStatusScreen : Screen("machine_status")
    object MaintenanceApprovalScreen : Screen("maintenance_approval")
    object ResourceManagementScreen : Screen("resource_management")
    object TicketManagementScreen : Screen("ticket_management")
    object UsageApprovalScreen : Screen("usage_approval")

}