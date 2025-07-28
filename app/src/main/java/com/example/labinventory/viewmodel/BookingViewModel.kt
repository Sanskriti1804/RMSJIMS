package com.example.labinventory.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.labinventory.R
import com.example.labinventory.data.model.BookingDates
import com.example.labinventory.data.model.BookingTab
import com.example.labinventory.data.model.InChargeInfo
import com.example.labinventory.data.model.ProductInfo
import com.example.labinventory.data.model.Status
import com.example.labinventory.data.model.TabItem


class InfoTabsViewModel : ViewModel() {

    private val _tabs = mutableStateListOf(
        TabItem(BookingTab.Booking_Requests, "Product", R.drawable.ic_booking_pending, true),
        TabItem(BookingTab.Verified_Bookings, "InCharge", R.drawable.ic_booking_verified, false),
        TabItem(BookingTab.Canceled_Bookings, "Booking", R.drawable.ic_booking_canceled, false)
    )
    val tabs: List<TabItem> = _tabs

    var selectedTab by mutableStateOf(BookingTab.Booking_Requests)
        private set

    val productInfo = ProductInfo(
        R.drawable.temp,
        "Canon EOS R50 V", "IDC", "Photo Studio", Status.PENDING
    )

    val inCharge = InChargeInfo(
        profName = "Sumant Rao",
        asstName = "Akash Kumar Swami",
        asstIcons = listOf(R.drawable.ic_mail, R.drawable.ic_call)
    )

    val bookingDates = BookingDates("2025-08-01", "2025-08-10")

    fun onTabSelect(tab: BookingTab) {
        selectedTab = tab
        _tabs.replaceAll { it.copy(isSelected = it.tab == tab) }
    }
}
