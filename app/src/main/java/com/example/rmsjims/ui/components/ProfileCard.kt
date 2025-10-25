package com.example.rmsjims.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmsjims.R
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.util.pxToDp

@Composable
fun ProfileCard() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ProfileImage()
        CustomTitle(
            header = "S.K. Shukla",
            fontWeight = FontWeight.Bold,
            fontSize = ResponsiveLayout.getResponsiveFontSize(20.sp, 24.sp, 28.sp)
        )
        Row (
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ){
            CustomLabel(
                header = "Teacher",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(vertical = pxToDp(2))
                    .background(
                        color = primaryColor.copy(0.2f),
                        shape = RoundedCornerShape(pxToDp(20))
                    )
                    .padding(horizontal = pxToDp(10), vertical = pxToDp(4)),
                headerColor = whiteColor
            )

            CustomLabel(
                header = "Engineering",
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(vertical = pxToDp(2))
                    .background(
                        color = primaryColor.copy(0.2f),
                        shape = RoundedCornerShape(pxToDp(20))
                    )
                    .padding(horizontal = pxToDp(10), vertical = pxToDp(4)),
                headerColor = whiteColor
            )
        }

        CustomLabel(
            header = "shukask@edu.com"
        )
        CustomLabel(
            header = "JIMS Building A"
        )
    }
}