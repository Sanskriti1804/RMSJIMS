package com.example.labinventory.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labinventory.R
import com.example.labinventory.ui.theme.categoryColor
import com.example.labinventory.ui.theme.navLabelColor
import com.example.labinventory.ui.theme.titleColor
import com.example.labinventory.util.pxToDp

@Composable
fun CustomTitle(
    header : String,
    headerColor : Color = titleColor,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize : TextUnit = 20.sp,
    fontFamily: FontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
    maxLine: Int = 1
){
    Text(
        text = header,
        color = headerColor,
        style = TextStyle(
            fontWeight = fontWeight,
            fontSize = fontSize,
            fontFamily = fontFamily),
        maxLines = maxLine
    )
}

@Composable
fun CustomSmallLabel(
    header : String,
    headerColor : Color = navLabelColor,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize : TextUnit = 14.sp,
    fontFamily: FontFamily = FontFamily(Font(R.font.font_sf_pro_display)),
    modifier : Modifier
){
    Text(
        text = header,
        modifier = modifier,
        color = headerColor,
        fontWeight = fontWeight,
        fontSize = fontSize,
        fontFamily = fontFamily
    )
}

@Composable
fun CustomLabel(
    header : String,
    headerColor : Color = categoryColor,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize : TextUnit = pxToDp(16).value.sp,
    fontFamily: FontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
    modifier: Modifier = Modifier,
    maxLine: Int = 1
){

    Text(
        text = header,
        modifier = modifier,
        color = headerColor,
        style = TextStyle(
            fontWeight = fontWeight,
            fontSize = fontSize,
            fontFamily = fontFamily),        maxLines = maxLine
    )
}

@Composable
fun CustomDivider(
    modifier: Modifier = Modifier.padding(12.dp),
    thickness : Dp = 2.dp,
    divColor : Color = Color.DarkGray
){
    Divider(
        modifier = modifier,
        thickness = thickness,
        color = divColor
    )
}

