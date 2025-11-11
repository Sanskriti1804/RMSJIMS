package com.example.rmsjims.navigation

sealed class Screen(val route : String) {
    object HomeScreen : Screen("home")
    object LoginScreen : Screen("login")
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
    object MachineDetailScreen : Screen("machine_detail/{machineId}") {
        fun createRoute(machineId: String) = "machine_detail/$machineId"
    }
    object MaintenanceApprovalScreen : Screen("maintenance_approval")
    object MaintenanceDetailScreen : Screen("maintenance_detail/{requestId}") {
        fun createRoute(requestId: String) = "maintenance_detail/$requestId"
    }
    object RoleSelectionScreen : Screen("role_selection")
    object RequestDetailsScreen : Screen("request_details/{requestId}") {
        fun createRoute(requestId: String) = "request_details/$requestId"
    }
    object ResourceManagementScreen : Screen("resource_management")
    object TicketManagementScreen : Screen("ticket_management")
    object UsageApprovalScreen : Screen("usage_approval")
    object AboutAppScreen : Screen("about_app")
    object RoleOverviewScreen : Screen("role_overview")
    object PermissionsOverviewScreen : Screen("permissions_overview/{selectedRole}") {
        fun createRoute(selectedRole: String) = "permissions_overview/$selectedRole"
    }

}