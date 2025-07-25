package com.example.labinventory.data.remote

import com.example.labinventory.data.model.InventoryItemImages

//what operations the data source must provide?
interface InventoryApiService {
    suspend fun getItems() : List<InventoryItemImages>
    suspend fun getItemById(itemId: Int) : InventoryItemImages?
    suspend fun addItem(itemImages: InventoryItemImages) : InventoryItemImages
    suspend fun deleteItem(itemId : Int) : Boolean
    suspend fun updateItem(itemImages: InventoryItemImages) : InventoryItemImages
}