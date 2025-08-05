package com.example.labinventory.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.labinventory.R
import com.example.labinventory.ui.theme.headerColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    onNavigationClick: (() -> Unit)? = null,
    containerColor: Color = whiteColor,
    titleColor: Color = headerColor,
){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = pxToDp(15))
                .background(containerColor),
            verticalAlignment = Alignment.Top
        ) {
            if (onNavigationClick != null) {
                AppNavBackIcon(
                    onClick = onNavigationClick,
                    modifier = Modifier
                        .padding(start = pxToDp(17), end = pxToDp(11), top = pxToDp(20))
                        .align(Alignment.CenterVertically)

                )

            }

            CustomLabel(
                header = title,
                headerColor = titleColor,
                fontSize = 25.sp,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = pxToDp(17))
            )
        }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun CustomTopBarPreview(){
    CustomTopBar(
        title = "Preview Title",
        onNavigationClick = {},
    )
}


