package com.example.labinventory.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labinventory.navigation.bottomNavItems
import com.example.labinventory.ui.theme.navBarColor
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.navLabelColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp

/*
* errors:
* The value 'null' can't be assigned to a parameter of type '@Composable () -> Unit'
* */

@Composable
fun CustomNavigationBar(
    bottomBarColor: Color = whiteColor,
    badgeColor: Color = highlightColor,
    contentColor: Color = whiteColor,
    selected: Boolean = false
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5f.dp)
                .background(Color(0xFFE0E0E0)) // light gray divider
        )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(pxToDp(101)) // Total height as before
            .padding(horizontal = 0.dp)
            .background(bottomBarColor),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        bottomNavItems.forEach { item ->
            Column(
                modifier = Modifier
                    .padding(
                        top = pxToDp(23),
                        bottom = pxToDp(29),
                        start = pxToDp(10),
                        end = pxToDp(10)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(modifier = Modifier) {
                    AppNavIcon(
                        painter = painterResource(id = item.iconResId),
                        iconSize = pxToDp(24)
                    )

                    // Badge on top end
                    if (item.badgeCount != null || item.hasNews) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(x = 10.dp, y = (-6).dp)
                                .background(badgeColor, shape = CircleShape)
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            if (item.badgeCount != null) {
                                Text(
                                    text = item.badgeCount.toString(),
                                    color = contentColor,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(pxToDp(8)))

                CustomSmallLabel(
                    header = item.label,
                    headerColor = navLabelColor,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 3.dp)
                )
            }
        }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomNavigationBarPreview() {
    CustomNavigationBar()
}