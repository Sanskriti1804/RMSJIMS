package com.example.rmsjims.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.draw.clip
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
import com.example.rmsjims.util.ResponsiveLayout
import java.util.Date
import kotlin.toString

@Composable
fun TicketCard(
    elevation: Dp = ResponsiveLayout.getResponsiveSize(2.dp, 3.dp, 4.dp),
    ticketId : String = "TX-10456",
    count : Int = 12
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = elevation
        ),
        shape = Shapes.CardShape,
        colors = CardDefaults.cardColors(
            containerColor = app_background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(ResponsiveLayout.getResponsiveSize(12.dp, 16.dp, 20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsiveSize(16.dp, 20.dp, 24.dp))
        ) {
            CustomLabel(
                header = ticketId,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.Start)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TicketFlag()
                Row {
                    AppCategoryIcon(
                        painter = painterResource(R.drawable.ic_assigned_time),
                        iconSize = ResponsiveLayout.getResponsiveSize(12.dp, 14.dp, 16.dp),
                    )
                    Spacer(modifier = Modifier.width(ResponsiveLayout.getResponsiveSize(4.dp, 6.dp, 8.dp)))
                    CustomLabel(
                        header = Date().toString(),
                        headerColor = onSurfaceColor,
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .padding(ResponsiveLayout.getResponsiveSize(6.dp, 8.dp, 10.dp))
                    .wrapContentSize()
                    .clip(Shapes.CardShape)
                    .border(ResponsiveLayout.getResponsiveSize(1.dp, 1.5.dp, 2.dp), cardColor)
            ) {
                Column(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(ResponsiveLayout.getResponsiveSize(10.dp, 12.dp, 14.dp)),
//                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(ResponsiveLayout.getResponsiveSize(8.dp, 10.dp, 12.dp))
                ) {
                    CustomLabel(
                        header = "Projector in 301A not working",
                        headerColor = onSurfaceColor.copy(0.8f),
                        fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CustomLabel(
                            header = "Deadline",
                            headerColor = onSurfaceColor.copy(0.8f),
                            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
                        )
                        CustomLabel(
                            header = "28 Oct, 2025",
                            headerColor = onSurfaceColor.copy(0.8f),
                            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CustomLabel(
                            header = "Requested by",
                            headerColor = onSurfaceColor.copy(0.8f),
                            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
                        )
                        CustomLabel(
                            header = "S.K. Shukla",
                            headerColor = onSurfaceColor.copy(0.8f),
                            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        CustomLabel(
                            header = "Assignee",
                            headerColor = onSurfaceColor.copy(0.8f),
                            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
                        )
                        CustomLabel(
                            header = "V. Verma",
                            headerColor = onSurfaceColor.copy(0.8f),
                            fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
                        )
                    }
                }

            }
        }
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row {
                AppCategoryIcon(
                    painter = painterResource(R.drawable.ic_ticket_thread),
                    iconSize = ResponsiveLayout.getResponsiveSize(12.dp, 14.dp, 16.dp),
                    tint = onSurfaceColor.copy(0.5f)
                )
                CustomLabel(
                    header = count.toString(),
                    headerColor = onSurfaceColor.copy(0.8f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    modifier = Modifier
                )
            }
            CustomLabel(
                header = "See Detail",
                headerColor = onSurfaceColor.copy(0.8f),
                fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp)
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun TicketCardPreview() {
    TicketCard()
}

