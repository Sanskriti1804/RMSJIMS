package com.example.rmsjims.data.remote.api

import com.example.rmsjims.data.remote.apiservice.ItemSubCategoriesApiService
import com.example.rmsjims.data.schema.ItemSubCategories
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class ItemSubCategoriesApi(
    private val supabaseClient: SupabaseClient
) : ItemSubCategoriesApiService {

    private val table = supabaseClient.postgrest["item_sub_categories"]
    override suspend fun getItemSubCategories(): List<ItemSubCategories> {
        return table.select().decodeList()
    }
}