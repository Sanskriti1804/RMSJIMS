package com.example.labinventory.util

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun pxToDp(px: Int): Dp {
    return (px * 0.96f).dp
}