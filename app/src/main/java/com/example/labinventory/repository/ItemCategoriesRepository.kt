package com.example.labinventory.repository

import com.example.labinventory.data.schema.ItemCategories
import com.example.labinventory.data.remote.apiservice.ItemCategoriesApiService

class ItemCategoriesRepository(
    private val categoryApiService: ItemCategoriesApiService
){
    suspend fun fetchCategories() : List<ItemCategories>{
        return categoryApiService.getCategories()
    }
}