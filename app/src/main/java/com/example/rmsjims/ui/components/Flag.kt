package com.example.rmsjims.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmsjims.R
import com.example.rmsjims.data.model.TicketPriority
import com.example.rmsjims.ui.theme.Shapes

@Composable
fun TicketFlag(
    ticket : TicketPriority = TicketPriority.HIGH
){
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(Shapes.CardShape)
            .background(ticket.dispColor.copy(0.2f))
    ){
        Row(
            modifier = Modifier
                .padding(5.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AppCategoryIcon(
                painter = painterResource(R.drawable.ic_priority_flag),
                iconSize =16.dp,
                tint = ticket.dispColor
            )
            CustomLabel(
                header = ticket.dispName,
                fontSize = 16.sp
            )
        }
    }
}