package com.example.labinventory.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import com.example.labinventory.R
import com.example.labinventory.data.model.BookingDates
import com.example.labinventory.data.model.BookingTab
import com.example.labinventory.data.model.InChargeInfo
import com.example.labinventory.data.model.ProductInfo
import com.example.labinventory.data.model.Status
import com.example.labinventory.data.model.TabItem
import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.theme.cardColor
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.someGrayColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp
import com.example.labinventory.viewmodel.BookingScreenViewmodel


@Composable
fun BookingScreen(viewModel: BookingScreenViewmodel) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            InfoCard(
                productInfo = viewModel.productInfo,
                inChargeInfo = viewModel.inCharge,
                bookingDates = viewModel.bookingDates,
                onEditBooking = {}
            )
        }
    }
}


//endregion
@Composable
fun BookingTabSelector(tabs: List<TabItem>, onTabSelected: (BookingTab) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        tabs.forEach {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onTabSelected(it.tab) }
                    .background(if (it.isSelected) highlightColor.copy(0.2f) else Color.Transparent)
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(painterResource(id = it.iconRes), contentDescription = it.label)
                Text(it.label, color = if (it.isSelected) highlightColor else Color.Gray)
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
                    modifier = Modifier
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

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Booking Dates", style = MaterialTheme.typography.titleSmall)

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("From: ${bookingDates.fromDate}")
                    Button(onClick = onEditBooking) { Text("Edit") }
                }

                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                    Text("To: ${bookingDates.toDate}")
                }
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
            Text(
                "$label:",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.width(50.dp)
            )
            Text(name, style = MaterialTheme.typography.bodyMedium)
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
    InfoCard(
        productInfo = ProductInfo(
            title = "3D Printer",
            code = "EQP-002",
            location = "Lab A1",
            imageRes = R.drawable.ic_mail,
            status = Status.PENDING,
        ),
        inChargeInfo = InChargeInfo(
            profName = "Dr. Mehta",
            asstName = "Priya Singh",
            asstIcons = listOf(R.drawable.ic_mail, R.drawable.ic_call)
        ),
        bookingDates = BookingDates(
            fromDate = "12 Aug 2025",
            toDate = "16 Aug 2025"
        ),
        onEditBooking = {}
    )
}

