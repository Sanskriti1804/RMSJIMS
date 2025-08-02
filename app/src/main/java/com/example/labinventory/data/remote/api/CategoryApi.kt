//package com.example.labinventory.data.remote.api
//
//import com.example.labinventory.data.model.Categories
//import com.example.labinventory.data.remote.CategoryApiService
//import io.github.jan.supabase.SupabaseClient
//import io.github.jan.supabase.postgrest.postgrest
//
//class CategoryApi(
//    private val supabaseClient: SupabaseClient
//): CategoryApiService {
//
//    private val table = supabaseClient.postgrest["item_categories"]
//    override suspend fun getCategories(): List<Categories> {
//        return table.select().decodeList()
//    }
//
//}