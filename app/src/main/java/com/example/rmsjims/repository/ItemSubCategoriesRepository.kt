package com.example.rmsjims.repository

import com.example.rmsjims.data.remote.apiservice.ItemSubCategoriesApiService
import com.example.rmsjims.data.schema.ItemSubCategories

class ItemSubCategoriesRepository (
    private val itemSubCategoriesApiService: ItemSubCategoriesApiService
){
    suspend fun fetchItemSubCategories() : List<ItemSubCategories>{
        return itemSubCategoriesApiService.getItemSubCategories()
    }
}