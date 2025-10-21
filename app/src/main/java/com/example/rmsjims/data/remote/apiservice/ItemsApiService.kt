package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.Items

interface ItemsApiService {
    suspend fun getItems(): List<Items>
}