package com.example.labinventory.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchColors
import androidx.compose.ui.graphics.Color

@Composable
fun CustomSwitch(
    isChecked : Boolean,
    enabled : Boolean,
    onCheckedChange : (Boolean) -> Unit,
    checkedIconColor: Color,
    checkedThumbColor: Color,
    checkedTrackColor: Color,
    checkedBorderColor: Color,
    thumbContent : (@Composable () -> Unit)? = null,
) {
    Switch(
        checked = isChecked,
        enabled = enabled,
        colors = SwitchDefaults.colors(
            checkedIconColor = checkedIconColor,
            checkedThumbColor = checkedThumbColor,
            checkedTrackColor = checkedTrackColor,
            checkedBorderColor = checkedBorderColor
        ),
        onCheckedChange = onCheckedChange,       //it - new value
        thumbContent = {
            if (thumbContent != null) {
                thumbContent()
            }
        }
    )
}

