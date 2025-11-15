package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.schema.ItemCategories
import com.example.rmsjims.data.remote.apiservice.ItemCategoriesApiService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class ItemCategoriesApi(
    private val supabaseClient: SupabaseClient
): ItemCategoriesApiService {

    private val table = supabaseClient.postgrest["item_categories"]
    override suspend fun getCategories(): List<ItemCategories> {
        Log.d("ItemCategoriesApi", "Making Supabase query to 'item_categories' table...")
        return try {
            val result = table.select().decodeList<ItemCategories>()
            Log.d("ItemCategoriesApi", "Supabase query successful, returned ${result.size} categories")
            result
        } catch (e: Exception) {
            Log.e("ItemCategoriesApi", "Supabase query FAILED", e)
            Log.e("ItemCategoriesApi", "Exception type: ${e.javaClass.simpleName}")
            Log.e("ItemCategoriesApi", "Exception message: ${e.message}")
            throw e
        }
    }

}