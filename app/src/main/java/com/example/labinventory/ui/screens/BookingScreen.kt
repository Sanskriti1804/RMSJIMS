package com.example.labinventory.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.labinventory.R
import com.example.labinventory.data.model.BookingDates
import com.example.labinventory.data.model.BookingTab
import com.example.labinventory.data.model.InChargeInfo
import com.example.labinventory.data.model.ProductInfo
import com.example.labinventory.data.model.Status
import com.example.labinventory.data.model.TabItem
import com.example.labinventory.navigation.Screen
import com.example.labinventory.ui.components.AppNavIcon
import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.components.CustomTopBar
import com.example.labinventory.ui.components.EditButton
import com.example.labinventory.ui.theme.cardColor
import com.example.labinventory.ui.theme.categoryColor
import com.example.labinventory.ui.theme.categoryIconColor
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.someGrayColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp
import com.example.labinventory.viewmodel.BookingScreenViewmodel
import org.koin.androidx.compose.koinViewModel


@Composable
fun BookingScreen(
    navController: NavHostController,
    viewModel: BookingScreenViewmodel = koinViewModel()
) {
    Scaffold(
        topBar = {
            CustomTopBar(title = "Bookings")
        }
    ) { paddingValues ->

        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues)) {

            BookingTabSelector(
                tabs = listOf(
                    TabItem(BookingTab.Booking_Requests, "Booking Requests", R.drawable.ic_booking_pending, isSelected = true),
                    TabItem(BookingTab.Verified_Bookings, "Approved", R.drawable.ic_booking_verified, isSelected = false),
                    TabItem(BookingTab.Canceled_Bookings, "Rejected", R.drawable.ic_booking_canceled, isSelected = false),
                ),
                onTabSelected = { selectedTab -> }
            )

            Spacer(modifier = Modifier.height(16.dp))

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
            .fillMaxWidth()
            .padding(start = pxToDp(25), end = pxToDp(25), top = pxToDp(4), bottom = pxToDp(12)),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        tabs.forEach {
            Column(
                modifier = Modifier
                    .clickable { onTabSelected(it.tab) }
                    .weight(1f)
                    .background(if (it.isSelected) highlightColor.copy(0.2f) else Color.Transparent)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(pxToDp(10))
            ) {
                AppNavIcon(
                    painter = painterResource(id = it.iconRes),
                    iconDescription = it.label,
                    iconSize = pxToDp(20),
                    tint = if (it.isSelected) highlightColor else categoryIconColor
                    )
                CustomLabel(
                    header = it.label,
                    fontSize = 12.sp,
                    headerColor = if (it.isSelected) highlightColor else categoryIconColor
                )
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
    containerColor : Color = cardColor,
    cardPadding : Dp = pxToDp(20)
) {
    Card(
        shape = cardShape,
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {
        Column(modifier = Modifier.padding(cardPadding)) {

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
                        fontSize = 14.sp,
                        modifier = Modifier,
                        headerColor = darkTextColor
                    )
                    CustomLabel(
                        header = productInfo.location,
                        fontSize = 12.sp,
                        modifier = Modifier,
                        headerColor = someGrayColor
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

            Spacer(modifier = Modifier.height(pxToDp(20)))
            Divider(thickness = pxToDp(1), color = someGrayColor)
            Spacer(modifier = Modifier.height(pxToDp(20)))

            Column(verticalArrangement = Arrangement.spacedBy(pxToDp(18))) {
                CustomLabel(
                    header = "InCharge",
                    headerColor = darkTextColor.copy(0.9f)
                )
                InChargeRow(label = "Prof.", name = "Sumant Rao")
                InChargeRow(
                    label = "Asst.",
                    name = "Akash Kumar Swami",
                    icons = listOf(R.drawable.ic_mail, R.drawable.ic_call)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Divider()
            Spacer(modifier = Modifier.height(16.dp))
            Column(verticalArrangement = Arrangement.spacedBy(pxToDp(18))) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = pxToDp(2)),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CustomLabel(
                        header = "Booking Dates",
                        headerColor = darkTextColor.copy(0.9f)
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
fun InChargeRoww(label: String, name: String, icons: List<Int>? = null) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            CustomLabel(
                header = "$label",
                fontSize = 14.sp,
                headerColor = darkTextColor.copy(0.5f),
                modifier = Modifier.width(50.dp)
            )
            CustomLabel(
                header = name,
                fontSize = 14.sp,
                modifier = Modifier,
                headerColor = darkTextColor.copy(0.8f)

            )
        }
        icons?.let {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                it.forEach { iconRes ->
                    Icon(
                        painter = painterResource(id = iconRes),
                        contentDescription = "Contact Icon"
                    )
                }
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
                fontSize = 14.sp,
                headerColor = darkTextColor.copy(0.5f),
                modifier = Modifier
                    .width(80.dp)
            )
            CustomLabel(
                header = name,
                fontSize = 14.sp,
                modifier = Modifier,
                headerColor = darkTextColor.copy(0.8f)

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

