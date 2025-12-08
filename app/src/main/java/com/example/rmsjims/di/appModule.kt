package com.example.rmsjims.di

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.rmsjims.data.remote.apiservice.BranchesApiService
import com.example.rmsjims.data.remote.apiservice.ItemCategoriesApiService
import com.example.rmsjims.data.remote.apiservice.DepartmentApiService
import com.example.rmsjims.data.remote.apiservice.ItemsApiService
import com.example.rmsjims.data.remote.SessionManager
import com.example.rmsjims.data.remote.api.BranchesApi
import com.example.rmsjims.data.remote.api.ItemCategoriesApi
import com.example.rmsjims.data.remote.api.DepartmentApi
import com.example.rmsjims.data.remote.api.FacilitiesApi
import com.example.rmsjims.data.remote.api.ItemImagesApi
import com.example.rmsjims.data.remote.api.ItemSubCategoriesApi
import com.example.rmsjims.data.remote.api.ItemsApi
import com.example.rmsjims.data.remote.api.UsersApi
import com.example.rmsjims.data.remote.apiservice.FacilitesApiService
import com.example.rmsjims.data.remote.apiservice.ItemImagesApiService
import com.example.rmsjims.data.remote.apiservice.ItemSubCategoriesApiService
import com.example.rmsjims.data.remote.apiservice.UsersApiService
import com.example.rmsjims.data.remote.apiservice.BuildingsApiService
import com.example.rmsjims.data.remote.apiservice.DepartmentsApiService
import com.example.rmsjims.data.remote.apiservice.RoomsApiService
import com.example.rmsjims.data.remote.apiservice.EquipmentApiService
import com.example.rmsjims.data.remote.apiservice.TicketsApiService
import com.example.rmsjims.data.remote.apiservice.BookingApiService
import com.example.rmsjims.data.remote.api.BuildingsApi
import com.example.rmsjims.data.remote.api.DepartmentsApi
import com.example.rmsjims.data.remote.api.RoomsApi
import com.example.rmsjims.data.remote.api.EquipmentApi
import com.example.rmsjims.data.remote.api.TicketsApi
import com.example.rmsjims.data.remote.api.BookingApi
import com.example.rmsjims.repository.AuthRepository
import com.example.rmsjims.repository.BranchRepository
import com.example.rmsjims.repository.ItemCategoriesRepository
import com.example.rmsjims.repository.DepartmentRepository
import com.example.rmsjims.repository.FacilitiesRepository
import com.example.rmsjims.repository.ItemImagesRepository
import com.example.rmsjims.repository.ItemSubCategoriesRepository
import com.example.rmsjims.repository.ItemsRepository
import com.example.rmsjims.repository.UsersRepository
import com.example.rmsjims.repository.BuildingsRepository
import com.example.rmsjims.repository.DepartmentsRepository
import com.example.rmsjims.repository.RoomsRepository
import com.example.rmsjims.repository.EquipmentRepository
import com.example.rmsjims.repository.TicketsRepository
import com.example.rmsjims.repository.BookingRepository
import com.example.rmsjims.viewmodel.AuthViewModel
import com.example.rmsjims.viewmodel.BookingScreenViewmodel
import com.example.rmsjims.viewmodel.BranchViewModel
import com.example.rmsjims.viewmodel.CalendarViewModel
import com.example.rmsjims.viewmodel.ItemCategoriesViewModel
import com.example.rmsjims.viewmodel.DepartmentViewModel
import com.example.rmsjims.viewmodel.FacilitiesViewModel
import com.example.rmsjims.viewmodel.FilterSortViewModel
import com.example.rmsjims.viewmodel.ItemImagesViewModel
import com.example.rmsjims.viewmodel.ItemSubCategoriesViewModel
import com.example.rmsjims.viewmodel.ItemsViewModel
import com.example.rmsjims.viewmodel.SearchViewModel
import com.example.rmsjims.viewmodel.UserSessionViewModel
import com.example.rmsjims.viewmodel.BuildingsViewModel
import com.example.rmsjims.viewmodel.DepartmentsViewModel
import com.example.rmsjims.viewmodel.RoomsViewModel
import com.example.rmsjims.viewmodel.EquipmentViewModel
import com.example.rmsjims.viewmodel.TicketsViewModel
import com.example.rmsjims.viewmodel.BookingViewModel
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
    single<UsersApiService> { UsersApi(get()) }
    single<BuildingsApiService> { BuildingsApi(get()) }
    single<DepartmentsApiService> { DepartmentsApi(get()) }
    single<RoomsApiService> { RoomsApi(get()) }
    single<EquipmentApiService> { EquipmentApi(get()) }
    single<TicketsApiService> { TicketsApi(get()) }
    single<BookingApiService> { BookingApi(get()) }

    single { BranchRepository (get()) }
    single { AuthRepository() }
    single { ItemCategoriesRepository(get()) }
    single { ItemsRepository(get()) }
    single { DepartmentRepository(get()) }
    single { FacilitiesRepository(get()) }
    single { ItemImagesRepository(get()) }
    single { ItemSubCategoriesRepository(get()) }
    single { UsersRepository(get()) }
    single { BuildingsRepository(get()) }
    single { DepartmentsRepository(get()) }
    single { RoomsRepository(get()) }
    single { EquipmentRepository(get()) }
    single { TicketsRepository(get()) }
    single { BookingRepository(get()) }

//    // ViewModels
    viewModel { ItemCategoriesViewModel(get()) }
    viewModel { ItemsViewModel(get()) }
    viewModel { ItemSubCategoriesViewModel(get()) }
    viewModel { ItemImagesViewModel(get()) }
    viewModel { UserSessionViewModel(get()) }
    viewModel { BookingScreenViewmodel(get(), get()) }
    viewModel { CalendarViewModel() }
    viewModel { FilterSortViewModel() }
    viewModel { SearchViewModel() }
    viewModel { BranchViewModel(get()) }
    viewModel { FacilitiesViewModel(get()) }
    viewModel { DepartmentViewModel(get()) }
    viewModel { AuthViewModel(get()) }
    viewModel { BuildingsViewModel(get()) }
    viewModel { DepartmentsViewModel(get()) }
    viewModel { RoomsViewModel(get()) }
    viewModel { EquipmentViewModel(get()) }
    viewModel { TicketsViewModel(get()) }
    viewModel { BookingViewModel(get()) }
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