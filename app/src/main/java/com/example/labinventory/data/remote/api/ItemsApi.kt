package com.example.labinventory.data.remote.api

import com.example.labinventory.data.model.Items
import com.example.labinventory.data.remote.ItemsApiService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.network.SupabaseApi
import io.github.jan.supabase.postgrest.postgrest

class ItemsApi(
    private val supabaseClient: SupabaseClient
) : ItemsApiService {

    private val table = supabaseClient.postgrest["items"]
    override suspend fun getItems(): List<Items> {
        return table.select().decodeList()
    }

}