//package com.example.labinventory.data.remote
//
//import com.example.labinventory.data.model.InventoryItemImages
//import io.github.jan.supabase.SupabaseClient
//import io.github.jan.supabase.postgrest.postgrest
//
////talks directly to api
//class SupabaseInventoryApi(
//    private val supabaseClient: SupabaseClient
//) : InventoryApiService //implementation of api service interface - talks directly to sb db using sb clint
//{
//    private val inventoryTable = supabaseClient.postgrest["item_images"]     //targeting this table in db
//
//    override suspend fun getItems(): List<InventoryItemImages> {
//        return inventoryTable
//            .select()       //select all
//            .decodeList<InventoryItemImages>()      //decode them into a list of model class
//    }
//
//    override suspend fun getItemById(itemId: Int): InventoryItemImages? {
//        return inventoryTable
//            .select { filter { eq("id", itemId) } }
//            .decodeSingleOrNull()
//    }
//
//    override suspend fun addItem(itemImages: InventoryItemImages): InventoryItemImages {
//        return inventoryTable.insert(itemImages).decodeSingle()
//    }
//
//    override suspend fun deleteItem(itemId: Int): Boolean {
//        val result = inventoryTable.delete {
//            filter { eq("item_id", itemId) }
//        }
//        return true
//    }
//
//    override suspend fun updateItem(itemImages: InventoryItemImages): InventoryItemImages {
//        return inventoryTable
//            .update{
//                filter { eq("id", itemImages.id)
//                   }
//            }
//            .decodeSingle()
//            }
//}