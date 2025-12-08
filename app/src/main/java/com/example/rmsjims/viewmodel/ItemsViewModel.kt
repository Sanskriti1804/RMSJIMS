package com.example.rmsjims.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.schema.Items
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.Facilities
import com.example.rmsjims.repository.ItemsRepository
import kotlinx.coroutines.launch

class ItemsViewModel(
    private val itemsRepository: ItemsRepository
) : ViewModel(){

    var itemsState by mutableStateOf<UiState<List<Items>>>(UiState.Loading)

    init {
        Log.d("ItemsViewModel", "ViewModel initialized, fetching items...")
        getItems()
    }

    fun refreshItems() {
        Log.d("ItemsViewModel", "Refreshing items (retry requested)...")
        getItems()
    }

    private fun getItems(){
        viewModelScope.launch {
            itemsState = UiState.Loading
            try{
                Log.d("ItemsViewModel", "Calling repository.fetchItems()...")
                val items = itemsRepository.fetchItems()
                Log.d("ItemsViewModel", "Received ${items.size} items from repository")
                if (items.isEmpty()) {
                    Log.w("ItemsViewModel", "WARNING: Repository returned empty list!")
                    Log.w("ItemsViewModel", "This might mean:")
                    Log.w("ItemsViewModel", "  1. The 'items' table is empty")
                    Log.w("ItemsViewModel", "  2. RLS policies are blocking access")
                    Log.w("ItemsViewModel", "  3. Network/connection issues")
                } else {
                    Log.d("ItemsViewModel", "First 3 items: ${items.take(3).map { it.name }}")
                }
                itemsState = UiState.Success(items)
            }
            catch (e : Exception){
                Log.e("ItemsViewModel", "ERROR fetching items", e)
                Log.e("ItemsViewModel", "Exception type: ${e.javaClass.simpleName}")
                Log.e("ItemsViewModel", "Exception message: ${e.message}")
                Log.e("ItemsViewModel", "Exception stack trace:", e)
                itemsState = UiState.Error(e)
            }
        }
    }

    fun getFacilityNameForEquipment(items: Items, facilities : List<Facilities>) : String{
        val facility = facilities.find { it.id == items.facility_id }
        return facility?.prof_incharge ?: "Unkown Facility"
    }
    
    suspend fun getItemById(id: Int): Items? {
        return try {
            itemsRepository.fetchItemById(id)
        } catch (e: Exception) {
            Log.e("ItemsViewModel", "Error fetching item by id", e)
            null
        }
    }
}