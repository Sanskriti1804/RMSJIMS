package com.example.rmsjims.ui.components

import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.util.ResponsiveLayout

@Composable
fun AppTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
//    height: Dp = ResponsiveLayout.getResponsiveSize(46.dp, 52.dp, 60.dp),
    shape: Shape = RoundedCornerShape(ResponsiveLayout.getResponsivePadding(4.dp, 6.dp, 8.dp)),
    placeholder: String,
    textColor: Color = onSurfaceColor.copy(alpha = 0.7f),
    containerColor: Color = onSurfaceVariant,
    minLines: Int = 1,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        shape = shape,
        placeholder = {
            CustomLabel(
                header = placeholder,
                modifier = Modifier.padding(0.dp),
                headerColor = onSurfaceColor.copy(0.7f),
                fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            disabledTextColor = textColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            cursorColor = textColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        visualTransformation = visualTransformation,
        minLines = minLines,
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun AppIconTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
//    height: Dp = ResponsiveLayout.getResponsiveSize(46.dp, 52.dp, 60.dp),
    shape: Shape = RoundedCornerShape(ResponsiveLayout.getResponsivePadding(4.dp, 6.dp, 8.dp)),
    placeholder: String,
    textColor: Color = onSurfaceColor.copy(alpha = 0.7f),
    containerColor: Color = onSurfaceVariant,
    minLines: Int = 1,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    icon: Painter
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        shape = shape,
        placeholder = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AppCategoryIcon(
                    painter = icon,
                    iconSize = 20.dp
                )
                CustomLabel(
                    header = placeholder,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(0.dp),
                    headerColor = onSurfaceColor.copy(0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 22.sp, 24.sp)
                )
            }
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = textColor,
            unfocusedTextColor = textColor,
            disabledTextColor = textColor,
            focusedContainerColor = containerColor,
            unfocusedContainerColor = containerColor,
            disabledContainerColor = containerColor,
            cursorColor = textColor,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        visualTransformation = visualTransformation,
        minLines = minLines,
        maxLines = maxLines,
        modifier = modifier.fillMaxWidth()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilteredAppTextField(
    modifier: Modifier = Modifier.fillMaxWidth(),
    value: String,
    onValueChange: (String) -> Unit,
    shape: Shape = RoundedCornerShape(ResponsiveLayout.getResponsivePadding(4.dp, 6.dp, 8.dp)),
    placeholder: String,
    textColor: Color = onSurfaceColor.copy(alpha = 0.7f),
    containerColor: Color = onSurfaceVariant,
    minLines: Int = 1,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    items: List<String> = emptyList(),
    onItemSelected: (String) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded && items.isNotEmpty(),
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        TextField(
            value = value,
            onValueChange = {
                onValueChange(it)
                expanded = true
            },
            shape = shape,
            placeholder = {
                CustomLabel(
                    header = placeholder,
                    modifier = Modifier.padding(0.dp),
                    headerColor = onSurfaceColor.copy(0.7f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                disabledTextColor = textColor,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                disabledContainerColor = containerColor,
                cursorColor = textColor,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            visualTransformation = visualTransformation,
            minLines = minLines,
            maxLines = maxLines,
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor() //
        )

        ExposedDropdownMenu(
            expanded = expanded && items.isNotEmpty(),
            onDismissRequest = { expanded = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        onItemSelected(item)
                        expanded = false
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }
    }
}

@Composable
fun AppDropDownTextField(
    modifier : Modifier = Modifier.fillMaxWidth(),
    value : String,
    items : List<String>,
//    height : Dp = ResponsiveLayout.getResponsiveSize(46.dp, 52.dp, 60.dp),
    onValueChange : (String) -> Unit,
    shape: Shape = RoundedCornerShape(ResponsiveLayout.getResponsivePadding(1.dp, 2.dp, 3.dp)),
    placeholder: String,
    textColor : Color = onSurfaceColor.copy(alpha = 0.7f),
    containerColor : Color = onSurfaceVariant
){
    var expanded by remember { mutableStateOf(false) }

    Box(modifier){
        OutlinedTextField(
            value = value,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            maxLines = 1,
            shape = shape,
            placeholder = {
                Row {
                    CustomLabel(
                        header = placeholder,
                        modifier = Modifier.padding(0.dp),
                        headerColor = onSurfaceColor.copy(0.7f),
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                    )

                }
            },
            trailingIcon = {
                Icon(
                    Icons.Default.ArrowDropDown,
                    contentDescription = "Drop Down Icon",
                    tint = textColor,
                    modifier = Modifier
                        .clickable {
                            expanded = !expanded
                        }
                        .padding(ResponsiveLayout.getResponsivePadding(8.dp, 10.dp, 12.dp))
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = textColor,
                unfocusedTextColor = textColor,
                focusedContainerColor = containerColor,
                unfocusedContainerColor = containerColor,
                focusedIndicatorColor = textColor
            ),
            modifier = Modifier
                .clickable{
                    expanded = true
                },
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            items.forEach{
                item ->
                DropdownMenuItem(
                    text = { Text(item)},
                    onClick = {
                        onValueChange(item)
                        expanded = false
                    }
                )
            }
        }
    }
}


