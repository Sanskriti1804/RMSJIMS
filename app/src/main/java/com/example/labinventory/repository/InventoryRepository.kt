//package com.example.labinventory.repository
//
//import com.example.labinventory.data.schema.InventoryItemImages
//import com.example.labinventory.data.remote.InventoryApiService
//
////seperates the data source away from vm - directly implements
//class InventoryRepository(
//    private val apiService: InventoryApiService
//){
//    suspend fun getInventoryItemImages() = apiService.getItems()
//
//    suspend fun getInventoryItemImageById(itemId: Int) = apiService.getItemById(itemId)
//    suspend fun addInventoryItemImage(itemImages: InventoryItemImages) = apiService.addItem(itemImages)
//    suspend fun deleteInventoryItemImage(itemId: Int) = apiService.deleteItem(itemId)
//    suspend fun updateInventoryItemImage(itemImages: InventoryItemImages) = apiService.updateItem(itemImages)
//}