package com.example.rmsjims.data.remote.api

import com.example.rmsjims.data.schema.ItemCategories
import com.example.rmsjims.data.remote.apiservice.ItemCategoriesApiService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class ItemCategoriesApi(
    private val supabaseClient: SupabaseClient
): ItemCategoriesApiService {

    private val table = supabaseClient.postgrest["item_categories"]
    override suspend fun getCategories(): List<ItemCategories> {
        return table.select().decodeList()
    }

}