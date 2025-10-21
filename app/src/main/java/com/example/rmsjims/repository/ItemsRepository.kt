package com.example.rmsjims.repository

import com.example.rmsjims.data.schema.Items
import com.example.rmsjims.data.remote.apiservice.ItemsApiService

class ItemsRepository(
    private val itemsApiService: ItemsApiService
){
    suspend fun fetchItems() : List<Items>{
        return itemsApiService.getItems()
    }
}