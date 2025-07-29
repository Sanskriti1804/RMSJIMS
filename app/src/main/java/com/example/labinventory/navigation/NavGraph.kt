package com.example.labinventory.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.labinventory.ui.screens.BookingScreen
import com.example.labinventory.ui.screens.CalendarScreen
import com.example.labinventory.ui.screens.ChatBottomSheet
import com.example.labinventory.ui.screens.EquipmentScreen
import com.example.labinventory.ui.screens.FilterSortBottomSheet
import com.example.labinventory.ui.screens.HomeScreen
import com.example.labinventory.ui.screens.ProdDescScreen
import com.example.labinventory.ui.screens.ProjectInfoScreen
import com.example.labinventory.viewmodel.BookingScreenViewmodel
import com.example.labinventory.viewmodel.CalendarViewModel
import com.example.labinventory.viewmodel.FilterSortViewModel
import com.example.labinventory.viewmodel.SearchViewModel
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
        startDestination = Screen.HomeScreen.route
    ) {

        composable(Screen.HomeScreen.route) {
           HomeScreen(navController)
        }
        composable(Screen.EquipmentScreen.route) {
            EquipmentScreen(navController)
        }
//        composable(Screen.BookingsScreen.route) {
//            val bookingViewModel : BookingScreenViewmodel = koinViewModel()
//            BookingScreen( bookingViewModel)
//        }
        composable(Screen.CalendarScreen.route) {
            val calendarViewModel : CalendarViewModel = koinViewModel()
            CalendarScreen( calendarViewModel)
        }
        composable(Screen.ChatBottomSheet.route) {
            val chatViewmodel : SearchViewModel = koinViewModel()
            ChatBottomSheet( chatViewmodel)
        }
        composable(Screen.FilterSortBottomSheet.route) {
            val filterSortViewModel : FilterSortViewModel = koinViewModel()
            FilterSortBottomSheet( filterSortViewModel)
        }
        composable(Screen.ProductDescriptionScreen.route) {
            ProdDescScreen()
        }
        composable(Screen.ProjectInfoScreen.route) {
            ProjectInfoScreen()
        }
    }
}