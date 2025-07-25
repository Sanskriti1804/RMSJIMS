package com.example.labinventory.ui.components

import androidx.compose.foundation.layout.padding
import androidx. compose. ui. graphics. Color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.labinventory.R
import com.example.labinventory.ui.theme.headerColor
import com.example.labinventory.ui.theme.whiteColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    titleOverflow: TextOverflow,
    //navIcon
    navigationIcon: Painter = painterResource(R.drawable.ic_backnav),
    onNavigationClick: (() -> Unit)? = null,
    containerColor: Color = whiteColor,
    titleColor: Color = headerColor,
    scrollBehavior: TopAppBarScrollBehavior
){
 TopAppBar(
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.Normal,
                fontSize = 24.sp,
                letterSpacing = 0.03.em,
                overflow = titleOverflow
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                onNavigationClick?.invoke()     //safely calls only if it is null
            }) {
                AppNavIcon(
                    painter = navigationIcon,
                    iconSize = 8.dp,
                    modifier = Modifier.padding(top = 6.dp, bottom = 7.dp, start = 6.dp, end = 3.dp)
                )
            }
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = titleColor, 
        )
    )
}