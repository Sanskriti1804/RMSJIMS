package com.example.rmsjims.data.remote.api

import com.example.rmsjims.data.schema.Items
import com.example.rmsjims.data.remote.apiservice.ItemsApiService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class ItemsApi(
    private val supabaseClient: SupabaseClient
) : ItemsApiService {

    private val table = supabaseClient.postgrest["items"]
    override suspend fun getItems(): List<Items> {
        return table.select().decodeList()
    }

}