package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.ItemSubCategories

interface ItemSubCategoriesApiService {
    suspend fun getItemSubCategories(): List<ItemSubCategories>
}