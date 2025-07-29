package com.example.labinventory.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.labinventory.ui.components.AppButton
import com.example.labinventory.ui.components.AppDropDownTextField
import com.example.labinventory.ui.components.AppTextField
import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.theme.darkTextColor

@SuppressLint("RememberReturnType")
@Composable
fun ProjectInfoScreen(){

    var value by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
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
                value = value,
                onValueChange = { value = it},
                placeholder = "Course Project"
            )
            AppDropDownTextField(
                value = value,
                onValueChange = { value = it},
                placeholder = "B.des"
            )
        }

        Spacer(modifier = Modifier.height(13.dp))
        CustomLabel(
            header = "Tell us about your project and where you’ll be using the equipment. Use the dropdown to select whether it’s a personal or course project.",
            headerColor = darkTextColor.copy(0.9f),
            fontSize = 14.sp,
            modifier = Modifier,
        )

        AppButton(
            buttonText = "BOOK",
            onClick = {},
            modifier = Modifier.padding(1.dp)
//            modifier = Modifier.align(Alignment.Bottom as Alignment.Horizontal)
        )
    }
}

