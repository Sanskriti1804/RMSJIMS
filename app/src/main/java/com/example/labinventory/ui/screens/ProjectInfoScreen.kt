package com.example.labinventory.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.labinventory.R
import com.example.labinventory.navigation.Screen
import com.example.labinventory.ui.components.AppButton
import com.example.labinventory.ui.components.AppDropDownTextField
import com.example.labinventory.ui.components.AppTextField
import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.components.CustomTopBar
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp

@SuppressLint("RememberReturnType")
@Composable
fun ProjectInfoScreen(
    navController: NavHostController
){

    var value by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Project Information",
                onNavigationClick = {
                    navController.popBackStack()
                }
            )
        },
        containerColor = whiteColor,
        bottomBar = {
            AppButton(
                buttonText = "CONFIRM",
                onClick = {
                   navController.navigate(Screen.BookingsScreen.route)
                },
                modifier = Modifier.padding(pxToDp(16))
            )
        }
    ) {paddingValues ->
        Column(
            modifier = Modifier
                .padding(pxToDp(16))
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(pxToDp(13))
        ){
            Spacer(modifier = Modifier.height(pxToDp(20)))
            AppTextField(
                value = value,
                onValueChange = { value = it},
                placeholder = "Project Name"
            )

            AppTextField(
                value = value,
                onValueChange = { value = it},
                placeholder = "Guide Name"
            )

            AppTextField(
                value = value,
                onValueChange = { value = it},
                placeholder = "Project Description",
                maxlines = 3
            )

            AppTextField(
                value = value,
                onValueChange = { value = it},
                placeholder = "Department"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AppDropDownTextField(
                    modifier = Modifier.weight(1f),
                    value = value,
                    onValueChange = { value = it},
                    placeholder = "Course Project"
                )
                AppDropDownTextField(
                    modifier = Modifier.weight(1f),
                    value = value,
                    onValueChange = { value = it},
                    placeholder = "B.des"
                )
            }

            Spacer(modifier = Modifier.height(13.dp))
            Text(
                text = "Tell us about your project and where you’ll be using the equipment. Use the dropdown to select whether it’s a personal or course project.",
                color = darkTextColor.copy(0.9f),
                fontSize = 16.sp,
                modifier = Modifier.fillMaxWidth(),
                letterSpacing = 0.3.sp,
                lineHeight = 20.sp,
                softWrap = true,
                fontFamily =  FontFamily(Font(R.font.font_alliance_regular_two)),
            )

        }
    }
}


