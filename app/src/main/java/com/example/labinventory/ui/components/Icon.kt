package com.example.labinventory.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.labinventory.R
import com.example.labinventory.ui.theme.categoryIconColor
import com.example.labinventory.ui.theme.labelColor
import com.example.labinventory.ui.theme.navBackColor
import com.example.labinventory.ui.theme.navLabelColor
import com.example.labinventory.ui.theme.searchBarColor
import com.example.labinventory.util.pxToDp

@Composable
fun AppCircularIcon(
    painter : Painter = painterResource(R.drawable.ic_reset),
    iconDescription : String = "Circular Icon",
    boxSize : Dp = pxToDp(46),
    boxShape : Shape = CircleShape,
    boxColor : Color = searchBarColor,
    iconPadding : Dp = pxToDp(11),
    iconSize : Dp = pxToDp(22),
    onClick : () -> Unit = {},
    tint : Color = labelColor
){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(boxSize)
            .clip(boxShape)
            .background(boxColor)
            .clickable { onClick() }
    ){
        Icon(
            painter = painter,
            contentDescription = iconDescription,
            modifier = Modifier
                .padding(iconPadding)
                .size(iconSize),
            tint = tint
        )
    }
}


@Composable
fun AppCategoryIcon(
    painter : Painter,
    iconDescription : String = "Equipment Category Icon",
    modifier: Modifier = Modifier,
    iconSize: Dp = pxToDp(24),
    tint : Color = categoryIconColor
){
    Icon(
        painter = painter,
        contentDescription = iconDescription,
        modifier = modifier
            .size(iconSize),
        tint = tint
    )
}

@Composable
fun AppNavIcon(
    painter : Painter,
    iconDescription : String = "Navigation Icon",
    modifier: Modifier = Modifier,
    iconSize : Dp = pxToDp(24),
    tint : Color = navLabelColor
){
    Icon(
        painter = painter,
        contentDescription = iconDescription,
        modifier = modifier
            .size(iconSize),
        tint = tint
    )
}

@Composable
fun AppNavBackIcon(
    painter : Painter = painterResource(R.drawable.ic_back),
    iconDescription : String = "Navigation Icon",
    modifier: Modifier = Modifier,
    size : Dp = pxToDp(24),
    tint : Color = navBackColor
){
    Icon(
        painter = painter,
        contentDescription = iconDescription,
        modifier = modifier
            .size(size),
        tint = tint
    )
}