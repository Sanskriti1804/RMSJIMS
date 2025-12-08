package com.example.rmsjims.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import android.content.Intent
import android.net.Uri
import androidx.navigation.NavHostController
import com.example.rmsjims.R
import com.example.rmsjims.data.model.BookingDates
import com.example.rmsjims.data.model.BookingTab
import com.example.rmsjims.data.model.InChargeInfo
import com.example.rmsjims.data.model.ProductInfo
import com.example.rmsjims.data.model.TabItem
import com.example.rmsjims.navigation.Screen
import com.example.rmsjims.ui.components.AppNavIcon
import com.example.rmsjims.ui.components.AppCircularIcon
import com.example.rmsjims.ui.components.CustomLabel
import com.example.rmsjims.ui.components.CustomNavigationBar
import com.example.rmsjims.ui.components.CustomTopBar
import com.example.rmsjims.ui.components.EditButton
import com.example.rmsjims.ui.theme.onSurfaceVariant
import com.example.rmsjims.ui.theme.categoryIconColor
import com.example.rmsjims.ui.theme.onSurfaceColor
import com.example.rmsjims.ui.theme.primaryColor
import com.example.rmsjims.ui.theme.cardColor
import com.example.rmsjims.ui.theme.whiteColor
import com.example.rmsjims.ui.theme.circularBoxColor
import com.example.rmsjims.util.pxToDp
import com.example.rmsjims.util.ResponsiveLayout
import com.example.rmsjims.viewmodel.BookingScreenViewmodel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BookingScreen(
    navController: NavHostController,
    viewModel: BookingScreenViewmodel = koinViewModel()
) {
    Scaffold(
        topBar = {
            CustomTopBar(title = "Bookings")
        },
        bottomBar = {
            CustomNavigationBar(navController = navController)
        },
    ) { paddingValues ->

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(ResponsiveLayout.getHorizontalPadding())
            .padding(paddingValues)) {
            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(27.dp, 32.dp, 38.dp)))

            BookingTabSelector(
                tabs = listOf(
                    TabItem(BookingTab.Booking_Requests, "Booking Requests", R.drawable.ic_booking_pending, isSelected = true),
                    TabItem(BookingTab.Verified_Bookings, "Approved Bookings", R.drawable.ic_booking_verified, isSelected = false),
                    TabItem(BookingTab.Canceled_Bookings, "Rejected Bookings", R.drawable.ic_booking_canceled, isSelected = false),
                ),
                onTabSelected = { selectedTab -> }
            )

            Spacer(modifier = Modifier.height(ResponsiveLayout.getResponsivePadding(16.dp, 20.dp, 24.dp)))

            InfoCard(
                productInfo = viewModel.productInfo,
                inChargeInfo = viewModel.inCharge,
                bookingDates = viewModel.bookingDates,
                onEditBooking = { navController.navigate(Screen.CalendarScreen.route)}
            )
        }
    }
}


//endregion
@Composable
fun BookingTabSelector(tabs: List<TabItem>, onTabSelected: (BookingTab) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        tabs.forEach {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp) // apply padding here, outside the clickable column
                    .clickable { onTabSelected(it.tab) }
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(pxToDp(10))
                ) {
                    AppNavIcon(
                        painter = painterResource(id = it.iconRes),
                        iconDescription = it.label,
                        iconSize = pxToDp(20),
                        tint = if (it.isSelected) primaryColor else categoryIconColor
                    )
                    CustomLabel(
                        header = it.label,
                        fontSize = 12.sp,
                        headerColor = if (it.isSelected) primaryColor else categoryIconColor
                    )
                }
            }

        }
    }
}

