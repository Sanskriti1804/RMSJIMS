package com.example.rmsjims.data.remote.apiservice

import com.example.rmsjims.data.schema.ItemImages

//what operations the data source must provide?
interface ItemImagesApiService {
    suspend fun getItems() : List<ItemImages>
    suspend fun getItemById(itemId: Int) : ItemImages?
    suspend fun addItem(itemImages: ItemImages) : ItemImages
    suspend fun deleteItem(itemId : Int) : Boolean
    suspend fun updateItem(itemImages: ItemImages) : ItemImages
}