package com.example.rmsjims.viewmodel

import android.util.Log
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
        Log.d("ItemCategoriesVM", "ViewModel initialized, fetching categories...")
        getCategories()
    }

    private fun getCategories(){
        viewModelScope.launch {
            categoriesState = UiState.Loading
            try {
                Log.d("ItemCategoriesVM", "Calling repository.fetchCategories()...")
                val categories = categoryRepository.fetchCategories()
                Log.d("ItemCategoriesVM", "Received ${categories.size} categories from repository")
                if (categories.isEmpty()) {
                    Log.w("ItemCategoriesVM", "WARNING: Repository returned empty list!")
                } else {
                    Log.d("ItemCategoriesVM", "Categories: ${categories.map { it.name }}")
                }
                categoriesState = UiState.Success(categories)
            }
            catch (e : Exception){
                Log.e("ItemCategoriesVM", "ERROR fetching categories", e)
                Log.e("ItemCategoriesVM", "Exception type: ${e.javaClass.simpleName}")
                Log.e("ItemCategoriesVM", "Exception message: ${e.message}")
                Log.e("ItemCategoriesVM", "Exception stack trace:", e)
                categoriesState = UiState.Error(e)
            }
        }
    }
}