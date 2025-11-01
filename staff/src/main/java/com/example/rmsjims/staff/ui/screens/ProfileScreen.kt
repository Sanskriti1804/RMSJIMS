package com.example.rmsjims.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.rmsjims.R
import com.example.rmsjims.data.model.BookingTab
import com.example.rmsjims.data.model.TabItem
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppButton
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.components.ProfileCard
import com.example.rmsjims.ui.theme.cardColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun ProfileScreen(
    navController: NavHostController,
){
    androidx.compose.material.Scaffold(
        topBar = {
            CustomTopBar(title = "Profile")
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getHorizontalPadding())
                .padding(paddingValues)
        ) {
            ProfileCard()
            Spacer(
                modifier = Modifier.height(
                    ResponsiveLayout.getResponsivePadding(
                        16.dp,
                        20.dp,
                        24.dp
                    )
                )
            )
            Divider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = cardColor
            )
            Spacer(
                modifier = Modifier.height(
                    ResponsiveLayout.getResponsivePadding(
                        16.dp,
                        20.dp,
                        24.dp
                    )
                )
            )
            Column(verticalArrangement = Arrangement.spacedBy(pxToDp(16))) {
                InChargeRow(label = "Email", name = "shukask@edu.com")
                InChargeRow(
                    label = "Contact no.",
                    name = "9895482191",
                    icons = listOf(R.drawable.ic_call)
                )
            }
        }
    }
}