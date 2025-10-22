package com.example.rmsjims.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.darkColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rmsjims.R
import com.example.rmsjims.data.model.TicketPriority
import com.example.rmsjims.ui.theme.Shapes
import com.example.rmsjims.ui.theme.app_background
import com.example.rmsjims.ui.theme.cardColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import java.util.Date
import kotlin.toString

@Composable
fun TicketCard(
    elevation: Dp = 2.dp,
    ticketId : String = "TX-10456",
    count : Int = 12
){
    Card(
        modifier = Modifier.
                fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = elevation
        ),
        shape = Shapes.CardShape,
        colors = CardDefaults.cardColors(
            containerColor = app_background
        )
        ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CustomLabel(
                header = ticketId,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
            )

            Row (
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                TicketFlag()
                Row {
                    AppCategoryIcon(
                        painter = painterResource(R.drawable.ic_assigned_time),
                        iconSize =8.dp,
                    )
                    CustomLabel(
                        header = Date().toString(),
                        headerColor = onSurfaceColor,
                        fontSize = 8.sp
                    )

                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .wrapContentSize()
                            .border(1.dp, cardColor)
                    ){
                        CustomLabel(
                            header = "Projector in 301A not working",
                            headerColor = onSurfaceColor.copy(0.8f),
                            fontSize = 8.sp
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CustomLabel(
                                header = "Deadline",
                                headerColor = onSurfaceColor.copy(0.8f),
                                fontSize = 8.sp
                            )
                            CustomLabel(
                                header = "28 Oct, 2025",
                                headerColor = onSurfaceColor.copy(0.8f),
                                fontSize = 8.sp
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CustomLabel(
                                header = "Requested by",
                                headerColor = onSurfaceColor.copy(0.8f),
                                fontSize = 8.sp
                            )
                            CustomLabel(
                                header = "S.K. Shukla",
                                headerColor = onSurfaceColor.copy(0.8f),
                                fontSize = 8.sp
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            CustomLabel(
                                header = "Assignee",
                                headerColor = onSurfaceColor.copy(0.8f),
                                fontSize = 8.sp
                            )
                            CustomLabel(
                                header = "V. Verma",
                                headerColor = onSurfaceColor.copy(0.8f),
                                fontSize = 8.sp
                            )
                        }
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            AppCategoryIcon(
                                painter = painterResource(R.drawable.ic_ticket_thread),
                                iconSize =8.dp,
                                tint = onSurfaceColor.copy(0.5f)
                            )
                            CustomLabel(
                                header = count.toString(),
                                headerColor = onSurfaceColor.copy(0.8f),
                                fontSize = 8.sp,
                                modifier = Modifier
                            )
                        }
                        CustomLabel(
                            header = "See Detail",
                            headerColor = onSurfaceColor.copy(0.8f),
                            fontSize = 8.sp
                        )
                    }

                }
            }
        }

    }

}

@Preview(showBackground = true)
@Composable
fun TicketCardPreview() {
    TicketCard()
}

