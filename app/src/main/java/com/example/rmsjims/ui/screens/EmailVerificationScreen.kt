package com.example.rmsjims.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.rmsjims.R
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppTextField
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.theme.Dimensions
import com.example.rmsjims.ui.theme.app_background


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmailVerificationScreen(navController: NavHostController){
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        containerColor = app_background,
        topBar = {
            CustomTopBar(
                title = "Verify Your Email"
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

            Spacer(modifier = Modifier.height(15.dp))

            CustomLabel(
                header = "Please Enter the 4 digit code sent to sans@gmail.com"
            )
            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

            Row (
                modifier = Modifier.padding(Dimensions.componentPadding()),
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                AppTextField(
                    value = "0",
                    onValueChange = {},
                    placeholder = "0"
                )
                AppTextField(
                    value = "0",
                    onValueChange = {},
                    placeholder = "0"
                )
                AppTextField(
                    value = "0",
                    onValueChange = {},
                    placeholder = "0"
                )
                AppTextField(
                    value = "0",
                    onValueChange = {},
                    placeholder = "0"
                )
            }

            Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

            AppButton(
                onClick = {},
//                    navController.navigate(Screen.NewPasswordScreen.route)},
                buttonText = "Verify",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmailVerificationScreenPreview(){
    val dummyNavController = rememberNavController()
    EmailVerificationScreen(dummyNavController)
}