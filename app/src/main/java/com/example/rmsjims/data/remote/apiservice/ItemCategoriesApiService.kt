package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.ItemCategories

interface ItemCategoriesApiService {
    suspend fun getCategories(): List<ItemCategories>
}