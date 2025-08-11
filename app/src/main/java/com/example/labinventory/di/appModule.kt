package com.example.labinventory.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.labinventory.data.remote.BranchesApiService
import com.example.labinventory.data.remote.CategoryApiService
import com.example.labinventory.data.remote.ItemsApiService
import com.example.labinventory.data.remote.SessionManager
import com.example.labinventory.data.remote.api.BranchesApi
import com.example.labinventory.data.remote.api.CategoryApi
import com.example.labinventory.data.remote.api.ItemsApi
import com.example.labinventory.repository.BranchRepository
import com.example.labinventory.repository.CategoryRepository
import com.example.labinventory.repository.ItemsRepository
import com.example.labinventory.viewmodel.BookingScreenViewmodel
import com.example.labinventory.viewmodel.BranchViewModel
import com.example.labinventory.viewmodel.CalendarViewModel
import com.example.labinventory.viewmodel.CategoryViewModel
import com.example.labinventory.viewmodel.FilterSortViewModel
import com.example.labinventory.viewmodel.ItemsViewModel
import com.example.labinventory.viewmodel.SearchViewModel
import com.example.labinventory.viewmodel.UserSessionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val appModule = module {

    // API implementation
//    single<InventoryApiService> { InventoryImageApi(get()) }
    single<CategoryApiService> { CategoryApi(get()) }
    single<ItemsApiService> { ItemsApi(get()) }
    single<BranchesApiService> { BranchesApi(get()) }
//    // Repository
//    single { InventoryRepository(get()) }
    single { CategoryRepository(get()) }
    single { ItemsRepository(get()) }
    single { BranchRepository(get()) }
//
//    // ViewModels
    viewModel { CategoryViewModel(get()) }
    viewModel { ItemsViewModel(get()) }
//    viewModel { InventoryImageViewModel(get()) }

    single { SessionManager(get()) }
    viewModel { UserSessionViewModel(get()) }

    viewModel { BookingScreenViewmodel() }
    viewModel { CalendarViewModel() }
    viewModel { FilterSortViewModel() }
    viewModel { SearchViewModel() }
    viewModel { BranchViewModel(get()) }
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