@Composable
fun InfoCard(
    productInfo: ProductInfo,
    inChargeInfo: InChargeInfo,
    bookingDates: BookingDates,
    onEditBooking: () -> Unit,
    icons: List<Int> = listOf(R.drawable.ic_mail),
    cardShape: Shape = RectangleShape,
    containerColor : Color = onSurfaceVariant,
) {
    Card(
        shape = cardShape,
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = pxToDp(20), vertical = pxToDp(22)),
            verticalArrangement = Arrangement.spacedBy(pxToDp(20))
        ) {

            Row {
                Image(
                    painter = painterResource(R.drawable.temp),
                    contentDescription = "Product Image",

                    modifier = Modifier.size(pxToDp(76))
                )
                Spacer(modifier = Modifier.width(pxToDp(30)))
                Column(
                    modifier = Modifier.weight(0.8f),
                    verticalArrangement = Arrangement.spacedBy(pxToDp(9))
                ) {
                    CustomLabel(
                        header = productInfo.title,
                        fontSize = 16.sp,
                        modifier = Modifier,
                        headerColor = onSurfaceColor
                    )
                    CustomLabel(
                        header = productInfo.location,
                        fontSize = 12.sp,
                        modifier = Modifier,
                        headerColor = cardColor
                    )
                    CustomLabel(
                        header = productInfo.status.label,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .padding(vertical = pxToDp(2))
                            .background(
                                color = productInfo.status.color,
                                shape = RoundedCornerShape(pxToDp(20))
                            )
                            .padding(horizontal = pxToDp(10), vertical = pxToDp(4)),
                        headerColor = whiteColor
                    )
                }
            }

//            Spacer(modifier = Modifier.height(pxToDp(20)))
            Divider(thickness = pxToDp(1), color = cardColor)
//            Spacer(modifier = Modifier.height(pxToDp(20)))

            Column(verticalArrangement = Arrangement.spacedBy(pxToDp(16))) {
                CustomLabel(
                    header = "InCharge",
                    headerColor = onSurfaceColor.copy(0.9f)
                )
                InChargeRow(
                    label = "Prof.",
                    name = "Sumant Rao",
                    email = "sumant.rao@jims.edu.in"
                )
                InChargeRow(
                    label = "Asst.",
                    name = "Akash Kumar Swami",
                    icons = listOf(R.drawable.ic_mail, R.drawable.ic_call),
                    email = "akash.swami@example.com",
                    phone = "+91 9876543210"
                )
            }

//            Spacer(modifier = Modifier.height(16.dp))
            Divider(thickness = pxToDp(1), color = cardColor)
//            Spacer(modifier = Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(pxToDp(18))) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = pxToDp(2)),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomLabel(
                        header = "Booking Dates",
                        headerColor = onSurfaceColor.copy(0.9f)
                    )
                    EditButton(
                        onClick = onEditBooking
                    )
                }
                DatesRow(label = "From", name = "2025-08-01")
                DatesRow(label = "To", name = "2025-08-10")
            }
        }
    }
}

@Composable
fun InChargeRow(
    label: String,
    name: String,
    icons: List<Int> = listOf(R.drawable.ic_mail),
    email: String? = null,
    phone: String? = null
) {
    val context = LocalContext.current
    
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        CustomLabel(
            header = label,
            headerColor = onSurfaceColor.copy(alpha = 0.5f),
            fontSize = 14.sp,
            modifier = Modifier.weight(0.2f)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(pxToDp(16)),
            modifier = Modifier
                .weight(0.9f)
        ) {
            CustomLabel(
                header = name,
                headerColor = onSurfaceColor.copy(alpha = 0.8f),
                fontSize = 14.sp,
                modifier = Modifier.padding(pxToDp(10))
            )

            icons.forEach { iconRes ->
                val adjustedIconSize = if (iconRes == R.drawable.ic_call) pxToDp(16) else pxToDp(20)
                AppCircularIcon(
                    painter = painterResource(iconRes),
                    boxSize = pxToDp(28),
                    iconPadding = pxToDp(4),
                    iconSize = adjustedIconSize,
                    tint = primaryColor,
                    boxColor = circularBoxColor,
                    onClick = {
                        when (iconRes) {
                            R.drawable.ic_mail -> {
                                val emailAddress = email ?: "sumant.rao@jims.edu.in"
                                // Open mail app with email in 'To' field
                                val intent = Intent(Intent.ACTION_SENDTO).apply {
                                    data = Uri.parse("mailto:$emailAddress")
                                }
                                context.startActivity(intent)
                            }
                            R.drawable.ic_call -> {
                                phone?.let { phoneNumber ->
                                    // Open dialer app with number on keypad
                                    val intent = Intent(Intent.ACTION_DIAL).apply {
                                        data = Uri.parse("tel:$phoneNumber")
                                    }
                                    context.startActivity(intent)
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}


@Composable
fun DatesRow(label: String, name: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomLabel(
                header = "$label",
                fontSize = 16.sp,
                headerColor = onSurfaceColor.copy(0.5f),
                modifier = Modifier
                    .width(80.dp)
            )
            CustomLabel(
                header = name,
                fontSize = 16.sp,
                modifier = Modifier,
                headerColor = onSurfaceColor.copy(0.8f)

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBookingScreen() {
//    BookingScreen()
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewBookingTabSelector() {
//    BookingTabSelector(
//        tabs = listOf(
//            TabItem(BookingTab.Booking_Requests,  "Booking Requests",R.drawable.ic_booking_pending, isSelected = true),
//            TabItem(BookingTab.Booking_Requests, "Booking Requests",R.drawable.ic_booking_pending, isSelected = false),
//            TabItem(BookingTab.Booking_Requests, "Booking Requests",R.drawable.ic_booking_pending, isSelected = false)
//        ),
//        onTabSelected = {}
//    )
//}

@Preview(showBackground = true)
@Composable
fun PreviewInfoCard() {
//
}

