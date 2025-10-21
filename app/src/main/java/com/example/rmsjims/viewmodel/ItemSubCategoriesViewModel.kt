package com.example.rmsjims.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.data.schema.ItemSubCategories
import com.example.rmsjims.repository.ItemSubCategoriesRepository
import kotlinx.coroutines.launch

class ItemSubCategoriesViewModel (
    private val ItemSubCategoriesRepository: ItemSubCategoriesRepository
) : ViewModel(){

    private var itemSubCategoriesState by mutableStateOf<UiState<List<ItemSubCategories>>>(UiState.Loading)
        private set

    init {
        getItemSubCategories()
    }

    private fun getItemSubCategories() {
        viewModelScope.launch {
            try {
                val itemSubCategories = ItemSubCategoriesRepository.fetchItemSubCategories()
                itemSubCategoriesState = UiState.Success(itemSubCategories)
            }
            catch (e : Exception){
                itemSubCategoriesState = UiState.Error(e)
            }
        }
    }

}