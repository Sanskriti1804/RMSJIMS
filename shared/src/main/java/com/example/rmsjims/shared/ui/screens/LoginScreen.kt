package com.example.rmsjims.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.rmsjims.R
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.AppIconTextField
import com.example.rmsjims.ui.components.AppLogoImage
import com.example.rmsjims.ui.components.AppTextField
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomTitle
import com.example.rmsjims.ui.theme.Dimensions
import com.example.rmsjims.ui.theme.app_background
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.util.ResponsiveLayout

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun LoginScreen(
    navController: NavHostController,
){

    val appLogo = painterResource(id = R.drawable.jims_logo)

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
//    var isLoading by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(app_background)
            .padding(ResponsiveLayout.getHorizontalPadding()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(22.dp, 32.dp, 42.dp)))
        AppLogoImage()
        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(4.dp, 8.dp, 12.dp)))

        AppIconTextField(
            value = username,
            onValueChange = {username = it},
            placeholder = "Username",
            icon = painterResource(R.drawable.ic_user)
        )

        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(4.dp, 8.dp, 12.dp)))

        AppIconTextField(
            value = password,
            onValueChange = {password = it},
            placeholder = "Password",
            visualTransformation = PasswordVisualTransformation(),
            icon = painterResource((R.drawable.ic_password))
        )

        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp)))

        CustomLabel(
            header = "Forgot your username or password?",
            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
            headerColor = onSurfaceColor.copy(0.5f),
            modifier = Modifier
                .align(Alignment.End)
                .clickable(
                    onClick = {
                        navController.navigate(Screen.ForgotPasswordScreen.route)
                    }
                )
        )
        Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp)))

        AppButton(
            onClick = {
//              authViewModel.login(username, password)
                navController.navigate(Screen.HomeScreen.route)
                      },
            buttonText = "LOGIN"
        )
    }
}
//
//@Preview(showBackground = true)
//@Composable
//fun LoginScreenPreview(){
//    val dummyNavController = rememberNavController()
//    LoginScreen(dummyNavController)
//}