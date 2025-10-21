package com.example.rmsjims.repository

import com.example.rmsjims.data.remote.apiservice.ItemImagesApiService
import com.example.rmsjims.data.schema.ItemImages


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