package com.example.labinventory.repository

import com.example.labinventory.data.remote.apiservice.ItemImagesApiService
import com.example.labinventory.data.schema.ItemImages


//seperates the data source away from vm - directly implements
class ItemImagesRepository(
    private val apiService: ItemImagesApiService
){
    suspend fun getInventoryItemImages() = apiService.getItems()

    suspend fun getInventoryItemImageById(itemId: Int) = apiService.getItemById(itemId)
    suspend fun addInventoryItemImage(itemImages: ItemImages) = apiService.addItem(itemImages)
    suspend fun deleteInventoryItemImage(itemId: Int) = apiService.deleteItem(itemId)
    suspend fun updateInventoryItemImage(itemImages: ItemImages) = apiService.updateItem(itemImages)
}