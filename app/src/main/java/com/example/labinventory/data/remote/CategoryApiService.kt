package com.example.labinventory.data.remote

import com.example.labinventory.data.model.Categories

interface CategoryApiService {
    suspend fun getCategories(): List<Categories>
}