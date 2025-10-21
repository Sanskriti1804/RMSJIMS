package com.example.shopping.startup.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppTextField
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.Dimensions
import com.example.rmsjims.ui.theme.app_background


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPasswordScreen(navController: NavHostController){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold (
        containerColor = app_background,
        topBar = {
            CustomTopBar(
                title = "Create New Password"
            )
        }
    ){ paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))
            CustomLabel(
                header = "Your New Password must be different from previously used Password"
            )
            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

            AppTextField(
                value = "New Password",
                onValueChange = {},
                placeholder = "New Password"
            )
            Spacer(modifier = Modifier.height(Dimensions.smallSpacer()))
            AppTextField(
                value = "Confirm Password",
                onValueChange = {},
                placeholder = "Confirm Password"
            )
            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))


            AppButton(
                onClick = {
                    navController.navigate(Screen.HomeScreen.route)},
                buttonText = "Save",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun newpasswordcreenPreview(){
    val dummyNavController = rememberNavController()
    NewPasswordScreen(dummyNavController)
}