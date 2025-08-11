package com.example.labinventory.repository

import com.example.labinventory.data.remote.apiservice.ItemSubCategoriesApiService
import com.example.labinventory.data.schema.ItemSubCategories

class ItemSubCategoriesRepository (
    private val itemSubCategoriesApiService: ItemSubCategoriesApiService
){
    suspend fun fetchItemSubCategories() : List<ItemSubCategories>{
        return itemSubCategoriesApiService.getItemSubCategories()
    }
}