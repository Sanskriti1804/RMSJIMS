package com.example.rmsjims.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmsjims.data.model.TicketGroup
import com.example.rmsjims.data.model.TicketPriority
import com.example.rmsjims.ui.components.CustomBadge
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.TicketCard
import com.example.rmsjims.ui.theme.Shapes
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.util.ResponsiveLayout

@Composable
fun TicketScreen(){
    val ticketPriority : TicketPriority = TicketPriority.HIGH
    val ticketGroup : TicketGroup = TicketGroup("Ticket Priority", ticketPriority.dispColor, 12)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor.copy(0.1f))
            .clip(Shapes.CardShape)
            .padding(ResponsiveLayout.getHorizontalPadding())
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp))
        ) {
            Row (
                horizontalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsiveSize(8.dp, 10.dp, 12.dp)),
                modifier = Modifier.align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ){
                CustomBadge(
                    badgeColor = ticketPriority.dispColor,
                    contentModifier = Modifier.size(ResponsiveLayout.getResponsiveSize(6.dp, 8.dp, 10.dp))
                )
                CustomLabel(
                    header = ticketPriority.dispName,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp)
                )
                CustomBadge(
                    badgeColor = ticketGroup.groupColor,
                    badgeContent = {10}
                )
            }
            TicketCard()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TicketScreenPreview() {
    TicketScreen()
}