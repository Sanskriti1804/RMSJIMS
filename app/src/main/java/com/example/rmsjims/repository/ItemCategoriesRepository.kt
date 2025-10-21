package com.example.rmsjims.repository

import com.example.rmsjims.data.schema.ItemCategories
import com.example.rmsjims.data.remote.apiservice.ItemCategoriesApiService

class ItemCategoriesRepository(
    private val categoryApiService: ItemCategoriesApiService
){
    suspend fun fetchCategories() : List<ItemCategories>{
        return categoryApiService.getCategories()
    }
}