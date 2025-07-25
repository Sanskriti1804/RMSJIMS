//package com.example.labinventory.components
//
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Clear
//import androidx.compose.material3.AssistChip
//import androidx.compose.material3.AssistChipDefaults
//import androidx.compose.material3.FilterChip
//import androidx.compose.material3.Icon
//import androidx.compose.material3.InputChip
//import androidx.compose.material3.SuggestionChip
//import androidx.compose.material3.SuggestionChipDefaults
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.graphics.painter.Painter
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.Dp
//import androidx.compose.ui.unit.dp
//import com.example.shopping.R
//import com.example.shopping.ui.theme.Dimensions
//import com.example.shopping.ui.theme.Shapes
//import com.example.shopping.ui.theme.app__dtext
//import com.example.shopping.ui.theme.app_ltext
//import com.example.shopping.ui.theme.app_lComponent
//import com.example.shopping.ui.theme.app_llComponent
//import com.example.shopping.ui.theme.app_background
//
//@Composable
//fun CustomAssistChip(
//    onClick : () -> Unit,
//    label : String,
//    icon : Painter = painterResource(R.drawable.ic_app_fav),
//    shape : Shape = Shapes.ChipsShape,
//    containerColor: Color = app_llComponent,
//    labelColor : Color = app__dtext,
//    iconColor : Color = app_lComponent,
//    chipElevation: Dp = Dimensions.buttonElevation,
//    borderWidth : Dp = Dimensions.buttonBorder,
//    borderColor : Color = app__dtext
//){
//    AssistChip(
//        onClick = onClick,
//        label = { Text(label) },
//        leadingIcon = {
//            CustomIcon(
//                painter = icon
//            )
//        },
//        shape = shape,
//        colors = AssistChipDefaults.assistChipColors(
//            containerColor = containerColor,
//            labelColor = labelColor,
//            leadingIconContentColor = iconColor
//        ),
//        elevation = AssistChipDefaults.assistChipElevation(
//            chipElevation
//        ),
//        border = BorderStroke(
//            width = borderWidth,
//            color = borderColor
//        )
//    )
//}
//
//@Composable
//fun CustomFilteringChip(
//    selected : Boolean,
//    onClick: () -> Unit,
//    label: String,
//){
//    FilterChip(
//        selected = selected,
//        onClick = onClick,
//        label = { Text(label) },
//        leadingIcon = {
//            if (selected) Icon(Icons.Default.Clear, contentDescription = null)
//        }
//
//    )
//}
//
//@Composable
//fun CustomInputChip(
//    selected: Boolean,
//    onClick: () -> Unit,
//    label: String,
//    icon: ImageVector,
//){
//    InputChip(
//        selected = selected,
//        onClick = onClick,
//        label = { Text(label) },
//        avatar = { icon }
//    )
//}
//
//@Composable
//fun CustomSuggestionChip(
//    onClick: () -> Unit,
//    label: String,
//    shape: Shape = Shapes.ChipsShape,
//    containerColor : Color = app_ltext,
//    labelColor : Color = app_background,
//    disabledContainerColor : Color = app_background,
//    disabledLabelColor : Color = app_ltext
//){
//    SuggestionChip(
//        onClick = onClick,
//        label = { Text(label) },
//        shape = shape,
//        colors = SuggestionChipDefaults.suggestionChipColors(
//            containerColor = containerColor,
//            labelColor = labelColor,
//            disabledContainerColor = disabledContainerColor,
//            disabledLabelColor = disabledLabelColor
//        )
//    )
//}