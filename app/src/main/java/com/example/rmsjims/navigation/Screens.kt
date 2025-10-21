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
    object ProductDescriptionScreen : Screen("profileDescription")

}