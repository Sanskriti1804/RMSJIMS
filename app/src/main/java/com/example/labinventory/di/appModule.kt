package com.example.labinventory.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.labinventory.data.remote.apiservice.BranchesApiService
import com.example.labinventory.data.remote.apiservice.ItemCategoriesApiService
import com.example.labinventory.data.remote.apiservice.DepartmentApiService
import com.example.labinventory.data.remote.apiservice.ItemsApiService
import com.example.labinventory.data.remote.SessionManager
import com.example.labinventory.data.remote.api.BranchesApi
import com.example.labinventory.data.remote.api.ItemCategoriesApi
import com.example.labinventory.data.remote.api.DepartmentApi
import com.example.labinventory.data.remote.api.FacilitiesApi
import com.example.labinventory.data.remote.api.ItemImagesApi
import com.example.labinventory.data.remote.api.ItemSubCategoriesApi
import com.example.labinventory.data.remote.api.ItemsApi
import com.example.labinventory.data.remote.apiservice.FacilitesApiService
import com.example.labinventory.data.remote.apiservice.ItemImagesApiService
import com.example.labinventory.data.remote.apiservice.ItemSubCategoriesApiService
import com.example.labinventory.data.schema.ItemSubCategories
import com.example.labinventory.repository.BranchRepository
import com.example.labinventory.repository.ItemCategoriesRepository
import com.example.labinventory.repository.DepartmentRepository
import com.example.labinventory.repository.FacilitiesRepository
import com.example.labinventory.repository.ItemImagesRepository
import com.example.labinventory.repository.ItemSubCategoriesRepository
import com.example.labinventory.repository.ItemsRepository
import com.example.labinventory.viewmodel.BookingScreenViewmodel
import com.example.labinventory.viewmodel.BranchViewModel
import com.example.labinventory.viewmodel.CalendarViewModel
import com.example.labinventory.viewmodel.ItemCategoriesViewModel
import com.example.labinventory.viewmodel.DepartmentViewModel
import com.example.labinventory.viewmodel.FacilitiesViewModel
import com.example.labinventory.viewmodel.FilterSortViewModel
import com.example.labinventory.viewmodel.ItemImagesViewModel
import com.example.labinventory.viewmodel.ItemSubCategoriesViewModel
import com.example.labinventory.viewmodel.ItemsViewModel
import com.example.labinventory.viewmodel.SearchViewModel
import com.example.labinventory.viewmodel.UserSessionViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

@RequiresApi(Build.VERSION_CODES.O)
val appModule = module {

    single { SessionManager(get()) }

    // API implementation
//    single<InventoryApiService> { InventoryImageApi(get()) }
    single<ItemCategoriesApiService> { ItemCategoriesApi(get()) }
    single<ItemsApiService> { ItemsApi(get()) }
    single<BranchesApiService> { BranchesApi(get()) }
    single<DepartmentApiService> { DepartmentApi(get()) }
    single<FacilitesApiService> { FacilitiesApi(get()) }
    single<ItemImagesApiService> { ItemImagesApi(get()) }
    single<ItemSubCategoriesApiService> { ItemSubCategoriesApi(get()) }

    single { BranchRepository (get()) }
    single { ItemCategoriesRepository(get()) }
    single { ItemsRepository(get()) }
    single { DepartmentRepository(get()) }
    single { FacilitiesRepository(get()) }
    single { ItemImagesRepository(get()) }
    single { ItemSubCategoriesRepository(get()) }

//    // ViewModels
    viewModel { ItemCategoriesViewModel(get()) }
    viewModel { ItemsViewModel(get()) }
    viewModel { ItemSubCategoriesViewModel(get()) }
    viewModel { ItemImagesViewModel(get()) }
    viewModel { UserSessionViewModel(get()) }
    viewModel { BookingScreenViewmodel() }
    viewModel { CalendarViewModel() }
    viewModel { FilterSortViewModel() }
    viewModel { SearchViewModel() }
    viewModel { BranchViewModel(get()) }
    viewModel { FacilitiesViewModel(get()) }
    viewModel { DepartmentViewModel(get()) }
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