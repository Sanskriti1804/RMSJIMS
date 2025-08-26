package com.example.labinventory.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.labinventory.R
import com.example.labinventory.data.model.BookingDates
import com.example.labinventory.data.model.InChargeInfo
import com.example.labinventory.data.model.ProductInfo
import com.example.labinventory.navigation.Screen
import com.example.labinventory.ui.components.AppButton
import com.example.labinventory.ui.components.CustomLabel
import com.example.labinventory.ui.components.CustomTopBar
import com.example.labinventory.ui.components.EditButton
import com.example.labinventory.ui.theme.cardColor
import com.example.labinventory.ui.theme.darkTextColor
import com.example.labinventory.ui.theme.daysColor
import com.example.labinventory.ui.theme.highlightColor
import com.example.labinventory.ui.theme.someGrayColor
import com.example.labinventory.ui.theme.weekendColor
import com.example.labinventory.ui.theme.whiteColor
import com.example.labinventory.util.pxToDp
import com.example.labinventory.viewmodel.BookingScreenViewmodel
import com.example.labinventory.viewmodel.CalendarViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreen(
    navController: NavHostController,
    viewModel: CalendarViewModel = koinViewModel(),
    bookingViewmodel: BookingScreenViewmodel
) {
    val selectedDate = viewModel.selectedDate
    val currentMonth = viewModel.currentMonth
    val dates = viewModel.getMonthDates()
    val months = viewModel.getMonths().take(3)

    val daysOfWeek = remember {
        listOf(
            DayOfWeek.SUNDAY,
            DayOfWeek.MONDAY,
            DayOfWeek.TUESDAY,
            DayOfWeek.WEDNESDAY,
            DayOfWeek.THURSDAY,
            DayOfWeek.FRIDAY,
            DayOfWeek.SATURDAY
        )
    }
    Scaffold(
        topBar = {
            CustomTopBar(
                title = "Booking Dates",
                onNavigationClick = {
                    navController.popBackStack()
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                AppButton(
                    buttonText = "CONFIRM",
                    onClick = {
                        navController.navigate(Screen.ProjectInfoScreen.route)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding( start = 16.dp,
                    end = 16.dp,
                    top = paddingValues.calculateTopPadding(),
                    bottom = 0.dp
                ),
            verticalArrangement = Arrangement.spacedBy(pxToDp(20))
           ) {

            Spacer(modifier = Modifier.height(pxToDp(6)))

            MonthTabRow(
                months = months,
                currentMonth = currentMonth,
                onMonthSelected = { viewModel.setMonth(it) }
            )
            DaysOfWeekHeader(daysOfWeek = daysOfWeek)

            // Calendar Grid
            CalendarGrid(
                dates = dates,
                selectedDate = selectedDate,
                today = LocalDate.now(),
                onDateClick = viewModel::selectDate,
                currentMonth = currentMonth,
            )

            // Status summary card
            StatusCard(
                holidayCount = viewModel.getHolidayCount(),
                bookedDate = viewModel.bookedDate,
                availableDate = viewModel.availableDate,
                bookOnDate = viewModel.bookOnDate,
                today = LocalDate.now()
            )

            //EquipCard
            EquipBookingCard(
                productInfo = bookingViewmodel.productInfo,
            )

        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthTabRow(
    months: List<YearMonth>,
    currentMonth: YearMonth,
    onMonthSelected: (YearMonth) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(pxToDp(12))
    ) {
        items(months) { month ->
            val isSelected = month == currentMonth

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(pxToDp(4)))
                    .background(if (isSelected) highlightColor else cardColor)
                    .clickable { onMonthSelected(month) }
            ) {
                CustomLabel(
                    header = month.month.name.lowercase().replaceFirstChar { it.uppercase() },
                    headerColor = if (isSelected) whiteColor.copy(alpha = 0.9f) else darkTextColor.copy(alpha = 0.9f),
                    modifier = Modifier
                        .padding(horizontal = pxToDp(45), vertical = pxToDp(12)),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysOfWeekHeader(
    daysOfWeek: List<DayOfWeek>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        daysOfWeek.forEach { day ->
            val isWeekend = day == DayOfWeek.SUNDAY || day == DayOfWeek.SATURDAY
            Box(
                modifier = Modifier
                    .weight(1f) ,// <-- same as CalendarGrid columns
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = day.name.first().toString(),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
                    textAlign = TextAlign.Center,
                    color = if (isWeekend) weekendColor else darkTextColor.copy(0.9f)
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarGrid(
    dates: List<LocalDate>, // Should be ordered from Sunday to Saturday in each week
    selectedDate: LocalDate,
    today: LocalDate,
    currentMonth: YearMonth,
    onDateClick: (LocalDate) -> Unit
) {
    val weeks = dates.chunked(7)

    Column(
        verticalArrangement = Arrangement.spacedBy(pxToDp(4))
    ) {
        weeks.forEach { week ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(pxToDp(4))
            ) {
                week.forEach { date ->
                    val isToday = date == today
                    val isSelected = date == selectedDate
                    val inMonth = date.month == selectedDate.month
                    val isWeekend = date.dayOfWeek == DayOfWeek.SUNDAY || date.dayOfWeek == DayOfWeek.SATURDAY

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .width(pxToDp(48))
                            .height(pxToDp(60))
                            .clip(RoundedCornerShape(pxToDp(4)))
                            .background(
                                when {
                                    isSelected -> highlightColor
                                    inMonth -> cardColor
                                    else -> cardColor
                                }
                            )
                            .border(
                                width = when {
                                    isToday -> pxToDp(1)
//                                    !inMonth -> 1.dp
                                    else -> 0.dp
                                },
                                color = when {
                                    isToday -> highlightColor
                                    !inMonth -> Color.Transparent
                                    else -> Color.Transparent
                                },
                                shape = RoundedCornerShape(pxToDp(4))
                            )
                            .clickable { onDateClick(date) }
                    ) {
                        Text(
                            text = date.dayOfMonth.toString(),
                            color = when {
                                isWeekend -> weekendColor
                                inMonth -> darkTextColor
                                else -> daysColor
                            },
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun StatusCard(
    holidayCount: Int,
    bookedDate: LocalDate,
    availableDate: LocalDate,
    bookOnDate: LocalDate,
    today: LocalDate,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults
            .cardColors(containerColor = cardColor),
        modifier = modifier.fillMaxWidth(),
        shape = RectangleShape
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            StatusLabel(
                label = "Holidays",
                value = holidayCount.toString(),
                valueColor = weekendColor
            )
            StatusLabel(
                label = "Booked",
                value = bookedDate.dayOfMonth.toString(),
                valueColor = weekendColor,
                isStrikethrough = true
            )
            StatusLabel(
                label = "Today",
                value = today.dayOfMonth.toString(),
                valueColor = highlightColor
            )
            StatusLabel(
                label = "Available",
                value = availableDate.dayOfMonth.toString(),
                valueColor = darkTextColor
            )
            StatusLabel(
                label = "Book On",
                value = bookOnDate.dayOfMonth.toString(),
                valueColor = whiteColor,
                containerColor = highlightColor,
                isBoxed = true
            )
        }
    }
}


@Composable
fun StatusLabel(
    label: String,
    value: String,
    valueColor: Color,
    containerColor: Color = highlightColor,
    isStrikethrough: Boolean = false,
    isBoxed: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.width(IntrinsicSize.Max)
    ) {
        Box(
            modifier = if (isBoxed) {
                Modifier
                    .background(containerColor)
                    .padding(horizontal = pxToDp(10), vertical = pxToDp(2))
            } else Modifier
        ) {
            Text(
                text = value,
                color = valueColor,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium.copy(
                    textDecoration = if (isStrikethrough) TextDecoration.LineThrough else TextDecoration.None
                )
            )
        }
        Spacer(modifier = Modifier.height(pxToDp(18)))
        Text(
            text = label,
            fontFamily = FontFamily(Font(R.font.font_alliance_regular_two)),
            textAlign = TextAlign.Center,
            color = darkTextColor,
            fontSize = 14.sp
        )
    }
}

@Composable
fun EquipBookingCard(
    productInfo: ProductInfo,
    cardShape: Shape = RectangleShape,
    containerColor : Color = whiteColor,
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

                    modifier = Modifier
                        .size(pxToDp(76))
                )
                Spacer(modifier = Modifier.width(pxToDp(30)))
                Column(
                    modifier = Modifier.weight(0.8f),
                    verticalArrangement = Arrangement.spacedBy(pxToDp(9))
                ) {
                    EquipBookingItem("Item", "Canon EOS R50 V")
                    EquipBookingItem("Location", "IDC School of Design")
                    EquipBookingItem("Timing", "(09:00am - 05:30pm)", highlightColor)
                }
            }
        }
    }
}

@Composable
fun EquipBookingItem(
    label : String,
    value : String,
    headerColor: Color = darkTextColor
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
       CustomLabel(
           header = label,
           headerColor = darkTextColor.copy(0.5f),
           fontSize = 14.sp
       )
       CustomLabel(
           header = value,
           headerColor = headerColor,
           fontSize = 14.sp
       )
    }
}


//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//@Preview(showBackground = true)
//fun CalendarScreenPreview() {
//    CalendarScreen(viewModel = CalendarViewModel())
//}
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun MonthTabRowPreview() {
//    val months = remember { (0..5).map { YearMonth.now().plusMonths(it.toLong()) } }
//    var selectedMonth by remember { mutableStateOf(YearMonth.now()) }
//
//    MonthTabRow(
//        months = months.take(3),
//        currentMonth = selectedMonth,
//        onMonthSelected = { selectedMonth = it }
//    )
//}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true, name = "Days Of Week Header")
//@Composable
//fun DaysOfWeekHeaderPreview() {
//    val daysOfWeek = listOf(
//        DayOfWeek.SUNDAY,
//        DayOfWeek.MONDAY,
//        DayOfWeek.TUESDAY,
//        DayOfWeek.WEDNESDAY,
//        DayOfWeek.THURSDAY,
//        DayOfWeek.FRIDAY,
//        DayOfWeek.SATURDAY
//    )
//
//    MaterialTheme {
//        Surface(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
//            DaysOfWeekHeader(
//                daysOfWeek = daysOfWeek
//            )
//        }
//    }
//}


//@RequiresApi(Build.VERSION_CODES.O)
//@Composable
//@Preview(showBackground = true)
//fun CalendarGridPreview() {
//    val sampleDates = generateSequence(LocalDate.now().withDayOfMonth(1)) { it.plusDays(1) }
//        .take(42) // 6 weeks
//        .toList()
//
//    CalendarGrid(
//        dates = sampleDates,
//        selectedDate = LocalDate.now().plusDays(3),
//        today = LocalDate.now(),
//        onDateClick = {}
//    )
//}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun StatusCardPreview() {
    StatusCard(
        holidayCount = 4,
        bookedDate = LocalDate.now().minusDays(2),
        today = LocalDate.now(),
        availableDate = LocalDate.now().plusDays(3),
        bookOnDate = LocalDate.now().plusDays(5)
    )
}

//@Composable
//@Preview(showBackground = true)
//fun StatusLabelPreview() {
//    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
//        StatusLabel("Booked", "2025-08-01", isStrikethrough = true)
//        StatusLabel("Today", "2025-07-28")
//    }
//}
