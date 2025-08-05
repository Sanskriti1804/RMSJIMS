package com.example.labinventory.repository

import com.example.labinventory.data.model.Items
import com.example.labinventory.data.remote.ItemsApiService

class ItemsRepository(
    private val itemsApiService: ItemsApiService
){
    suspend fun fetchItems() : List<Items>{
        return itemsApiService.getItems()
    }
}