package com.example.rmsjims.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
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
        startDestination = Screen.LoginScreen.route
    ) {

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
    }
}