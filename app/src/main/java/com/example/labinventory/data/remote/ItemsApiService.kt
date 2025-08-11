package com.example.labinventory.data.remote

import com.example.labinventory.data.schema.Items

interface ItemsApiService {
    suspend fun getItems(): List<Items>
}