package com.example.rmsjims.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(
    startDestination: String = Screen.SharedNavGraph.route
) {
    val navController = rememberNavController()
    MainAppNavGraph(
        navController = navController,
        startDestination = startDestination
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainAppNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.SharedNavGraph.route
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Shared Navigation Graph - contains Splash, Login, Role Selection
        composable(Screen.SharedNavGraph.route) {
            SharedNavGraph(
                parentNavController = navController
            )
        }

        // Login Screen - directly accessible from main nav graph for logout/role switch
        composable(Screen.LoginScreen.route) {
            val sharedNavController = rememberNavController()
            SharedNavGraph(
                navController = sharedNavController,
                startDestination = Screen.LoginScreen.route,
                parentNavController = navController
            )
        }

        // Admin Navigation Graph - merged with shared screens
        composable(Screen.AdminNavGraph.route) {
            AdminNavGraph(navController = navController, parentNavController = navController)
        }

        // Assistant Navigation Graph - merged with shared screens
        composable(Screen.AssistantNavGraph.route) {
            AssistantNavGraph(navController = navController, parentNavController = navController)
        }

        // Staff Navigation Graph - merged with shared screens
        composable(Screen.StaffNavGraph.route) {
            StaffNavGraph(navController = navController, parentNavController = navController)
        }
    }
}

@Composable
fun AdminNavGraph(
    navController: NavHostController,
    parentNavController: NavHostController
) {
    AdminModuleApp(parentNavController = parentNavController)
}

@Composable
fun AssistantNavGraph(
    navController: NavHostController,
    parentNavController: NavHostController
) {
    AssistantModuleApp(parentNavController = parentNavController)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StaffNavGraph(
    navController: NavHostController,
    parentNavController: NavHostController
) {
    StaffModuleApp(parentNavController = parentNavController)
}

