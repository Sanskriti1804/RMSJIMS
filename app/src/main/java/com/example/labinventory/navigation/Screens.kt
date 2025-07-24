package com.example.labinventory.navigation

sealed class Screen(val route : String) {
    object SplashScreen : Screen("splash")
    object SignupScreen : Screen("signup")
    object LoginScreen : Screen("login")
    object EmailVerificationScreen : Screen("email_verification")
    object ForgotPasswordScreen : Screen("forgot_password")
    object NewPasswordScreen : Screen("new_password")
    object HomeScreen : Screen("home")
    object EquipmentScreen : Screen("equipment")
    object BookingsScreen : Screen("bookings")
    object CancellationScreen : Screen("cancellation")
    object CheckoutScreen : Screen("checkout")
    object ConfirmationScreen : Screen("confirmation")
    object EmptyCartScreen : Screen("empty_cart")
    object SummaryScreen : Screen("summary")
    object FilterScreen : Screen("filter")
    object ProfileScreen : Screen("profile")
    object SearchScreen : Screen("search")
    object ProductDescriptionScreen : Screen("profileDescription")
}