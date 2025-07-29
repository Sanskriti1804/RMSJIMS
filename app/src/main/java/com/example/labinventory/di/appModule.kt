package com.example.labinventory.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.labinventory.viewmodel.BookingScreenViewmodel
import com.example.labinventory.viewmodel.CalendarViewModel
import com.example.labinventory.viewmodel.FilterSortViewModel
import com.example.labinventory.viewmodel.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val appModule = module{

    viewModel { BookingScreenViewmodel() }
    viewModel { CalendarViewModel() }
    viewModel { FilterSortViewModel() }
    viewModel { SearchViewModel() }

}


// `single<InventoryApiService>` means that Koin will create only one instance of `InventoryApiService`
// throughout the application's lifecycle.
// It creates an instance of `SupabaseInventoryApi`.
//    // `get()` within the lambda is a Koin function that resolves and provides any dependencies
//    single<InventoryApiService> { SupabaseInventoryApi(get()) }
//
//    single { InventoryRepository(get()) }
//
//    single { InventoryImageViewModel(get()) }