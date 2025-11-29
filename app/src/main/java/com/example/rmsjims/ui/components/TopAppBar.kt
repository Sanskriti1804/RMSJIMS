package com.example.rmsjims.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = ResponsiveLayout.getResponsivePadding(15.dp, 18.dp, 22.dp))
            .background(containerColor)
    ) {
        // Back button positioned absolutely on the left
        if (onNavigationClick != null) {
            AppNavBackIcon(
                onClick = onNavigationClick,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(
                        start = ResponsiveLayout.getResponsivePadding(17.dp, 20.dp, 24.dp),
                        top = ResponsiveLayout.getResponsivePadding(20.dp, 24.dp, 28.dp)
                    )
            )
        }

        // Title centered horizontally across the full width
        CustomLabel(
            header = title,
            headerColor = titleColor,
            fontSize = ResponsiveLayout.getResponsiveFontSize(25.sp, 28.sp, 32.sp),
            modifier = Modifier
                .align(Alignment.Center)
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


