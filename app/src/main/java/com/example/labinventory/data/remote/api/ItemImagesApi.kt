package com.example.labinventory.data.remote.api

import com.example.labinventory.data.remote.apiservice.ItemImagesApiService
import com.example.labinventory.data.schema.ItemImages
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.postgrest


//talks directly to api
class ItemImagesApi(
    private val supabaseClient: SupabaseClient
) : ItemImagesApiService //implementation of api service interface - talks directly to sb db using sb clint
{
    private val inventoryTable = supabaseClient.postgrest["item_images"]     //targeting this table in db

    override suspend fun getItems(): List<ItemImages> {
        return inventoryTable
            .select()       //select all
            .decodeList<ItemImages>()      //decode them into a list of model class
    }

    override suspend fun getItemById(itemId: Int): ItemImages? {
        return inventoryTable
            .select { filter { eq("id", itemId) } }
            .decodeSingleOrNull()
    }

    override suspend fun addItem(itemImages: ItemImages): ItemImages {
        return inventoryTable.insert(itemImages).decodeSingle()
    }

    override suspend fun deleteItem(itemId: Int): Boolean {
        val result = inventoryTable.delete {
            filter { eq("item_id", itemId) }
        }
        return true
    }

    override suspend fun updateItem(itemImages: ItemImages): ItemImages {
        return inventoryTable
            .update(itemImages){
                filter { eq("id", itemImages.id)
                   }
            }
            .decodeSingle()
            }
}