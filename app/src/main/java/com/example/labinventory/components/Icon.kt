package com.example.labinventory.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.labinventory.R
import com.example.labinventory.ui.theme.searchBarColor

@Composable
fun AppCircularIcon(
    painter : Painter = painterResource(R.drawable.ic_reset),
    iconDescription : String = "Circular Icon",
    boxSize : Dp = 15.dp,
    boxShape : Shape = CircleShape,
    boxBackgroundColor : Color = searchBarColor,
    iconPadding : Dp = 3.dp,
    iconSize : Dp = 8.dp,
    onClick : () -> Unit = {}
){
    Box(
        modifier = Modifier
            .size(boxSize)
            .clip(boxShape)
            .background(boxBackgroundColor)
            .clickable { onClick() }
    ){
        Icon(
            painter = painter,
            contentDescription = iconDescription,
            modifier = Modifier
                .padding(iconPadding)
                .size(iconSize),
        )
    }
}


@Composable
fun AppCategoryIcon(
    painter : Painter,
    iconDescription : String = "Equipment Category Icon",
    modifier: Modifier = Modifier,
    iconSize : Dp = 8.dp
){
    Icon(
        painter = painter,
        contentDescription = iconDescription,
        modifier = Modifier
            .padding(top = 4.dp)
            .size(iconSize)
    )
}

@Composable
fun AppNavIcon(
    painter : Painter,
    iconDescription : String = "Navigation Icon",
    modifier: Modifier = Modifier,
    iconSize : Dp = 8.dp
){
    Icon(
        painter = painter,
        contentDescription = iconDescription,
        modifier = modifier
            .size(iconSize)
    )
}