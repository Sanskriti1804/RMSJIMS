//package com.example.labinventory.viewmodel
//
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.labinventory.data.model.Categories
//import com.example.labinventory.data.model.UiState
//import com.example.labinventory.repository.CategoryRepository
//import kotlinx.coroutines.launch
//
//class CategoryViewModel (
//    private val categoryRepository: CategoryRepository
//) : ViewModel(){
//
//    var categoriesState by mutableStateOf<UiState<List<Categories>>>(UiState.Loading)
//        private set
//
//    init {
//        getCategories()
//    }
//
//    private fun getCategories(){
//        viewModelScope.launch {
//            categoriesState = UiState.Loading
//            try {
//                val categories = categoryRepository.fetchCategories()
//                categoriesState = UiState.Success(categories)
//            }
//            catch (e : Exception){
//                categoriesState = UiState.Error(e)
//            }
//        }
//    }
//}