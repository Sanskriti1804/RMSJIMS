package com.example.rmsjims.ui.components


import android.annotation.SuppressLint
import androidx.compose.material3.Badge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@SuppressLint("SuspiciousIndentation")
@Composable
fun CustomBadge(
    showBadge : Boolean = true,
    badgeContent : (@Composable () -> Unit)? = null,
    badgeColor : Color = Color.Blue,
    contentColor : Color = Color.White,
    contentModifier: Modifier = Modifier
){
    if (!showBadge) return
                if (badgeContent != null) {
                    Badge(
                        containerColor = badgeColor,
                        contentColor = contentColor,
                        modifier = contentModifier
                    ) {
                        badgeContent()
                    }
                } else {
                    Badge(containerColor = badgeColor)
                }
}
