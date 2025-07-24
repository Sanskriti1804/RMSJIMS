package com.example.labinventory.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Badge
import androidx.compose.material.Text
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labinventory.navigation.bottomNavItems
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.navLabelColor
import com.example.labinventory.ui.theme.whiteColor
import kotlin.collections.forEachIndexed


@Composable
fun CustomNavigationBar(
    navBarPadding : Dp,
    bottomBarColor : Color,
    badgecolor : Color = highlightColor,
    contentColor : Color = whiteColor,
    selected : Boolean = false,
    navBarShape : Shape,
    ){
    NavigationBar(
        modifier = Modifier
            .padding(navBarPadding)
            .clip(navBarShape)
            .fillMaxWidth(),
        containerColor = bottomBarColor,
        contentColor = contentColor
    ) {
        bottomNavItems.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selected,
                onClick = {},
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge(
                                    backgroundColor = badgecolor,
                                    contentColor = contentColor
                                ) {
                                    Text(text = item.badgeCount.toString())
                                }
                            } else if (item.hasNews) {
                                Badge(
                                    backgroundColor = badgecolor,
                                )
                            }
                        }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(33.dp)
                                .padding(top = 7.dp, bottom = 9.dp, start = 15.dp, end = 15.dp)
                        ) {
                            AppNavIcon(
                                painter = painterResource(item.iconResId)
                            )
                            CustomLabel(
                                header = item.label,
                                headerColor = navLabelColor,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(top = 3.dp)
                            )
                        }
                    }
                }
            )
        }
    }
}