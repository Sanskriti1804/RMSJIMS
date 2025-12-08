package com.example.rmsjims.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.R
import com.example.rmsjims.data.local.BookingDatesManager
import com.example.rmsjims.data.model.BookingDates
import com.example.rmsjims.data.model.BookingItem
import com.example.rmsjims.data.model.BookingPriority
import com.example.rmsjims.data.model.BookingStatus
import com.example.rmsjims.data.model.BookingTab
import com.example.rmsjims.data.model.InChargeInfo
import com.example.rmsjims.data.model.ProductInfo
import com.example.rmsjims.data.model.Status
import com.example.rmsjims.data.model.TabItem
import com.example.rmsjims.data.schema.Booking
import com.example.rmsjims.repository.BookingRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for BookingScreen UI compatibility
 * Integrates with BookingRepository to fetch real bookings from database
 */
class BookingScreenViewmodel(
    application: Application,
    private val bookingRepository: BookingRepository
) : AndroidViewModel(application) {

    private val bookingDatesManager = BookingDatesManager(application)

    private val _tabs = mutableStateListOf(
        TabItem(BookingTab.Booking_Requests, "Booking Requests", R.drawable.ic_booking_pending, true),
        TabItem(BookingTab.Verified_Bookings, "Approved Bookings", R.drawable.ic_booking_verified, false),
        TabItem(BookingTab.Canceled_Bookings, "Rejected Bookings", R.drawable.ic_booking_canceled, false)
    )
    val tabs: List<TabItem> = _tabs

    var selectedTab by mutableStateOf(BookingTab.Booking_Requests)
        private set

    var selectedBookingDates by mutableStateOf<BookingDates?>(null)
        private set

    private val _allBookings = mutableStateListOf<Booking>()
    val allBookings: List<Booking> = _allBookings

    init {
        loadBookings()
    }

    fun loadBookings() {
        viewModelScope.launch {
            try {
                val bookings = bookingRepository.getAllBookings()
                _allBookings.clear()
                _allBookings.addAll(bookings)
                Log.d("BookingScreenViewmodel", "Loaded ${bookings.size} bookings from database")
            } catch (e: Exception) {
                Log.e("BookingScreenViewmodel", "Error loading bookings", e)
            }
        }
    }

    fun updateSelectedBookingDates(equipmentTitle: String, dates: BookingDates) {
        selectedBookingDates = dates
        bookingDatesManager.saveBookingDates(equipmentTitle, dates)
    }

    // Convert database Booking to UI BookingItem format
    val filteredBookings: List<BookingItem>
        get() {
            val statusFilter = when (selectedTab) {
                BookingTab.Booking_Requests -> "pending"
                BookingTab.Verified_Bookings -> "approved"
                BookingTab.Canceled_Bookings -> "rejected"
            }
            
            val filtered = _allBookings.filter { it.status == statusFilter }
            
            return filtered.map { booking ->
                BookingItem(
                    id = booking.id?.toString() ?: "0",
                    productInfo = ProductInfo(
                        imageRes = R.drawable.temp, // Default image
                        title = booking.productName ?: booking.projectName, // Show product name or project name
                        code = booking.equipmentId?.toString() ?: "N/A",
                        location = booking.department ?: booking.productDescription ?: "N/A",
                        status = when (booking.status) {
                            "pending" -> Status.PENDING
                            "approved" -> Status.BOOKED
                            "rejected" -> Status.CANCELLED
                            else -> Status.PENDING
                        }
                    ),
                    inChargeInfo = InChargeInfo(
                        profName = booking.bookerName, // Show booker name
                        asstName = booking.guideName ?: "N/A",
                        asstIcons = emptyList()
                    ),
                    bookingDates = BookingDates(
                        fromDate = booking.bookingDate ?: booking.startDate ?: "",
                        toDate = booking.endDate ?: booking.bookingDate ?: ""
                    ),
                    bookingStatus = when (booking.status) {
                        "pending" -> BookingStatus.VERIFICATION_PENDING
                        "approved" -> BookingStatus.APPROVED
                        "rejected" -> BookingStatus.REJECTED
                        else -> BookingStatus.VERIFICATION_PENDING
                    },
                    priority = BookingPriority.MEDIUM, // Default priority
                    rejectionReason = booking.rejectionReason
                )
            }
        }

    fun onTabSelect(tab: BookingTab) {
        selectedTab = tab
        _tabs.replaceAll { it.copy(isSelected = it.tab == tab) }
        // Reload bookings when tab changes
        loadBookings()
    }
}

