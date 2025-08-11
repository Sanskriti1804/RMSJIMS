package com.example.labinventory.data.remote.apiservice

import com.example.labinventory.data.schema.Items

interface ItemsApiService {
    suspend fun getItems(): List<Items>
}