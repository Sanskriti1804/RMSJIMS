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
import com.example.labinventory.util.ResponsiveLayout

@Composable
fun CustomTitle(
    header : String,
    headerColor : Color = titleColor,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize : TextUnit = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp),
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
    fontSize : TextUnit = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
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
    fontSize : TextUnit = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
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
    modifier: Modifier = Modifier.padding(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp)),
    thickness : Dp = ResponsiveLayout.getResponsivePadding(2.dp, 2.5.dp, 3.dp),
    divColor : Color = Color.DarkGray
){
    Divider(
        modifier = modifier,
        thickness = thickness,
        color = divColor
    )
}

