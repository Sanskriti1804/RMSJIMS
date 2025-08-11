package com.example.labinventory.data.remote

import com.example.labinventory.data.schema.Categories

interface CategoryApiService {
    suspend fun getCategories(): List<Categories>
}