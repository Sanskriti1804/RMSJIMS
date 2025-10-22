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

@Composable
fun TicketScreen(){
    val ticketPriority : TicketPriority = TicketPriority.HIGH
    val ticketGroup : TicketGroup = TicketGroup("Ticket Priority", ticketPriority.dispColor, 12)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryColor.copy(0.1f))
            .clip(Shapes.CardShape)
            .padding(16.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.align(Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ){
                CustomBadge(
                    badgeColor = ticketPriority.dispColor,
                    contentModifier = Modifier.size(6.dp)
                )
                CustomLabel(
                    header = ticketPriority.dispName
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