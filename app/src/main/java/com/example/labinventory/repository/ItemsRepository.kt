package com.example.labinventory.repository

import com.example.labinventory.data.schema.Items
import com.example.labinventory.data.remote.apiservice.ItemsApiService

class ItemsRepository(
    private val itemsApiService: ItemsApiService
){
    suspend fun fetchItems() : List<Items>{
        return itemsApiService.getItems()
    }
}