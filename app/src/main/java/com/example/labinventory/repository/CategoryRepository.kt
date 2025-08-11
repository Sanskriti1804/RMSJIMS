package com.example.labinventory.repository

import com.example.labinventory.data.schema.Categories
import com.example.labinventory.data.remote.CategoryApiService

class CategoryRepository(
    private val categoryApiService: CategoryApiService
){
    suspend fun fetchCategories() : List<Categories>{
        return categoryApiService.getCategories()
    }
}