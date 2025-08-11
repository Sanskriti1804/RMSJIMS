package com.example.labinventory.data.remote.apiservice

import com.example.labinventory.data.schema.ItemCategories

interface ItemCategoriesApiService {
    suspend fun getCategories(): List<ItemCategories>
}