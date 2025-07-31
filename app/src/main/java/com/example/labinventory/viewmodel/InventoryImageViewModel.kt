package com.example.labinventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labinventory.data.model.InventoryItemImages
import com.example.labinventory.data.model.UiState
import com.example.labinventory.repository.InventoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//ui _trigger events
class InventoryImageViewModel(
    private val repository: InventoryRepository
) : ViewModel(){
    private val _itemImageListState = MutableStateFlow<UiState<List<InventoryItemImages>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<InventoryItemImages>>> = _itemImageListState

    private val _itemImageState = MutableStateFlow<UiState<InventoryItemImages>>(UiState.Loading)
    val itemImageState : StateFlow<UiState<InventoryItemImages>> = _itemImageState
    fun loadInventoryItemImages(){
        viewModelScope.launch {
            _itemImageListState.value = UiState.Loading
            try {
                val inventoryItemImages = repository.getInventoryItemImages()
                _itemImageListState.value = UiState.Success(inventoryItemImages)
            } catch (e: Exception){
                _itemImageListState.value = UiState.Error(e)
            }
        }
    }

    fun getInventoryItemImageById(id: Int){
        viewModelScope.launch {
            _itemImageListState.value = UiState.Loading
            try {
                val inventoryItemImage = repository.getInventoryItemImageById(id)
                if (inventoryItemImage !=  null) {
                    _itemImageState.value = UiState.Success(inventoryItemImage)
                }else{
                    _itemImageState.value = UiState.Error(Exception("Item not found"))
                }
            }catch (e : Exception){
                _itemImageState.value = UiState.Error(e)
            }
        }
    }

    fun addInventoryItemImage(itemImages: InventoryItemImages){
        viewModelScope.launch {
            try {
                repository.addInventoryItemImage(itemImages)
                loadInventoryItemImages()
            }
            catch (e : Exception){
                _itemImageListState.value = UiState.Error(e)
            }
        }
    }

    fun updateInventoryItemImage(itemImages: InventoryItemImages) {
        viewModelScope.launch {
            try {
                repository.updateInventoryItemImage(itemImages)
                loadInventoryItemImages()
            }
            catch (e : Exception){
                _itemImageListState.value = UiState.Error(e)
            }
        }
    }

    fun deleteInventoryItemImage(item: InventoryItemImages) {
        viewModelScope.launch {
            try {
                repository.deleteInventoryItemImage(itemId = item.id)
                loadInventoryItemImages()
            }
            catch (e : Exception){
                _itemImageListState.value = UiState.Error(e)
            }
        }
    }

}