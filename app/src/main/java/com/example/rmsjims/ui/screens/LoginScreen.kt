package com.example.rmsjims.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rmsjims.R
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppLogoImage
import com.example.rmsjims.ui.components.AppTextField
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomTitle
import com.example.rmsjims.ui.theme.Dimensions

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
){

    val appLogo = painterResource(id = R.drawable.jims_logo)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier.padding(Dimensions.componentPadding()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        AppLogoImage()
        Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

        CustomTitle(
            header = "Login"
        )

        Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

        Card(
            onClick = {}
        ) {
            Column(
                modifier = Modifier.padding(Dimensions.componentPadding()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CustomTitle(
                    header = "email"
                )
                AppTextField(
                    value = email,
                    onValueChange = {email = it},
                    placeholder = "Enter your email"
                )
                CustomTitle(
                    header = "Password"
                )
                AppTextField(
                    value = password,
                    onValueChange = {password = it},
                    placeholder = "Enter your password",
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(Dimensions.medSpacer()))

                AppButton(
                    onClick = {},
//                        authViewModel.login(email, password)
//                        navController.navigate(Screen.HomeScreen.route)},
                    buttonText = "LOGIN"
                )

                CustomLabel(
                    header = "Forgot Password",
                    fontSize = 10.sp,
                    modifier = Modifier
                        .clickable(
                            onClick = {}
//                                navController.navigate(Screen.ForgotPasswordScreen.route)}
                        )
                )
            }
        }
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview(){
//    val dummyNavController = rememberNavController()
//    LoginScreen(dummyNavController)
//}