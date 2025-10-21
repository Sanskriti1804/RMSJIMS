package com.example.rmsjims.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rmsjims.data.schema.ItemCategories
import com.example.rmsjims.data.model.UiState
import com.example.rmsjims.repository.ItemCategoriesRepository
import kotlinx.coroutines.launch

class ItemCategoriesViewModel (
    private val categoryRepository: ItemCategoriesRepository
) : ViewModel(){

    var categoriesState by mutableStateOf<UiState<List<ItemCategories>>>(UiState.Loading)
        private set

    init {
        getCategories()
    }

    private fun getCategories(){
        viewModelScope.launch {
            categoriesState = UiState.Loading
            try {
                val categories = categoryRepository.fetchCategories()
                categoriesState = UiState.Success(categories)
            }
            catch (e : Exception){
                categoriesState = UiState.Error(e)
            }
        }
    }
}