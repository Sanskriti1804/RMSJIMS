package com.example.rmsjims.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmsjims.ui.theme.headerColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout


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
                .padding(top = ResponsiveLayout.getResponsivePadding(15.dp, 18.dp, 22.dp))
                .background(containerColor),
            verticalAlignment = Alignment.Top
        ) {
            if (onNavigationClick != null) {
                AppNavBackIcon(
                    onClick = onNavigationClick,
                    modifier = Modifier
                        .padding(
                            start = ResponsiveLayout.getResponsivePadding(17.dp, 20.dp, 24.dp), 
                            end = ResponsiveLayout.getResponsivePadding(11.dp, 14.dp, 17.dp), 
                            top = ResponsiveLayout.getResponsivePadding(20.dp, 24.dp, 28.dp)
                        )
                        .align(Alignment.CenterVertically)

                )
            }
            else{
                Spacer(modifier = Modifier.width(ResponsiveLayout.getResponsivePadding(16.dp, 18.dp, 20.dp)))

            }

            CustomLabel(
                header = title,
                headerColor = titleColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(25.sp, 28.sp, 32.sp),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(top = ResponsiveLayout.getResponsivePadding(17.dp, 20.dp, 24.dp))
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


