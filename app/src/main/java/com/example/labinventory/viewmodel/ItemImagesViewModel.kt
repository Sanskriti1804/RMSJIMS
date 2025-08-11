package com.example.labinventory.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labinventory.data.model.UiState
import com.example.labinventory.data.schema.ItemImages
import com.example.labinventory.repository.ItemImagesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

//ui _trigger events
class ItemImagesViewModel(
    private val repository: ItemImagesRepository
) : ViewModel(){
    private val _itemImageListState = MutableStateFlow<UiState<List<ItemImages>>>(UiState.Loading)
    val uiState : StateFlow<UiState<List<ItemImages>>> = _itemImageListState

    private val _itemImageState = MutableStateFlow<UiState<ItemImages>>(UiState.Loading)
    val itemImageState : StateFlow<UiState<ItemImages>> = _itemImageState
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

    fun addInventoryItemImage(itemImages: ItemImages){
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

    fun updateInventoryItemImage(itemImages: ItemImages) {
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

    fun deleteInventoryItemImage(item: ItemImages) {
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