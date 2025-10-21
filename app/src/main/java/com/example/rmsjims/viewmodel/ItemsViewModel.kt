package com.example.rmsjims.viewmodel

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
        getItems()
    }

    private fun getItems(){
        viewModelScope.launch {
            itemsState = UiState.Loading
            try{
                val items = itemsRepository.fetchItems()
                itemsState = UiState.Success(items)
            }
            catch (e : Exception){
                itemsState = UiState.Error(e)
            }
        }
    }

    fun getFacilityNameForEquipment(items: Items, facilities : List<Facilities>) : String{
        val facility = facilities.find { it.id == items.facility_id }
        return facility?.prof_incharge ?: "Unkown Facility"
    }
}