package com.example.rmsjims.ui.screens.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
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
fun ForgotPasswordScreen(navController: NavHostController){

    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold (
        containerColor = app_background,
        topBar = {
            CustomTopBar(
            title = "Forgot Password",
            onNavigationClick = {},
        )}
    ){ paddingValues ->
        Column (
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))
            CustomLabel(
                header = "Please Enter your Email Address to recieve a Verification Code"
            )
            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

            AppTextField(
                value = "Email Address",
                onValueChange = {},
                placeholder = "Enter your email address"
            )


            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

            AppButton(
                onClick = {
                    navController.navigate(Screen.EmailVerificationScreen.route)},
                buttonText = "Send"
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun pswdFOrgotScreenPreview(){
    val dummyNavController = rememberNavController()
    ForgotPasswordScreen(dummyNavController)
}