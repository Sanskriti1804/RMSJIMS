package com.example.rmsjims.data.remote.api

import android.util.Log
import com.example.rmsjims.data.schema.Items
import com.example.rmsjims.data.remote.apiservice.ItemsApiService
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest

class ItemsApi(
    private val supabaseClient: SupabaseClient
) : ItemsApiService {

    private val table = supabaseClient.postgrest["items"]
    override suspend fun getItems(): List<Items> {
        Log.d("ItemsApi", "Making Supabase query to 'items' table...")
        return try {
            val result = table.select().decodeList<Items>()
            Log.d("ItemsApi", "Supabase query successful, returned ${result.size} items")
            result
        } catch (e: Exception) {
            Log.e("ItemsApi", "Supabase query FAILED", e)
            Log.e("ItemsApi", "Exception type: ${e.javaClass.simpleName}")
            Log.e("ItemsApi", "Exception message: ${e.message}")
            throw e
        }
    }

}