//package com.example.labinventory.di
//
//// This Kotlin file defines a Koin module named `appModule`.
//// Koin is a dependency injection framework for Kotlin.
//// Modules in Koin are used to declare dependencies that can be injected into other parts of the application.
//
//// Importing necessary classes from different packages.
//// These classes represent the dependencies that will be provided by this module.
//import com.example.labinventory.data.remote.InventoryApiService
//import com.example.labinventory.data.remote.SupabaseInventoryApi
//import com.example.labinventory.repository.InventoryRepository
//import com.example.labinventory.viewmodel.InventoryImageViewModel
//import org.koin.dsl.module
//
//val appModule = module{
//    // `single<InventoryApiService>` means that Koin will create only one instance of `InventoryApiService`
//    // throughout the application's lifecycle.
//    // It creates an instance of `SupabaseInventoryApi`.
//    // `get()` within the lambda is a Koin function that resolves and provides any dependencies
//    single<InventoryApiService> { SupabaseInventoryApi(get()) }
//
//    single { InventoryRepository(get()) }
//
//    single { InventoryImageViewModel(get()) }
//}