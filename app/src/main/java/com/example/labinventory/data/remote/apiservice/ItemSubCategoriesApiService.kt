package com.example.labinventory.data.remote.apiservice

import androidx.compose.runtime.Composable
import com.example.labinventory.data.schema.ItemSubCategories

interface ItemSubCategoriesApiService {
    suspend fun getItemSubCategories(): List<ItemSubCategories>
}