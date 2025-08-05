package com.example.labinventory.data.model

import androidx.compose.ui.unit.Dp

data class ResponsiveLayout (
    val screenWidth : Dp,
    val screenHeight : Dp,
    val topPadding : Dp,
    val isTablet : Boolean,
    val isPotrait : Boolean
)