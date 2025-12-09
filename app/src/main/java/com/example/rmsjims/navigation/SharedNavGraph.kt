package com.example.rmsjims.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.ui.screens.shared.LoginScreen
import com.example.rmsjims.ui.screens.shared.RoleSelectionScreen
import com.example.rmsjims.viewmodel.UserSessionViewModel
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SharedNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.RoleSelectionScreen.route,
    parentNavController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Login Screen
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                navController = navController,
                parentNavController = parentNavController
            )
        }

        // Role Selection Screen
        composable(Screen.RoleSelectionScreen.route) {
            val sessionViewModel: UserSessionViewModel = koinViewModel()
            RoleSelectionScreen(
                navController = navController, // Use local nav controller to navigate to Login screen
                sessionViewModel = sessionViewModel,
                parentNavController = parentNavController
            )
        }
    }
}

