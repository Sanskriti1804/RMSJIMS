package com.example.labinventory.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.labinventory.ui.theme.onSurfaceVariant
import com.example.labinventory.ui.theme.titleColor
import com.example.labinventory.util.ResponsiveLayout

/**
 * Responsive card component that adapts to different screen sizes
 * while maintaining the existing UI style
 */
@Composable
fun ResponsiveCard(
    title: String,
    subtitle: String? = null,
    onClick: (() -> Unit)? = null,
    containerColor: Color = onSurfaceVariant,
    shape: Shape = RoundedCornerShape(8.dp),
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(ResponsiveLayout.getResponsiveSize(110.dp, 160.dp, 200.dp)),
        onClick = onClick ?: {},
        shape = shape,
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ResponsiveLayout.getResponsiveSize(2.dp, 3.dp, 4.dp)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 24.dp, 32.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    color = titleColor,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                    textAlign = TextAlign.Start
                )
                
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = titleColor.copy(alpha = 0.7f),
                        fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                        textAlign = TextAlign.Start,
                        modifier = Modifier.padding(
                            top = ResponsiveLayout.getResponsivePadding(4.dp, 6.dp, 8.dp)
                        )
                    )
                }
            }
        }
    }
}

/**
 * Responsive grid card that adapts to different screen sizes
 */
@Composable
fun ResponsiveGridCard(
    title: String,
    onClick: (() -> Unit)? = null,
    containerColor: Color = onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(ResponsiveLayout.getResponsiveSize(120.dp, 180.dp, 240.dp)),
        onClick = onClick ?: {},
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ResponsiveLayout.getResponsiveSize(2.dp, 3.dp, 4.dp)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 24.dp, 32.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                color = titleColor,
                fontSize = ResponsiveLayout.getResponsiveFontSize(18.sp, 22.sp, 26.sp),
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Responsive list card for different screen sizes
 */
@Composable
fun ResponsiveListCard(
    title: String,
    description: String,
    onClick: (() -> Unit)? = null,
    containerColor: Color = onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(ResponsiveLayout.getResponsiveSize(80.dp, 100.dp, 120.dp)),
        onClick = onClick ?: {},
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ResponsiveLayout.getResponsiveSize(1.dp, 2.dp, 3.dp)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(12.dp, 16.dp, 20.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = title,
                    color = titleColor,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(14.sp, 16.sp, 18.sp),
                    textAlign = TextAlign.Start
                )
                
                Text(
                    text = description,
                    color = titleColor.copy(alpha = 0.6f),
                    fontSize = ResponsiveLayout.getResponsiveFontSize(12.sp, 14.sp, 16.sp),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.padding(
                        top = ResponsiveLayout.getResponsivePadding(2.dp, 4.dp, 6.dp)
                    )
                )
            }
        }
    }
}

/**
 * Responsive action card with button
 */
@Composable
fun ResponsiveActionCard(
    title: String,
    actionText: String,
    onClick: () -> Unit,
    containerColor: Color = onSurfaceVariant,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(ResponsiveLayout.getResponsiveSize(100.dp, 140.dp, 180.dp)),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = ResponsiveLayout.getResponsiveSize(2.dp, 3.dp, 4.dp)
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ResponsiveLayout.getResponsivePadding(16.dp, 24.dp, 32.dp))
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = titleColor,
                    fontSize = ResponsiveLayout.getResponsiveFontSize(16.sp, 18.sp, 20.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(
                        bottom = ResponsiveLayout.getResponsivePadding(8.dp, 12.dp, 16.dp)
                    )
                )
                
                AppButton(
                    buttonText = actionText,
                    onClick = onClick,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
