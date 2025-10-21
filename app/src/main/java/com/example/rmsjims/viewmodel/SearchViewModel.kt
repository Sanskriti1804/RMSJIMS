package com.example.rmsjims.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class SearchViewModel : ViewModel() {

    private val _query = mutableStateOf("")
    val query = _query

    private val _isChatSheetVisible = mutableStateOf(false)
    val isChatSheetVisible = _isChatSheetVisible

    fun showChatSheet() {
        _isChatSheetVisible.value = true
    }

    fun hideChatSheet() {
        _isChatSheetVisible.value = false
    }
    fun onQueryChange(newValue: String) {
        _query.value = newValue
    }

    fun onSearchTriggered() {
        println("Search triggered with query: ${_query.value}")
    }
}
